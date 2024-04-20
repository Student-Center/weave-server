package com.studentcenter.weave.application.meetingTeam.service.application

import com.studentcenter.weave.application.common.security.context.getCurrentUserAuthentication
import com.studentcenter.weave.application.meetingTeam.port.inbound.GetListMeetingTeam
import com.studentcenter.weave.application.meetingTeam.port.outbound.MeetingTeamRepository
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
class GetListMeetingTeamService(
    private val getUser: GetUser,
    private val getUniversity: GetUniversity,
    private val meetingTeamRepository: MeetingTeamRepository,
) : GetListMeetingTeam {

    override fun invoke(query: GetListMeetingTeam.Query): GetListMeetingTeam.Result {
        val oppositeGender = getCurrentUserAuthentication()
            .gender
            .getOppositeGender()

        val meetingTeams: List<MeetingTeam> = MeetingTeamListFilter(
            memberCount = query.memberCount,
            youngestMemberBirthYear = query.youngestMemberBirthYear,
            oldestMemberBirthYear = query.oldestMemberBirthYear,
            preferredLocations = query.preferredLocations,
            gender = oppositeGender,
            status = MeetingTeamStatus.PUBLISHED,
        ).let {
            meetingTeamRepository.scrollByFilter(
                filter = it,
                next = query.next,
                limit = query.limit + 1
            )
        }

        val hasNext = meetingTeams.size > query.limit
        val next = if (hasNext) meetingTeams.last().id else null
        val items = if (hasNext) meetingTeams.take(query.limit) else meetingTeams

        val meetingTeamInfos = items.map { team ->
            val memberInfos = team
                .members
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
        member: MeetingMember,
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
