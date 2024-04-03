package com.studentcenter.weave.domain.chat.entity

import com.studentcenter.weave.domain.chat.entity.ChatRoom
import com.studentcenter.weave.domain.meeting.entity.MeetingFixtureFactory
import com.studentcenter.weave.domain.meeting.enums.MeetingStatus
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe


@DisplayName("ChatRoomTest")
class ChatRoomTest : DescribeSpec({

    describe("채팅방 생성 테스트") {
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

})
