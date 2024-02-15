package com.studentcenter.weave.domain.meetingTeam.entity

import com.studentcenter.weave.domain.meetingTeam.enums.Location
import com.studentcenter.weave.domain.meetingTeam.enums.MeetingTeamStatus
import com.studentcenter.weave.domain.meetingTeam.vo.TeamIntroduce
import com.studentcenter.weave.domain.user.enums.Gender
import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class MeetingTeamTest : FunSpec({

    (2..4).forEach() { memberCount ->
        test("팀원의 수는 최소 2명 최대 4명까지 가능하다 : $memberCount 명 - 성공") {
            val teamIntroduce = TeamIntroduce("팀 한줄 소개")
            val location = Location.BUSAN
            val gender = Gender.MAN

            shouldNotThrowAny {
                MeetingTeam.create(
                    teamIntroduce = teamIntroduce,
                    memberCount = memberCount,
                    location = location,
                    gender = gender,
                )
            }
        }
    }

    listOf(1, 5).forEach() { memberCount ->
        test("팀원의 수는 최소 2명 최대 4명까지 가능하다 : $memberCount 명 - 실패") {
            val teamIntroduce = TeamIntroduce("팀 한줄 소개")
            val location = Location.BUSAN
            val gender = Gender.MAN

            shouldThrow<IllegalArgumentException> {
                MeetingTeam.create(
                    teamIntroduce = teamIntroduce,
                    memberCount = memberCount,
                    location = location,
                    gender = gender,
                )
            }
        }
    }

    test("미팅 팀 생성시 WAITING 상태로 생성된다.") {
        // arrange, act
        val meetingTeam = MeetingTeam.create(
            teamIntroduce = TeamIntroduce("팀 한줄 소개"),
            memberCount = 2,
            location = Location.BUSAN,
            gender = Gender.MAN,
        )

        // assert
        meetingTeam.status shouldBe MeetingTeamStatus.WAITING
    }

})
