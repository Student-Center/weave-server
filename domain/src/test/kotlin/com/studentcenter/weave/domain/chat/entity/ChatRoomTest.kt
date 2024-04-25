package com.studentcenter.weave.domain.chat.entity

import com.studentcenter.weave.domain.meeting.entity.MeetingFixtureFactory
import com.studentcenter.weave.domain.meeting.enums.MeetingStatus
import com.studentcenter.weave.support.common.uuid.UuidCreator
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import java.util.*


@DisplayName("ChatRoomTest")
class ChatRoomTest : DescribeSpec({

    describe("채팅방 생성") {
        context("미팅이 매칭된 상태일 경우") {
            it("미팅 팀을 통해 채팅방을 생성한다") {
                // arrange
                val meeting = MeetingFixtureFactory.create(
                    status = MeetingStatus.COMPLETED,
                )

                // act
                val chatRoom = ChatRoom.create(meeting)

                // assert
                chatRoom.receivingTeamId shouldBe meeting.receivingTeamId
                chatRoom.requestingTeamId shouldBe meeting.requestingTeamId
            }
        }

        MeetingStatus
            .entries
            .filter { it != MeetingStatus.COMPLETED }
            .forEach { status ->
                context("미팅이 매칭된 상태가 아닐 경우 - $status") {
                    it("예외를 발생시킨다") {
                        // arrange
                        val meeting = MeetingFixtureFactory.create(
                            status = status,
                        )

                        // act & assert
                        val exception = shouldThrow<IllegalArgumentException> {
                            ChatRoom.create(meeting)
                        }
                        exception.message shouldBe "미팅이 매칭되지 않았습니다."
                    }
                }
            }
    }

    describe("채팅 멤버 추가") {
        context("멤버가 존재하지 않는 경우") {
            it("멤버를 추가한다") {
                // arrange
                val chatRoom: ChatRoom = ChatRoomFixtureFactory.create()
                val userId: UUID = UuidCreator.create()
                val meetingTeamId: UUID = UuidCreator.create()

                // act
                val newMember: ChatRoom = chatRoom.addMember(userId, meetingTeamId)

                // assert
                newMember.members.size shouldBe 1
            }
        }

        context("멤버가 이미 존재하는 경우") {
            it("멤버를 추가하지 않는다") {
                // arrange
                val userId: UUID = UuidCreator.create()
                val meetingTeamId: UUID = UuidCreator.create()

                val chatMember: ChatMember = ChatMemberFixtureFactory.create(
                    userId = userId,
                    meetingTeamId = meetingTeamId
                )
                val chatRoom: ChatRoom = ChatRoomFixtureFactory.create(
                    members = listOf(chatMember)
                )

                // act
                val newMember: ChatRoom = chatRoom.addMember(userId, meetingTeamId)

                // assert
                newMember.members.size shouldBe 1
            }
        }
    }

})
