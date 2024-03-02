package com.studentcenter.weave.application.meeting.service.application

import com.studentcenter.weave.application.common.exception.MeetingExceptionType
import com.studentcenter.weave.application.common.security.context.getCurrentUserAuthentication
import com.studentcenter.weave.application.meeting.port.inbound.MeetingRequestUseCase
import com.studentcenter.weave.application.meeting.service.domain.MeetingDomainService
import com.studentcenter.weave.application.meetingTeam.port.inbound.MeetingTeamQueryUseCase
import com.studentcenter.weave.application.user.port.inbound.UserQueryUseCase
import com.studentcenter.weave.domain.meeting.entity.Meeting
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingTeam
import com.studentcenter.weave.domain.meetingTeam.enums.MeetingTeamStatus
import com.studentcenter.weave.support.common.exception.CustomException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class MeetingRequestApplicationService(
    private val userQueryUseCase: UserQueryUseCase,
    private val meetingTeamQueryUseCase: MeetingTeamQueryUseCase,
    private val meetingDomainService: MeetingDomainService,
) : MeetingRequestUseCase {

    @Transactional
    override fun invoke(command: MeetingRequestUseCase.Command) {
        validateMyUniversityEmailVerified()

        val myMeetingTeam: MeetingTeam = getMyMeetingTeam()
            .also {
                validateMyMeetingTeamStatus(it)
                validateDuplicatedRequest(it, command.receivingMeetingTeamId)
            }


        val receivingMeetingTeam: MeetingTeam =
            meetingTeamQueryUseCase.getById(command.receivingMeetingTeamId)

        validateMeetingTeamConditions(myMeetingTeam, receivingMeetingTeam)

        Meeting.create(
            requestingTeamId = myMeetingTeam.id,
            receivingTeamId = receivingMeetingTeam.id,
        ).also { meetingDomainService.save(it) }
    }

    private fun validateDuplicatedRequest(myTeam: MeetingTeam, receivingTeamId: UUID) {
        if(meetingDomainService.existsMeetingRequest(myTeam.id, receivingTeamId)) {
            throw CustomException(
                MeetingExceptionType.ALREADY_REQUEST_MEETING,
                "이미 상대팀에게 미팅을 신청했어요!",
            )
        }
    }

    private fun validateMyUniversityEmailVerified() {
        val isUniversityEmailVerified = getCurrentUserAuthentication()
            .let { userQueryUseCase.isUserUniversityVerified(it.userId) }

        if (isUniversityEmailVerified.not()){
            throw CustomException(
                MeetingExceptionType.CAN_NOT_MEETING_REQUEST_NOT_UNIV_VERIFIED_USER,
                "대학교 이메일 인증이 되지 않았어요! 대학교 이메일을 인증해 주세요!",
            )
        }
    }

    private fun getMyMeetingTeam(): MeetingTeam {
        return getCurrentUserAuthentication()
            .let { meetingTeamQueryUseCase.findByMemberUserId(it.userId) }
            ?: throw CustomException(
                MeetingExceptionType.NOT_FOUND_MY_MEETING_TEAM,
                "내 미팅팀이 존재하지 않아요! 미팅팀에 참여해 주세요!",
            )
    }

    private fun validateMyMeetingTeamStatus(myMeetingTeam: MeetingTeam) {
        if (myMeetingTeam.status != MeetingTeamStatus.PUBLISHED) {
            throw CustomException(
                MeetingExceptionType.CAN_NOT_PUBLISHED_TEAM,
                "나의 미팅팀이 공개되지 않았어요! 미팅팀을 공개해 주세요!",
            )
        }
    }

    private fun validateMeetingTeamConditions(
        myMeetingTeam: MeetingTeam,
        receivingMeetingTeam: MeetingTeam,
    ) {
        if (myMeetingTeam.gender == receivingMeetingTeam.gender) {
            throw CustomException(
                MeetingExceptionType.CAN_NOT_MEETING_REQUEST_SAME_GENDER,
                "다른 성별의 미팅팀에만 미팅을 요청할 수 있어요!",
            )
        }

        if (myMeetingTeam.memberCount != receivingMeetingTeam.memberCount) {
            throw CustomException(
                MeetingExceptionType.CAN_NOT_MEETING_REQUEST_NOT_SAME_MEMBERS,
                "동일한 인원수의 미팅팀에만 미팅을 요청할 수 있어요!"
            )
        }
    }

}
