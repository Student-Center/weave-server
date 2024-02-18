package com.studentcenter.weave.application.meetingTeam.service.util.impl

import com.studentcenter.weave.application.common.properties.MeetingTeamInvitationPropertiesFixtureFactory
import com.studentcenter.weave.application.meetingTeam.outbound.MeetingTeamInvitationRepositorySpy
import com.studentcenter.weave.application.meetingTeam.util.impl.MeetingTeamInvitationServiceImpl
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingTeamFixtureFactory
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import java.util.*

@DisplayName("MeetingTeamInvitationServiceImpl")
class MeetingTeamInvitationServiceImplTest : DescribeSpec({

    val meetingTeamInvitationProperties = MeetingTeamInvitationPropertiesFixtureFactory.create()
    val meetingTeamInvitationRepository = MeetingTeamInvitationRepositorySpy()

    val sut = MeetingTeamInvitationServiceImpl(
        meetingTeamInvitationProperties = meetingTeamInvitationProperties,
        meetingTeamInvitationRepository = meetingTeamInvitationRepository,
    )

    describe("create") {
        it("초대 링크를 위한 랜덤 UUID를 생성한다.") {
            // arrange
            val meetingTeam = MeetingTeamFixtureFactory.create()

            // act
            val actual: UUID = sut.create(meetingTeam.id)

            // assert
            actual.shouldNotBeNull()
            actual::class shouldBe UUID::class
        }
    }

})
