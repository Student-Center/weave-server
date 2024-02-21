package com.studentcenter.weave.application.meetingTeam.service.util.impl

import com.studentcenter.weave.application.common.properties.MeetingTeamInvitationPropertiesFixtureFactory
import com.studentcenter.weave.application.meetingTeam.outbound.MeetingTeamInvitationRepositorySpy
import com.studentcenter.weave.application.meetingTeam.util.impl.MeetingTeamInvitationServiceImpl
import com.studentcenter.weave.application.meetingTeam.vo.MeetingTeamInvitation
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingTeamFixtureFactory
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.nulls.shouldNotBeNull

@DisplayName("MeetingTeamInvitationServiceImpl")
class MeetingTeamInvitationServiceImplTest : DescribeSpec({

    val meetingTeamInvitationProperties = MeetingTeamInvitationPropertiesFixtureFactory.create()
    val meetingTeamInvitationRepository = MeetingTeamInvitationRepositorySpy()

    val sut = MeetingTeamInvitationServiceImpl(
        meetingTeamInvitationProperties = meetingTeamInvitationProperties,
        meetingTeamInvitationRepository = meetingTeamInvitationRepository,
    )

    describe("create") {
        it("랜덤 UUID와 함께 초대 링크를 생성한다.") {
            // arrange
            val meetingTeam = MeetingTeamFixtureFactory.create()

            // act
            val actual: MeetingTeamInvitation = sut.create(meetingTeam.id)

            // assert
            // TODO 초대 링크 조회 API 작업 후 변경하기
            actual.shouldNotBeNull()
        }
    }

})
