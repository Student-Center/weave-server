package com.studentcenter.weave.bootstrap.chat.controller

import com.studentcenter.weave.application.chat.outbound.ChatMessageRepositorySpy
import com.studentcenter.weave.domain.chat.entity.ChatMessageFixtureFactory
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.springframework.messaging.simp.SimpMessageSendingOperations

@DisplayName("ChatMessageHandler")
class ChatMessageHandlerTest : DescribeSpec({

    val simpMessageSendingOperations = mockk<SimpMessageSendingOperations>()
    val chatMessageRepositorySpy = ChatMessageRepositorySpy()
    val sut = ChatMessageHandler(simpMessageSendingOperations)

    beforeTest {
        chatMessageRepositorySpy.clear()
    }

    describe("handleChatMessage") {
        context("채팅 메시지 이벤트를 받았을때") {
            it("채팅 메시지를 브로드캐스트 한다") {
                // arrange
                val chatMessage = ChatMessageFixtureFactory.create()

                every {
                    simpMessageSendingOperations.convertAndSend(any(), chatMessage)
                } returns Unit

                // act
                sut.handleChatMessage(chatMessage)

                // assert
                verify {
                    simpMessageSendingOperations.convertAndSend(
                        /* destination = */ "/topic/chat-rooms/${chatMessage.roomId}",
                        /* payload = */chatMessage
                    )
                }
            }
        }
    }


})
