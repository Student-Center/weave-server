package com.studentcenter.weave.application.meetingTeam.service.application

import com.studentcenter.weave.application.meetingTeam.port.inbound.MeetingTeamInfoGetAllByIdUseCase
import com.studentcenter.weave.application.meetingTeam.service.domain.MeetingTeamDomainService
import com.studentcenter.weave.application.meetingTeam.vo.MeetingTeamInfo
import com.studentcenter.weave.application.university.port.inbound.UniversityGetByIdUsecase
import com.studentcenter.weave.application.user.port.inbound.UserQueryUseCase
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingTeam
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class MeetingTeamInfoGetAllByIdApplicationService(
    private val userQueryUseCase: UserQueryUseCase,
    private val universityGetByIdUsecase: UniversityGetByIdUsecase,
    private val meetingTeamDomainService: MeetingTeamDomainService,
) : MeetingTeamInfoGetAllByIdUseCase {

    @Transactional(readOnly = true)
    override fun invoke(ids: List<UUID>): List<MeetingTeamInfo> {
        val meetingTeams = meetingTeamDomainService.getAllByIds(ids)
        return meetingTeams.map { MeetingTeamInfo(
                team = it,
                memberInfos = createMemberInfos(it)
            )
        }
    }

    private fun createMemberInfos(team: MeetingTeam) =
        meetingTeamDomainService
            .findAllMeetingMembersByMeetingTeamId(team.id)
            .map {
                val memberUser = userQueryUseCase.getById(it.userId)
                val university = universityGetByIdUsecase.invoke(memberUser.universityId)
                MeetingTeamInfo.MemberInfo(
                    id = it.id,
                    user = memberUser,
                    university = university,
                    role = it.role,
                )
            }

}
