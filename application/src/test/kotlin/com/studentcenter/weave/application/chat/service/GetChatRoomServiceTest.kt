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

    describe("채팅방 상세 정보 조회") {
        context("채팅방에 소속된 사용자면") {
            it("채팅방과 관련된 정보를 조회한다") {
                // arrange
                val meAsTeamLeaderUser = UserFixtureFactory
                    .create()
                    .also { every { getUser.getById(it.id) } returns it }

                UserAuthenticationFixtureFactory
                    .create(user = meAsTeamLeaderUser)
                    .also { SecurityContextHolder.setContext(UserSecurityContext(it)) }

                val myTeamMemberUser = UserFixtureFactory
                    .create()
                    .also { every { getUser.getById(it.id) } returns it }

                val otherTeamLeaderUser = UserFixtureFactory
                    .create()
                    .also { every { getUser.getById(it.id) } returns it }

                val otherTeamMemberUser = UserFixtureFactory
                    .create()
                    .also { every { getUser.getById(it.id) } returns it }

                val myTeam = MeetingTeamFixtureFactory
                    .create(
                        leader = meAsTeamLeaderUser,
                        members = listOf(myTeamMemberUser),
                    ).also { every { getMeetingTeam.getById(it.id) } returns it }

                val otherTeam = MeetingTeamFixtureFactory
                    .create(
                        leader = otherTeamLeaderUser,
                        members = listOf(otherTeamMemberUser),
                    ).also { every { getMeetingTeam.getById(it.id) } returns it }

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
                result.myTeam.id shouldBe myTeam.id
                result.myTeam.members.size shouldBe 2

                result.otherTeam.id shouldBe otherTeam.id
                result.otherTeam.members.size shouldBe 2
            }
        }

        context("채팅방에 소속된 사용자가 아닐경우") {
            it("예외를 발생시킨다") {
                // arrange
                UserFixtureFactory
                    .create()
                    .let { UserAuthenticationFixtureFactory.create(user = it) }
                    .also { SecurityContextHolder.setContext(UserSecurityContext(it)) }

                val otherTeam1LeaderUser = UserFixtureFactory
                    .create()
                    .also { every { getUser.getById(it.id) } returns it }

                val otherTeam1MemberUser = UserFixtureFactory
                    .create()
                    .also { every { getUser.getById(it.id) } returns it }

                val otherTeam1 = MeetingTeamFixtureFactory
                    .create(
                        leader = otherTeam1LeaderUser,
                        members = listOf(otherTeam1MemberUser),
                    ).also { every { getMeetingTeam.getById(it.id) } returns it }

                val otherTeam2LeaderUser = UserFixtureFactory
                    .create()
                    .also { every { getUser.getById(it.id) } returns it }

                val otherTeam2MemberUser = UserFixtureFactory
                    .create()
                    .also { every { getUser.getById(it.id) } returns it }

                val otherTeam2 = MeetingTeamFixtureFactory
                    .create(
                        leader = otherTeam2LeaderUser,
                        members = listOf(otherTeam2MemberUser),
                    ).also { every { getMeetingTeam.getById(it.id) } returns it }

                val chatRoom = ChatRoomFixtureFactory
                    .create(
                        requestingTeamId = otherTeam1.id,
                        receivingTeamId = otherTeam2.id,
                    )
                    .also { chatRoomRepository.save(it) }

                // act & assert
                shouldThrow<IllegalArgumentException> {
                    sut.getDetailById(chatRoom.id)
                }
            }
        }
    }


})
