package com.studentcenter.weave.application.meetingTeam.service.application

import com.studentcenter.weave.application.meetingTeam.port.inbound.MeetingTeamInfoGetAllByIdUseCase
import com.studentcenter.weave.application.meetingTeam.service.domain.MeetingTeamDomainService
import com.studentcenter.weave.application.meetingTeam.vo.MeetingTeamInfo
import com.studentcenter.weave.application.university.port.inbound.UniversityGetByIdUsecase
import com.studentcenter.weave.application.user.port.inbound.UserQueryUseCase
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingTeam
import com.studentcenter.weave.domain.university.entity.University
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*
import kotlin.collections.HashMap

@Service
class MeetingTeamInfoGetAllByIdApplicationService(
    private val userQueryUseCase: UserQueryUseCase,
    private val universityGetByIdUsecase: UniversityGetByIdUsecase,
    private val meetingTeamDomainService: MeetingTeamDomainService,
) : MeetingTeamInfoGetAllByIdUseCase {

    @Transactional(readOnly = true)
    override fun invoke(ids: List<UUID>): List<MeetingTeamInfo> {
        val meetingTeams = meetingTeamDomainService.getAllByIds(ids)
        // TODO(cache): cache layer 도입 시 수정 필요
        val univCache: MutableMap<UUID, University> = HashMap()
        return meetingTeams.map {
            MeetingTeamInfo(team = it, memberInfos = createMemberInfos(it, univCache))
        }
    }

    private fun createMemberInfos(
        team: MeetingTeam,
        univCache: MutableMap<UUID, University>
    ) : List<MeetingTeamInfo.MemberInfo> {
        return meetingTeamDomainService
            .findAllMeetingMembersByMeetingTeamId(team.id)
            .map {
                val memberUser = userQueryUseCase.getById(it.userId)
                val university = getUniversityById(memberUser.universityId, univCache)
                MeetingTeamInfo.MemberInfo(
                    id = it.id,
                    user = memberUser,
                    university = university,
                    role = it.role,
                )
            }
    }

    private fun getUniversityById(universityId: UUID, cache: MutableMap<UUID, University>) : University {
        if (cache.contains(universityId).not()) {
            cache[universityId] = universityGetByIdUsecase.invoke(universityId)
        }

        return cache[universityId] ?: throw NoSuchElementException("학교를 찾을 수 없습니다.")
    }

}
