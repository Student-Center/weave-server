package com.studentcenter.weave.application.meeting.service.application

import com.studentcenter.weave.application.meeting.port.inbound.MeetingTeamDeleteUseCase
import com.studentcenter.weave.application.meeting.port.outbound.MeetingMemberRepository
import com.studentcenter.weave.application.meeting.port.outbound.MeetingTeamRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MeetingTeamDeleteApplicationService(
    private val meetingTeamRepository: MeetingTeamRepository,
    private val meetingMemberRepository: MeetingMemberRepository,
) : MeetingTeamDeleteUseCase {

    @Transactional
    override fun invoke(command: MeetingTeamDeleteUseCase.Command) {
        meetingTeamRepository.getById(command.id)
            .also {
                meetingMemberRepository.deleteAllByMeetingTeamId(it.id)
                meetingTeamRepository.deleteById(it.id)
            }
    }

}
