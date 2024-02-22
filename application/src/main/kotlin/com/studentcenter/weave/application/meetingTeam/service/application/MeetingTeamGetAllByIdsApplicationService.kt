package com.studentcenter.weave.application.meetingTeam.service.application

import com.studentcenter.weave.application.meetingTeam.port.inbound.MeetingTeamGetAllByIdsUseCase
import com.studentcenter.weave.application.meetingTeam.service.domain.MeetingTeamDomainService
import com.studentcenter.weave.application.meetingTeam.vo.MeetingTeamInfo
import com.studentcenter.weave.application.university.port.inbound.UniversityGetByIdUsecase
import com.studentcenter.weave.application.user.port.inbound.UserQueryUseCase
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingTeam
import com.studentcenter.weave.domain.university.entity.University
import com.studentcenter.weave.domain.user.entity.User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*
import kotlin.collections.HashMap

@Service
class MeetingTeamGetAllByIdsApplicationService(
    private val userQueryUseCase: UserQueryUseCase,
    private val universityGetByIdUsecase: UniversityGetByIdUsecase,
    private val meetingTeamDomainService: MeetingTeamDomainService,
) : MeetingTeamGetAllByIdsUseCase {

    @Transactional(readOnly = true)
    override fun invoke(command: MeetingTeamGetAllByIdsUseCase.Command): MeetingTeamGetAllByIdsUseCase.Result {
        val meetingTeams = meetingTeamDomainService.getAllByIds(command.ids)
        val univCache = HashMap<UUID, University>(meetingTeams.size * MeetingTeam.MAX_MEMBER_COUNT)
        val meetingTeamInfos = meetingTeams.map { team ->
            val memberInfos = meetingTeamDomainService
                .findAllMeetingMembersByMeetingTeamId(team.id)
                .map {
                    val memberUser = userQueryUseCase.getById(it.userId)
                    val university = getUniversityWithCache(univCache, memberUser)
                    MeetingTeamInfo.MemberInfo(
                        id = it.id,
                        user = memberUser,
                        university = university,
                        role = it.role,
                    )
                }

            MeetingTeamInfo(
                team = team,
                memberInfos = memberInfos
            )
        }

        return MeetingTeamGetAllByIdsUseCase.Result(meetingTeamInfos)
    }

    /**
     * 비슷한 학교가 많을 것으로 보여서 한번 조회한 뒤에 캐시처리하여 재활용
     */
    private fun getUniversityWithCache(
        universityCache: HashMap<UUID, University>,
        memberUser: User,
    ): University {
        if (universityCache.contains(memberUser.universityId).not()) {
            val univ = universityGetByIdUsecase.invoke(memberUser.universityId)
            universityCache[univ.id] = univ
        }
        val university = universityCache[memberUser.universityId]!!
        return university
    }

}
