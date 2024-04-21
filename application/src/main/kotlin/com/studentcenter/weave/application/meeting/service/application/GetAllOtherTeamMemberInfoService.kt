package com.studentcenter.weave.application.meeting.service.application

import com.studentcenter.weave.application.common.security.context.getCurrentUserAuthentication
import com.studentcenter.weave.application.meeting.port.inbound.GetAllOtherTeamMemberInfo
import com.studentcenter.weave.application.meeting.service.domain.MeetingDomainService
import com.studentcenter.weave.application.meetingTeam.port.inbound.GetMeetingTeam
import com.studentcenter.weave.application.meetingTeam.vo.MemberInfo
import com.studentcenter.weave.application.university.port.inbound.GetUniversity
import com.studentcenter.weave.application.user.port.inbound.GetUser
import com.studentcenter.weave.domain.meeting.entity.Meeting
import com.studentcenter.weave.domain.meeting.exception.MeetingException
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingTeam
import org.springframework.stereotype.Service
import java.util.*

@Service
class GetAllOtherTeamMemberInfoService(
    private val meetingDomainService: MeetingDomainService,
    private val getMeetingTeam: GetMeetingTeam,
    private val getUser: GetUser,
    private val getUniversity: GetUniversity,
) : GetAllOtherTeamMemberInfo {

    override fun invoke(meetingId: UUID): List<MemberInfo> {
        val meeting = meetingDomainService.getById(meetingId)
        val myTeam =
            getMeetingTeam.getByMemberUserId(getCurrentUserAuthentication().userId)
        validateMyMeetingByTeam(meeting, myTeam)
        validateMeeting(meeting)

        val otherTeamId = if (meeting.requestingTeamId != myTeam.id) {
            meeting.requestingTeamId
        } else {
            meeting.receivingTeamId
        }


        return getMeetingTeam.getById(otherTeamId).members
            .map {
                val user = getUser.getById(it.userId)
                val university = getUniversity.getById(user.universityId)
                MemberInfo(
                    id = it.id,
                    user = user,
                    university = university,
                    role = it.role
                )
            }
    }

    private fun validateMeeting(meeting: Meeting) {
        // FIXME(prepared): 추후에 상태가 추가되면 Completed -> Prepared
        if (meeting.isCompleted().not()) {
            throw MeetingException.IsNotCompletedMeeting()
        }
    }

    private fun validateMyMeetingByTeam(
        meeting: Meeting,
        myTeam: MeetingTeam,
    ) {
        require(meeting.receivingTeamId == myTeam.id || meeting.requestingTeamId == myTeam.id) {
            "해당 미팅을 찾을 수 없습니다."
        }
    }

}
