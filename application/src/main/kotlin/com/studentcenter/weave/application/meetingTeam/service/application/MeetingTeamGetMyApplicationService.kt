package com.studentcenter.weave.application.meetingTeam.service.application

import com.studentcenter.weave.application.common.security.context.getCurrentUserAuthentication
import com.studentcenter.weave.application.meetingTeam.port.inbound.MeetingTeamGetMyUseCase
import com.studentcenter.weave.application.meetingTeam.service.domain.MeetingTeamDomainService
import com.studentcenter.weave.application.meetingTeam.vo.MyMeetingTeamInfo
import com.studentcenter.weave.application.university.port.inbound.UniversityGetByIdUsecase
import com.studentcenter.weave.application.user.port.inbound.UserQueryUseCase
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingMember
import org.springframework.stereotype.Service
import java.util.*

@Service
class MeetingTeamGetMyApplicationService(
    private val userQueryUseCase: UserQueryUseCase,
    private val universityGetByIdUsecase: UniversityGetByIdUsecase,
    private val meetingDomainService: MeetingTeamDomainService,
) : MeetingTeamGetMyUseCase {

    override fun invoke(command: MeetingTeamGetMyUseCase.Command): MeetingTeamGetMyUseCase.Result {
        val currentUser = getCurrentUserAuthentication().let { userQueryUseCase.getById(it.userId) }

        val meetingTeams =
            meetingDomainService.scrollByMemberUserId(currentUser.id, command.next, command.limit)

        val myMeetingTeamInfos = meetingTeams.map { team ->
            val memberInfos = meetingDomainService
                .findAllMeetingMembersByMeetingTeamId(team.id)
                .sortedBy { it.id }
                .map { createMemberInfo(it, currentUser.id) }

            MyMeetingTeamInfo(
                team = team,
                memberInfos = memberInfos
            )
        }

        return MeetingTeamGetMyUseCase.Result(
            items = myMeetingTeamInfos,
            next = myMeetingTeamInfos.lastOrNull()?.team?.id,
        )
    }

    private fun createMemberInfo(
        member: MeetingMember,
        currentUserId: UUID
    ): MyMeetingTeamInfo.MemberInfo {
        val memberUser = userQueryUseCase.getById(member.userId)
        val university = universityGetByIdUsecase.invoke(memberUser.universityId)
        return MyMeetingTeamInfo.MemberInfo(
            user = memberUser,
            university = university,
            role = member.role,
            isMe = member.userId == currentUserId
        )
    }

}
