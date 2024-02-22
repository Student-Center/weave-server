package com.studentcenter.weave.application.meeting.service.application

import com.studentcenter.weave.application.common.security.context.getCurrentUserAuthentication
import com.studentcenter.weave.application.meeting.port.inbound.MeetingRequestUseCase
import com.studentcenter.weave.application.meeting.service.domain.impl.MeetingDomainService
import com.studentcenter.weave.application.meetingTeam.port.inbound.MeetingTeamQueryUseCase
import com.studentcenter.weave.application.user.port.inbound.UserQueryUseCase
import com.studentcenter.weave.domain.meeting.entity.Meeting
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingTeam
import com.studentcenter.weave.domain.meetingTeam.enums.MeetingTeamStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

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
            .also { validateMyMeetingTeamStatus(it) }

        val receivingMeetingTeam: MeetingTeam =
            meetingTeamQueryUseCase.getById(command.receivingMeetingTeamId)

        validateMeetingTeamConditions(myMeetingTeam, receivingMeetingTeam)

        Meeting.create(
            requestingTeamId = myMeetingTeam.id,
            receivingTeamId = receivingMeetingTeam.id,
        ).also { meetingDomainService.save(it) }
    }

    private fun validateMyUniversityEmailVerified() {
        val isUniversityEmailVerified = getCurrentUserAuthentication()
            .let { userQueryUseCase.isUserUniversityVerified(it.userId) }

        require(isUniversityEmailVerified) {
            "대학교 이메일 인증이 되지 않았어요! 대학교 이메일을 인증해 주세요!"
        }
    }

    private fun getMyMeetingTeam(): MeetingTeam {
        return getCurrentUserAuthentication()
            .let { meetingTeamQueryUseCase.findByMemberUserId(it.userId) }
            ?: throw IllegalArgumentException("내 미팅팀이 존재하지 않아요! 미팅팀에 참여해 주세요!")
    }

    private fun validateMyMeetingTeamStatus(myMeetingTeam: MeetingTeam) {
        require(myMeetingTeam.status == MeetingTeamStatus.PUBLISHED) {
            "나의 미팅팀이 공개되지 않았어요! 미팅팀을 공개해 주세요!"
        }
    }

    private fun validateMeetingTeamConditions(
        myMeetingTeam: MeetingTeam,
        receivingMeetingTeam: MeetingTeam,
    ) {
        require(myMeetingTeam.gender != receivingMeetingTeam.gender) {
            "다른 성별의 미팅팀에만 미팅을 요청할 수 있어요!"
        }

        require(myMeetingTeam.memberCount == receivingMeetingTeam.memberCount) {
            "동일한 인원수의 미팅팀에만 미팅을 요청할 수 있어요!"
        }
    }

}
