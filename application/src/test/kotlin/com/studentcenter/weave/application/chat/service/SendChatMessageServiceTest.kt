package com.studentcenter.weave.application.chat.service

import com.studentcenter.weave.application.chat.outbound.ChatMessagePublisherSpy
import com.studentcenter.weave.application.chat.outbound.ChatMessageRepositorySpy
import com.studentcenter.weave.domain.chat.entity.ChatMessageFixtureFactory
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runTest

@DisplayName("SendChatMessageService")
class SendChatMessageServiceTest : DescribeSpec({

    val chatMessagePublisher = ChatMessagePublisherSpy()
    val chatMessageRepository = ChatMessageRepositorySpy()

    val sut = SendChatMessageService(
        chatMessagePublisher = chatMessagePublisher,
        chatMessageRepository = chatMessageRepository,
    )

    afterEach {
        chatMessagePublisher.clear()
        chatMessageRepository.clear()
    }

    describe("채팅 메시지 전송") {
        it("채팅 메시지를 큐에 전달하고, DB에 저장한다.") {
            // arrange
            val chatMessage = ChatMessageFixtureFactory.create()

            // act
            runTest {
                sut.invoke(
                    roomId = chatMessage.roomId,
                    userId = chatMessage.senderId,
                    contents = chatMessage.contents,
                )
            }

            // assert
            chatMessagePublisher.count() shouldBe 1
            chatMessageRepository.findAllByUserIdAndRoomId(
                chatMessage.senderId,
                chatMessage.roomId
            ).size shouldBe 1
        }

    }


})
