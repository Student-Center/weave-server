package com.studentcenter.weave.application.chat.service

import com.studentcenter.weave.application.chat.outbound.ChatRoomRepositorySpy
import com.studentcenter.weave.domain.chat.entity.ChatRoom
import com.studentcenter.weave.domain.meeting.entity.MeetingFixtureFactory
import com.studentcenter.weave.domain.meeting.enums.MeetingStatus
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

@DisplayName("[서비스] 채팅방 생성")
class CreateChatRoomServiceTest : DescribeSpec({

    val chatRoomRepositorySpy = ChatRoomRepositorySpy()
    val sut = CreateChatRoomService(
        chatRoomRepository = chatRoomRepositorySpy,
    )

    afterEach {
        chatRoomRepositorySpy.clear()
    }

    describe("채팅방 생성") {
        context("매칭된 미팅 정보가 주어졌을때") {
            it("채팅방을 생성한다") {
                // arrange
                val meeting = MeetingFixtureFactory.create(status = MeetingStatus.COMPLETED)

                // act
                sut.invoke(meeting)

                // assert
                val savedChatRooms: List<ChatRoom> = chatRoomRepositorySpy.findAll()
                savedChatRooms.size shouldBe 1

                val savedChatRoom = savedChatRooms.first()
                savedChatRoom.meetingId shouldBe meeting.id
                savedChatRoom.requestingTeamId shouldBe meeting.requestingTeamId
                savedChatRoom.receivingTeamId shouldBe meeting.receivingTeamId
            }
        }

        MeetingStatus.entries.filter { it != MeetingStatus.COMPLETED }.forEach { status ->
            context("매칭되지 않은 미팅 정보가 주어졌을때 - $status") {
                it("예외를 발생시킨다") {
                    // arrange
                    val meeting = MeetingFixtureFactory.create(status = status)

                    // act & assert
                    shouldThrow<IllegalArgumentException> {
                        sut.invoke(meeting)
                    }
                }
            }
        }
    }


})
