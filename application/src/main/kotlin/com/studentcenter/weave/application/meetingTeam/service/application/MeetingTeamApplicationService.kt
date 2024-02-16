package com.studentcenter.weave.application.meetingTeam.service.application

import com.studentcenter.weave.application.meetingTeam.port.inbound.MeetingTeamGetDetailUseCase
import com.studentcenter.weave.application.meetingTeam.service.domain.MeetingTeamDomainService
import com.studentcenter.weave.application.meetingTeam.vo.MeetingMemberDetailInfo
import com.studentcenter.weave.application.university.port.inbound.MajorGetByIdUseCase
import com.studentcenter.weave.application.university.port.inbound.UniversityGetByIdUsecase
import com.studentcenter.weave.application.user.port.inbound.UserGetByIdUseCase
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MeetingTeamApplicationService(
    private val meetingTeamDomainService: MeetingTeamDomainService,
    private val majorGetByIdUsecase: MajorGetByIdUseCase,
    private val universityGetByIdUsecase: UniversityGetByIdUsecase,
    private val userGetByIdUseCase: UserGetByIdUseCase,
) : MeetingTeamGetDetailUseCase {

    @Transactional(readOnly = true)
    override fun invoke(command: MeetingTeamGetDetailUseCase.Command): MeetingTeamGetDetailUseCase.Result {
        val meetingTeam = meetingTeamDomainService.getById(command.meetingId)
        val memberDetailInfos = meetingTeamDomainService
            .findAllMeetingMembersByMeetingTeamId(command.meetingId)
            .map {
                val user = userGetByIdUseCase.invoke(it.userId)
                MeetingMemberDetailInfo.of(
                    user = userGetByIdUseCase.invoke(it.userId),
                    major = majorGetByIdUsecase.invoke(user.majorId),
                    university = universityGetByIdUsecase.invoke(user.universityId),
                    role = it.role
                )
            }

        return MeetingTeamGetDetailUseCase.Result(
            meetingTeam = meetingTeam,
            members = memberDetailInfos
        )
    }

}
