package com.studentcenter.weave.application.meetingTeam.service.application

import com.studentcenter.weave.application.common.security.context.getCurrentUserAuthentication
import com.studentcenter.weave.application.meetingTeam.port.inbound.MeetingTeamCreateUseCase
import com.studentcenter.weave.application.meetingTeam.service.domain.MeetingTeamDomainService
import com.studentcenter.weave.application.user.port.inbound.GetUser
import com.studentcenter.weave.application.user.vo.UserAuthentication
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingTeam
import com.studentcenter.weave.domain.meetingTeam.enums.MeetingMemberRole
import com.studentcenter.weave.domain.user.entity.User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MeetingTeamCreateApplicationService(
    private val meetingTeamDomainService: MeetingTeamDomainService,
    private val getUser: GetUser,
) : MeetingTeamCreateUseCase {

    @Transactional
    override fun invoke(command: MeetingTeamCreateUseCase.Command) {
        val userAuthentication: UserAuthentication = getCurrentUserAuthentication()
        val user: User = getUser
            .getById(userAuthentication.userId)
            .also { checkUserRegisterKakaoId(it) }

        val meetingTeam = MeetingTeam.create(
            teamIntroduce = command.teamIntroduce,
            memberCount = command.memberCount,
            location = command.location,
            gender = user.gender,
        )
        meetingTeamDomainService.save(meetingTeam)
        meetingTeamDomainService.addMember(
            meetingTeam = meetingTeam,
            user = user,
            role = MeetingMemberRole.LEADER,
        )
    }

    private fun checkUserRegisterKakaoId(user: User) {
        require (user.kakaoId != null) {
            "카카오 ID가 등록된 유저만 팀을 생성할 수 있어요. 카카오 ID를 등록해주세요!"
        }
    }

}
