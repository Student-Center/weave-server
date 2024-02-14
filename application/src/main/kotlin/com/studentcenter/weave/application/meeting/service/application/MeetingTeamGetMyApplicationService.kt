package com.studentcenter.weave.application.meeting.service.application

import com.studentcenter.weave.application.common.security.context.getCurrentUserAuthentication
import com.studentcenter.weave.application.meeting.port.inbound.MeetingTeamGetMyUseCase
import com.studentcenter.weave.application.meeting.service.domain.MeetingTeamDomainService
import com.studentcenter.weave.application.meeting.vo.MeetingTeamInfo
import com.studentcenter.weave.application.university.port.inbound.UniversityGetByIdUsecase
import com.studentcenter.weave.application.user.port.inbound.UserQueryUseCase
import com.studentcenter.weave.domain.meeting.entity.MeetingMember
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

        val meetingTeamInfos = meetingTeams.map { team ->
            val memberInfos = meetingDomainService
                .findAllMeetingMembersByMeetingTeamId(team.id)
                .map { createMemberInfo(it, currentUser.id) }

            MeetingTeamInfo(
                team = team,
                memberInfos = memberInfos
            )
        }

        return MeetingTeamGetMyUseCase.Result(
            item = meetingTeamInfos,
            next = meetingTeamInfos.lastOrNull()?.team?.id,
        )
    }

    private fun createMemberInfo(
        member: MeetingMember,
        currentUserId: UUID
    ): MeetingTeamInfo.MemberInfo {
        val memberUser = userQueryUseCase.getById(member.userId)
        val university =
            universityGetByIdUsecase.invoke(UniversityGetByIdUsecase.Command(memberUser.universityId)).university
        return MeetingTeamInfo.MemberInfo(
            user = memberUser,
            university = university,
            role = member.role,
            isMe = member.userId == currentUserId
        )
    }

}
