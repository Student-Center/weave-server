package com.studentcenter.weave.application.meetingTeam.service.application

import com.studentcenter.weave.application.common.exception.MeetingTeamExceptionType
import com.studentcenter.weave.application.common.security.context.getCurrentUserAuthentication
import com.studentcenter.weave.application.meeting.port.inbound.CancelAllMeetingUseCase
import com.studentcenter.weave.application.meetingTeam.port.inbound.DeleteMeetingTeam
import com.studentcenter.weave.application.meetingTeam.port.outbound.MeetingTeamRepository
import com.studentcenter.weave.support.common.exception.CustomException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class DeleteMeetingTeamService(
    private val cancelAllMeetingUseCase: CancelAllMeetingUseCase,
    private val meetingTeamRepository: MeetingTeamRepository,
) : DeleteMeetingTeam {

    @Transactional
    override fun invoke(id: UUID) {
        val currentUserId: UUID = getCurrentUserAuthentication().userId

        val myTeam = meetingTeamRepository.getByMemberUserId(currentUserId)
        if (myTeam.id != id) {
            throw CustomException(MeetingTeamExceptionType.IS_NOT_TEAM_MEMBER, "팀 멤버가 아닙니다.")
        }

        myTeam.delete(currentUserId, meetingTeamRepository::deleteById)
        cancelAllMeetingUseCase.invoke(CancelAllMeetingUseCase.Command(myTeam.id))
    }

}
