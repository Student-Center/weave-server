package com.studentcenter.weave.application.chat.service

import com.studentcenter.weave.application.chat.outbound.ChatRoomRepositorySpy
import com.studentcenter.weave.application.chat.vo.ChatRoomDetail
import com.studentcenter.weave.application.common.security.context.UserSecurityContext
import com.studentcenter.weave.application.meetingTeam.port.inbound.GetMeetingTeam
import com.studentcenter.weave.application.user.port.inbound.GetUser
import com.studentcenter.weave.application.user.vo.UserAuthenticationFixtureFactory
import com.studentcenter.weave.domain.chat.entity.ChatRoomFixtureFactory
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingTeamFixtureFactory
import com.studentcenter.weave.domain.user.entity.UserFixtureFactory
import com.studentcenter.weave.support.security.context.SecurityContextHolder
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

@DisplayName("GetChatRoomServiceTest")
class GetChatRoomServiceTest : DescribeSpec({

    val getMeetingTeam = mockk<GetMeetingTeam>()
    val getUser = mockk<GetUser>()
    val chatRoomRepository = ChatRoomRepositorySpy()

    val sut = GetChatRoomService(
        getUser = getUser,
        getMeetingTeam = getMeetingTeam,
        chatRoomRepository = chatRoomRepository,
    )

    afterEach { chatRoomRepository.clear() }

    describe("채팅방 상세 정보 조회") {
        context("채팅방에 소속된 사용자면") {
            it("채팅방과 관련된 정보를 조회한다") {
                // arrange
                val meAsTeamLeaderUser = UserFixtureFactory.create()
                val myTeamMemberUser = UserFixtureFactory.create()

                UserAuthenticationFixtureFactory
                    .create(user = meAsTeamLeaderUser)
                    .also { SecurityContextHolder.setContext(UserSecurityContext(it)) }

                val myTeam = MeetingTeamFixtureFactory.create(
                    leader = meAsTeamLeaderUser,
                    members = listOf(myTeamMemberUser),
                ).also { every { getMeetingTeam.getById(it.id) } returns it }

                val myTeamIds = listOf(meAsTeamLeaderUser.id, myTeamMemberUser.id)
                every { getUser.findAllByIds(myTeamIds) } returns listOf(
                    meAsTeamLeaderUser,
                    myTeamMemberUser
                )

                val otherTeamLeaderUser = UserFixtureFactory.create()
                val otherTeamMemberUser = UserFixtureFactory.create()

                val otherTeam = MeetingTeamFixtureFactory.create(
                    leader = otherTeamLeaderUser,
                    members = listOf(otherTeamMemberUser),
                ).also { every { getMeetingTeam.getById(it.id) } returns it }

                val otherTeamIds = listOf(otherTeamLeaderUser.id, otherTeamMemberUser.id)
                every { getUser.findAllByIds(otherTeamIds) } returns listOf(
                    otherTeamLeaderUser,
                    otherTeamMemberUser
                )

                val chatRoom = ChatRoomFixtureFactory
                    .create(
                        requestingTeamId = myTeam.id,
                        receivingTeamId = otherTeam.id,
                    )
                    .also { chatRoomRepository.save(it) }


                // act
                val result: ChatRoomDetail = sut.getDetailById(chatRoom.id)

                // assert
                result.id shouldBe chatRoom.id
                result.myTeam.meetingTeamId shouldBe myTeam.id
                result.myTeam.members.size shouldBe 2

                result.otherTeam.meetingTeamId shouldBe otherTeam.id
                result.otherTeam.members.size shouldBe 2
            }
        }

        context("채팅방에 소속된 사용자가 아닐경우") {
            it("예외를 발생시킨다") {
                // arrange
                val me = UserFixtureFactory.create()
                UserAuthenticationFixtureFactory
                    .create(user = me)
                    .also { SecurityContextHolder.setContext(UserSecurityContext(it)) }


                val otherTeamLeaderUser = UserFixtureFactory.create()
                val otherTeamMemberUser = UserFixtureFactory.create()


                val otherTeam = MeetingTeamFixtureFactory.create(
                    leader = otherTeamLeaderUser,
                    members = listOf(otherTeamMemberUser),
                ).also { every { getMeetingTeam.getById(it.id) } returns it }

                val myTeamIds = listOf(otherTeamLeaderUser.id, otherTeamMemberUser.id)
                every { getUser.findAllByIds(myTeamIds) } returns listOf(
                    otherTeamLeaderUser,
                    otherTeamMemberUser
                )

                val otherTeam2LeaderUser = UserFixtureFactory.create()
                val otherTeam2MemberUser = UserFixtureFactory.create()

                val otherTeam2 = MeetingTeamFixtureFactory.create(
                    leader = otherTeam2LeaderUser,
                    members = listOf(otherTeam2MemberUser),
                ).also { every { getMeetingTeam.getById(it.id) } returns it }

                val otherTeamIds = listOf(otherTeam2LeaderUser.id, otherTeam2MemberUser.id)
                every { getUser.findAllByIds(otherTeamIds) } returns listOf(
                    otherTeam2LeaderUser,
                    otherTeam2MemberUser
                )

                val chatRoom = ChatRoomFixtureFactory.create(
                    requestingTeamId = otherTeam.id,
                    receivingTeamId = otherTeam2.id,
                ).also { chatRoomRepository.save(it) }

                // act & assert
                shouldThrow<IllegalArgumentException> {
                    sut.getDetailById(chatRoom.id)
                }
            }
        }
    }


})
