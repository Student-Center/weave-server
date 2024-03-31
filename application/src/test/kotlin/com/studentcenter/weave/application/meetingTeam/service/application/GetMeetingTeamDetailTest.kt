package com.studentcenter.weave.application.meetingTeam.service.application

import com.studentcenter.weave.application.common.security.context.UserSecurityContext
import com.studentcenter.weave.application.meetingTeam.outbound.MeetingMemberRepositorySpy
import com.studentcenter.weave.application.meetingTeam.outbound.MeetingTeamMemberSummaryRepositorySpy
import com.studentcenter.weave.application.meetingTeam.outbound.MeetingTeamRepositorySpy
import com.studentcenter.weave.application.meetingTeam.port.inbound.GetMeetingTeamDetail
import com.studentcenter.weave.application.meetingTeam.service.domain.impl.MeetingTeamDomainServiceImpl
import com.studentcenter.weave.application.university.port.inbound.GetMajor
import com.studentcenter.weave.application.university.port.inbound.GetUniversity
import com.studentcenter.weave.application.user.port.inbound.GetUser
import com.studentcenter.weave.application.user.vo.UserAuthenticationFixtureFactory
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingTeamFixtureFactory
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingTeamMemberSummaryFixtureFactory
import com.studentcenter.weave.domain.meetingTeam.enums.MeetingTeamStatus
import com.studentcenter.weave.domain.user.entity.MajorFixtureFactory
import com.studentcenter.weave.domain.user.entity.UniversityFixtureFactory
import com.studentcenter.weave.domain.user.entity.UserFixtureFactory
import com.studentcenter.weave.support.security.context.SecurityContextHolder
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk

@DisplayName("GetMeetingTeamDetailTest")
class GetMeetingTeamDetailTest : DescribeSpec({

    val getMajor = mockk<GetMajor>()
    val getUniversity = mockk<GetUniversity>()

    val meetingTeamRepositorySpy = MeetingTeamRepositorySpy()
    val meetingMemberRepositorySpy = MeetingMemberRepositorySpy()
    val meetingTeamMemberSummaryRepositorySpy = MeetingTeamMemberSummaryRepositorySpy()
    val getUser = mockk<GetUser>()

    val meetingTeamDomainService = MeetingTeamDomainServiceImpl(
        meetingTeamMemberSummaryRepository = meetingTeamMemberSummaryRepositorySpy
    )

    val sut = GetMeetingTeamDetailService(
        meetingTeamDomainService = meetingTeamDomainService,
        meetingTeamRepository = meetingTeamRepositorySpy,
        getMajor = getMajor,
        getUser = getUser,
        getUniversity = getUniversity
    )

    afterEach {
        meetingTeamRepositorySpy.clear()
        meetingMemberRepositorySpy.clear()
        meetingTeamMemberSummaryRepositorySpy.clear()
        SecurityContextHolder.clearContext()
        clearAllMocks()
    }

    describe("미팅팀 상세 조회") {
        context("내 미팅팀일 경우") {
            it("미팅팀 정보를 조회한다 - 케미 점수(X)") {
                // arrange
                val me = UserFixtureFactory.create()
                me.let { UserAuthenticationFixtureFactory.create(it) }
                    .also { SecurityContextHolder.setContext(UserSecurityContext(it)) }

                val targetMeetingTeam = MeetingTeamFixtureFactory
                    .create(leader = me)
                    .also { meetingTeamRepositorySpy.save(it) }

                every { getUser.getById(any()) } returns UserFixtureFactory.create()
                every { getUniversity.getById(any()) } returns UniversityFixtureFactory.create()
                every { getMajor.getById(any()) } returns MajorFixtureFactory.create()

                // act
                val result = sut.invoke(GetMeetingTeamDetail.Command(targetMeetingTeam.id))

                // assert
                result.meetingTeam shouldBe targetMeetingTeam
                result.affinityScore shouldBe null
            }
        }

        context("조회하려는 팀이 내 미팅팀이 아니고, 내팀(PUBLISHED), 상대팀(PUBLISHED) 상태인 경우") {
            it("미팅팀 정보를 조회한다 - 케미 점수(O)") {
                // arrange
                val me = UserFixtureFactory.create()
                me.let { UserAuthenticationFixtureFactory.create(it) }
                    .also { SecurityContextHolder.setContext(UserSecurityContext(it)) }

                val myMeetingTeam = MeetingTeamFixtureFactory
                    .create(status = MeetingTeamStatus.PUBLISHED, leader = me)
                    .also { meetingTeamRepositorySpy.save(it) }
                val targetMeetingTeam = MeetingTeamFixtureFactory
                    .create(status = MeetingTeamStatus.PUBLISHED)
                    .also { meetingTeamRepositorySpy.save(it) }

                MeetingTeamMemberSummaryFixtureFactory
                    .create(meetingTeamId = myMeetingTeam.id)
                    .also { meetingTeamMemberSummaryRepositorySpy.save(it) }
                MeetingTeamMemberSummaryFixtureFactory
                    .create(meetingTeamId = targetMeetingTeam.id)
                    .also { meetingTeamMemberSummaryRepositorySpy.save(it) }

                every { getUser.getById(any()) } returns UserFixtureFactory.create()
                every { getUniversity.getById(any()) } returns UniversityFixtureFactory.create()
                every { getMajor.getById(any()) } returns MajorFixtureFactory.create()

                // act
                val result = sut.invoke(GetMeetingTeamDetail.Command(targetMeetingTeam.id))

                // assert
                result.meetingTeam shouldBe targetMeetingTeam
                result.affinityScore shouldNotBe null
            }
        }

        context("내 미팅팀이 아니고, 내팀(WAITING), 상대팀(PUBLISH) 상태인 경우") {
            it("미팅팀 정보를 조회한다 - 케미 점수(O)") {
                // arrange
                val me = UserFixtureFactory.create()
                me.let { UserAuthenticationFixtureFactory.create(it) }
                    .also { SecurityContextHolder.setContext(UserSecurityContext(it)) }

                val myMeetingTeam = MeetingTeamFixtureFactory
                    .create(status = MeetingTeamStatus.WAITING, leader = me)
                    .also { meetingTeamRepositorySpy.save(it) }
                val targetMeetingTeam = MeetingTeamFixtureFactory
                    .create(status = MeetingTeamStatus.PUBLISHED)
                    .also { meetingTeamRepositorySpy.save(it) }

                MeetingTeamMemberSummaryFixtureFactory
                    .create(meetingTeamId = targetMeetingTeam.id)
                    .also { meetingTeamMemberSummaryRepositorySpy.save(it) }

                every { getUser.getById(any()) } returns UserFixtureFactory.create()
                every { getUniversity.getById(any()) } returns UniversityFixtureFactory.create()
                every { getMajor.getById(any()) } returns MajorFixtureFactory.create()

                // act
                val result = sut.invoke(GetMeetingTeamDetail.Command(targetMeetingTeam.id))

                // assert
                result.meetingTeam shouldBe targetMeetingTeam
                result.affinityScore shouldBe null
            }
        }

        context("내 미팅팀이 아니고, 내팀(PUBLISH), 상대팀(WAITING) 상태인 경우") {
            it("미팅팀 정보를 조회한다 - 케미 점수(O)") {
                // arrange
                val me = UserFixtureFactory.create()
                me.let { UserAuthenticationFixtureFactory.create(it) }
                    .also { SecurityContextHolder.setContext(UserSecurityContext(it)) }

                val myMeetingTeam = MeetingTeamFixtureFactory
                    .create(status = MeetingTeamStatus.WAITING, leader = me)
                    .also { meetingTeamRepositorySpy.save(it) }
                val targetMeetingTeam = MeetingTeamFixtureFactory
                    .create(status = MeetingTeamStatus.PUBLISHED)
                    .also { meetingTeamRepositorySpy.save(it) }

                MeetingTeamMemberSummaryFixtureFactory
                    .create(meetingTeamId = myMeetingTeam.id)
                    .also { meetingTeamMemberSummaryRepositorySpy.save(it) }

                every { getUser.getById(any()) } returns UserFixtureFactory.create()
                every { getUniversity.getById(any()) } returns UniversityFixtureFactory.create()
                every { getMajor.getById(any()) } returns MajorFixtureFactory.create()

                // act
                val result = sut.invoke(GetMeetingTeamDetail.Command(targetMeetingTeam.id))

                // assert
                result.meetingTeam shouldBe targetMeetingTeam
                result.affinityScore shouldBe null
            }
        }

        context("내 미팅팀이 없는 경우") {
            it("미팅팀 정보를 조회한다 - 케미 점수(X)") {
                // arrange
                val targetMeetingTeam = MeetingTeamFixtureFactory
                    .create(status = MeetingTeamStatus.PUBLISHED)
                    .also { meetingTeamRepositorySpy.save(it) }

                UserFixtureFactory
                    .create()
                    .let {
                        SecurityContextHolder.setContext(
                            UserSecurityContext(
                                UserAuthenticationFixtureFactory.create(it)
                            )
                        )
                    }

                every { getUser.getById(any()) } returns UserFixtureFactory.create()
                every { getUniversity.getById(any()) } returns UniversityFixtureFactory.create()
                every { getMajor.getById(any()) } returns MajorFixtureFactory.create()

                // act
                val result = sut.invoke(GetMeetingTeamDetail.Command(targetMeetingTeam.id))

                // assert
                result.meetingTeam shouldBe targetMeetingTeam
                result.affinityScore shouldBe null
            }
        }
    }


})
