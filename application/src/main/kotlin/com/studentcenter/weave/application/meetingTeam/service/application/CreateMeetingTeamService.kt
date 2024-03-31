package com.studentcenter.weave.application.meetingTeam.service.application

import com.studentcenter.weave.application.common.security.context.getCurrentUserAuthentication
import com.studentcenter.weave.application.meetingTeam.port.inbound.CreateMeetingTeam
import com.studentcenter.weave.application.meetingTeam.port.outbound.MeetingTeamRepository
import com.studentcenter.weave.application.user.port.inbound.GetUser
import com.studentcenter.weave.application.user.vo.UserAuthentication
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingTeam
import com.studentcenter.weave.domain.user.entity.User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CreateMeetingTeamService(
    private val meetingTeamRepository: MeetingTeamRepository,
    private val getUser: GetUser,
) : CreateMeetingTeam {

    @Transactional
    override fun invoke(command: CreateMeetingTeam.Command) {
        val userAuthentication: UserAuthentication = getCurrentUserAuthentication()
        val user: User = getUser
            .getById(userAuthentication.userId)
            .also { checkUserRegisterKakaoId(it) }

        val meetingTeam = MeetingTeam.create(
            teamIntroduce = command.teamIntroduce,
            memberCount = command.memberCount,
            location = command.location,
            leader = user,
        )
        meetingTeamRepository.save(meetingTeam)
    }

    private fun checkUserRegisterKakaoId(user: User) {
        require(user.kakaoId != null) {
            "카카오 ID가 등록된 유저만 팀을 생성할 수 있어요. 카카오 ID를 등록해주세요!"
        }
    }

}
