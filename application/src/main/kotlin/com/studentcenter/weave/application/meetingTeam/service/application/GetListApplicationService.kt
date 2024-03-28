package com.studentcenter.weave.application.meetingTeam.service.application

import com.studentcenter.weave.application.common.security.context.getCurrentUserAuthentication
import com.studentcenter.weave.application.meetingTeam.port.inbound.GetListMeetingTeam
import com.studentcenter.weave.application.meetingTeam.service.domain.MeetingTeamDomainService
import com.studentcenter.weave.application.meetingTeam.vo.MeetingTeamInfo
import com.studentcenter.weave.application.meetingTeam.vo.MeetingTeamListFilter
import com.studentcenter.weave.application.meetingTeam.vo.MemberInfo
import com.studentcenter.weave.application.university.port.inbound.GetUniversity
import com.studentcenter.weave.application.user.port.inbound.GetUser
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingMember
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingTeam
import com.studentcenter.weave.domain.meetingTeam.enums.MeetingTeamStatus
import org.springframework.stereotype.Service

@Service
class GetListApplicationService(
    private val getUser: GetUser,
    private val getUniversity: GetUniversity,
    private val meetingTeamDomainService: MeetingTeamDomainService,
) : GetListMeetingTeam {

    override fun invoke(command: GetListMeetingTeam.Command): GetListMeetingTeam.Result {
        val oppositeGender = getCurrentUserAuthentication()
            .gender
            .getOppositeGender()

        val meetingTeams: List<MeetingTeam> = MeetingTeamListFilter(
            memberCount = command.memberCount,
            youngestMemberBirthYear = command.youngestMemberBirthYear,
            oldestMemberBirthYear = command.oldestMemberBirthYear,
            preferredLocations = command.preferredLocations,
            gender = oppositeGender,
            status = MeetingTeamStatus.PUBLISHED,
        ).let {
            meetingTeamDomainService.scrollByFilter(
                filter = it,
                next = command.next,
                limit = command.limit + 1
            )
        }

        val hasNext = meetingTeams.size > command.limit
        val next = if (hasNext) meetingTeams.last().id else null
        val items = if (hasNext) meetingTeams.take(command.limit) else meetingTeams

        val meetingTeamInfos = items.map { team ->
            val memberInfos = meetingTeamDomainService
                .findAllMeetingMembersByMeetingTeamId(team.id)
                .map { createMemberInfo(it) }

            MeetingTeamInfo(
                team = team,
                memberInfos = memberInfos
            )
        }

        return GetListMeetingTeam.Result(
            items = meetingTeamInfos,
            next = next,
        )
    }

    private fun createMemberInfo(
        member: MeetingMember
    ): MemberInfo {
        val memberUser = getUser.getById(member.userId)
        val university = getUniversity.getById(memberUser.universityId)
        return MemberInfo(
            id = member.id,
            user = memberUser,
            university = university,
            role = member.role,
        )
    }

}
