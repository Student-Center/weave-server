package com.studentcenter.weave.application.meetingTeam.service.application

import com.studentcenter.weave.application.common.security.context.getCurrentUserAuthentication
import com.studentcenter.weave.application.meetingTeam.port.inbound.MeetingTeamGetMyUseCase
import com.studentcenter.weave.application.meetingTeam.service.domain.MeetingTeamDomainService
import com.studentcenter.weave.application.meetingTeam.vo.MyMeetingTeamInfo
import com.studentcenter.weave.application.university.port.inbound.GetUniversity
import com.studentcenter.weave.application.user.port.inbound.GetUser
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingMember
import org.springframework.stereotype.Service
import java.util.*

@Service
class MeetingTeamGetMyApplicationService(
    private val getUser: GetUser,
    private val getUniversity: GetUniversity,
    private val meetingDomainService: MeetingTeamDomainService,
) : MeetingTeamGetMyUseCase {

    override fun invoke(command: MeetingTeamGetMyUseCase.Command): MeetingTeamGetMyUseCase.Result {
        val currentUser = getCurrentUserAuthentication().let { getUser.getById(it.userId) }

        val meetingTeams = meetingDomainService.scrollByMemberUserId(
            userId = currentUser.id,
            next = command.next,
            limit = command.limit + 1
        )

        val hasNext = meetingTeams.size > command.limit
        val next = if (hasNext) meetingTeams.last().id else null
        val items = if (hasNext) meetingTeams.take(command.limit) else meetingTeams

        val myMeetingTeamInfos = items.map { team ->
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
            next = next
        )
    }

    private fun createMemberInfo(
        member: MeetingMember,
        currentUserId: UUID
    ): MyMeetingTeamInfo.MemberInfo {
        val memberUser = getUser.getById(member.userId)
        val university = getUniversity.getById(memberUser.universityId)
        return MyMeetingTeamInfo.MemberInfo(
            user = memberUser,
            university = university,
            role = member.role,
            isMe = member.userId == currentUserId
        )
    }

}
