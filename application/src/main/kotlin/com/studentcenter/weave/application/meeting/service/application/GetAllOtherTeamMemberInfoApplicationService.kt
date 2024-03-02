package com.studentcenter.weave.application.meeting.service.application

import com.studentcenter.weave.application.common.security.context.getCurrentUserAuthentication
import com.studentcenter.weave.application.meeting.port.inbound.GetAllOtherTeamMemberInfoUseCase
import com.studentcenter.weave.application.meeting.service.domain.MeetingDomainService
import com.studentcenter.weave.application.meetingTeam.port.inbound.MeetingTeamQueryUseCase
import com.studentcenter.weave.application.meetingTeam.vo.MemberInfo
import com.studentcenter.weave.application.university.port.inbound.UniversityGetByIdUsecase
import com.studentcenter.weave.application.user.port.inbound.UserQueryUseCase
import com.studentcenter.weave.domain.meeting.entity.Meeting
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingTeam
import org.springframework.stereotype.Service
import java.util.*

@Service
class GetAllOtherTeamMemberInfoApplicationService(
    private val meetingDomainService: MeetingDomainService,
    private val meetingTeamQueryUseCase: MeetingTeamQueryUseCase,
    private val userQueryUseCase: UserQueryUseCase,
    private val universityGetByIdUseCase: UniversityGetByIdUsecase,
) : GetAllOtherTeamMemberInfoUseCase {

    override fun invoke(meetingId: UUID): List<MemberInfo> {
        val meeting = meetingDomainService.getById(meetingId)
        val myTeam = meetingTeamQueryUseCase.getByMemberUserId(getCurrentUserAuthentication().userId)
        validateMyMeetingByTeam(meeting, myTeam)

        val otherTeamId = if (meeting.requestingTeamId != myTeam.id) meeting.requestingTeamId
        else meeting.receivingTeamId

        return meetingTeamQueryUseCase
            .findAllMeetingMembersByMeetingTeamId(otherTeamId)
            .map {
                val user = userQueryUseCase.getById(it.userId)
                val university = universityGetByIdUseCase.invoke(user.universityId)
                MemberInfo(
                    id = it.id,
                    user = user,
                    university = university,
                    role = it.role
                )
            }
    }

    private fun validateMyMeetingByTeam(meeting: Meeting, myTeam: MeetingTeam) {
        require(meeting.receivingTeamId == myTeam.id || meeting.requestingTeamId == myTeam.id) {
            "해당 미팅을 찾을 수 없습니다."
        }
    }

}
