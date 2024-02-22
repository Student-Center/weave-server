package com.studentcenter.weave.application.meetingTeam.service.application

import com.studentcenter.weave.application.meetingTeam.port.inbound.MeetingTeamGetAllByIdsUseCase
import com.studentcenter.weave.application.meetingTeam.service.domain.MeetingTeamDomainService
import com.studentcenter.weave.application.meetingTeam.vo.MeetingTeamInfo
import com.studentcenter.weave.application.university.port.inbound.UniversityGetByIdUsecase
import com.studentcenter.weave.application.user.port.inbound.UserQueryUseCase
import org.springframework.stereotype.Service

@Service
class MeetingTeamGetAllByIdsApplicationService(
    private val userQueryUseCase: UserQueryUseCase,
    private val universityGetByIdUsecase: UniversityGetByIdUsecase,
    private val meetingTeamDomainService: MeetingTeamDomainService,
) : MeetingTeamGetAllByIdsUseCase {

    override fun invoke(command: MeetingTeamGetAllByIdsUseCase.Command): MeetingTeamGetAllByIdsUseCase.Result {
        val meetingTeams = meetingTeamDomainService.getAllByIds(command.ids)
        val meetingTeamInfos = meetingTeams.map { team ->
            val memberInfos = meetingTeamDomainService
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

            MeetingTeamInfo(
                team = team,
                memberInfos = memberInfos
            )
        }

        return MeetingTeamGetAllByIdsUseCase.Result(meetingTeamInfos)
    }

}
