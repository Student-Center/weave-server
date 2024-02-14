package com.studentcenter.weave.application.meeting.service.application

import com.studentcenter.weave.application.meeting.port.inbound.MeetingTeamGetMyUseCase
import com.studentcenter.weave.application.meeting.vo.MeetingTeamInfo
import com.studentcenter.weave.domain.meeting.entity.MeetingTeam
import com.studentcenter.weave.domain.meeting.enums.Location
import com.studentcenter.weave.domain.meeting.vo.TeamIntroduce
import com.studentcenter.weave.domain.university.entity.University
import com.studentcenter.weave.domain.university.vo.UniversityName
import com.studentcenter.weave.domain.user.entity.User
import com.studentcenter.weave.domain.user.enums.Gender
import com.studentcenter.weave.domain.user.vo.BirthYear
import com.studentcenter.weave.domain.user.vo.Mbti
import com.studentcenter.weave.domain.user.vo.Nickname
import com.studentcenter.weave.support.common.uuid.UuidCreator
import com.studentcenter.weave.support.common.vo.Email
import org.springframework.stereotype.Service

@Service
class MeetingTeamGetMyApplicationService : MeetingTeamGetMyUseCase {

    override fun invoke(command: MeetingTeamGetMyUseCase.Command): MeetingTeamGetMyUseCase.Result {

        // TODO: Implement the logic

        val user = User.create(
            nickname = Nickname("nickname"),
            mbti = Mbti("Intj"),
            email = Email("test@test.com"),
            gender = Gender.MAN,
            birthYear = BirthYear(1995),
            universityId = UuidCreator.create(),
            majorId = UuidCreator.create(),
            avatar = null,
        )

        val university = University.create(
            name = UniversityName("test"),
            domainAddress = "test",
            logoAddress = "test"
        )

        val memberInfo = MeetingTeamInfo.MemberInfo.of(
            user = user,
            university = university,
            isLeader = true,
            isMe = true,
        )

        val meetingTeam = MeetingTeam.create(
            teamIntroduce = TeamIntroduce("test"),
            memberCount = 4,
            gender = Gender.MAN,
            location = Location.SUWON,
        )

        val meetingTeamInfo = MeetingTeamInfo.of(
            team = meetingTeam,
            memberInfos = listOf(memberInfo),
        )

        return MeetingTeamGetMyUseCase.Result(
            item = listOf(meetingTeamInfo),
            next = null,
            limit = command.limit,
        )
    }

}
