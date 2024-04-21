package com.studentcenter.weave.application.meeting.service.application

import com.studentcenter.weave.application.common.security.context.getCurrentUserAuthentication
import com.studentcenter.weave.application.meeting.port.inbound.RequestMeeting
import com.studentcenter.weave.application.meeting.service.domain.MeetingAttendanceDomainService
import com.studentcenter.weave.application.meeting.service.domain.MeetingDomainService
import com.studentcenter.weave.application.meetingTeam.port.inbound.GetMeetingTeam
import com.studentcenter.weave.application.user.port.inbound.GetUser
import com.studentcenter.weave.domain.meeting.entity.Meeting
import com.studentcenter.weave.domain.meeting.entity.MeetingAttendance
import com.studentcenter.weave.domain.meeting.exception.MeetingException
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingMember
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingTeam
import com.studentcenter.weave.domain.meetingTeam.enums.MeetingTeamStatus
import com.studentcenter.weave.domain.meetingTeam.exception.MeetingTeamException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class RequestMeetingService(
    private val getUser: GetUser,
    private val getMeetingTeam: GetMeetingTeam,
    private val meetingDomainService: MeetingDomainService,
    private val meetingAttendanceDomainService: MeetingAttendanceDomainService,
) : RequestMeeting {

    @Transactional
    override fun invoke(command: RequestMeeting.Command) {
        val myMeetingTeam: MeetingTeam = getMyMeetingTeam()
            .also {
                validateMyMeetingTeamStatus(it)
                validateDuplicatedRequest(it, command.receivingMeetingTeamId)
            }

        validateMyUniversityEmailVerified()

        val receivingMeetingTeam: MeetingTeam =
            getMeetingTeam.getById(command.receivingMeetingTeamId)

        validateMeetingTeamConditions(myMeetingTeam, receivingMeetingTeam)

        Meeting.create(
            requestingTeamId = myMeetingTeam.id,
            receivingTeamId = receivingMeetingTeam.id,
        ).also {
            meetingDomainService.save(it)
            meetingAttendanceDomainService.save(createMeetingAttendance(it, myMeetingTeam.members))
        }
    }

    private fun createMeetingAttendance(
        meeting: Meeting,
        members: List<MeetingMember>,
    ): MeetingAttendance {
        val teamMember = members.firstOrNull { it.userId == getCurrentUserAuthentication().userId }

        if (teamMember == null) {
            throw MeetingTeamException.IsNotTeamMember()
        }

        return MeetingAttendance.create(
            meetingId = meeting.id,
            meetingMemberId = teamMember.id,
            isAttend = true
        )
    }

    private fun validateDuplicatedRequest(
        myTeam: MeetingTeam,
        receivingTeamId: UUID,
    ) {
        if (meetingDomainService.existsMeetingRequest(myTeam.id, receivingTeamId)) {
            throw MeetingException.AlreadyRequestMeeting()
        }
    }

    private fun validateMyUniversityEmailVerified() {
        val user = getCurrentUserAuthentication()
            .let { getUser.getById(it.userId) }

        if (user.isUnivVerified.not()) {
            throw MeetingException.UniversityMailUnverifiedUser()
        }
    }

    private fun getMyMeetingTeam(): MeetingTeam {
        return getCurrentUserAuthentication()
            .let { getMeetingTeam.findByMemberUserId(it.userId) }
            ?: throw MeetingTeamException.CanNotFindMyMeetingTeam()
    }

    private fun validateMyMeetingTeamStatus(myMeetingTeam: MeetingTeam) {
        if (myMeetingTeam.status != MeetingTeamStatus.PUBLISHED) {
            throw MeetingTeamException.IsNotPublishedTeam()
        }
    }

    private fun validateMeetingTeamConditions(
        myMeetingTeam: MeetingTeam,
        receivingMeetingTeam: MeetingTeam,
    ) {
        if (myMeetingTeam.gender == receivingMeetingTeam.gender) {
            throw MeetingException.CanNotRequestToSameGender()
        }

        if (myMeetingTeam.memberCount != receivingMeetingTeam.memberCount) {
            throw MeetingException.CanNotRequestToDifferentMemberCount()
        }
    }

}
