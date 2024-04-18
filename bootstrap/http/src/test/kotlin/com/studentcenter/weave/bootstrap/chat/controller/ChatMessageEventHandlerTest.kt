package com.studentcenter.weave.bootstrap.chat.controller

import com.studentcenter.weave.application.chat.outbound.ChatMessageRepositorySpy
import com.studentcenter.weave.application.chat.service.SaveChatMessageService
import com.studentcenter.weave.domain.chat.entity.ChatMessageFixtureFactory
import com.studentcenter.weave.domain.chat.event.ChatMessageConsumeEvent
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.springframework.messaging.simp.SimpMessageSendingOperations

@DisplayName("ChatMessageEventHandler")
class ChatMessageEventHandlerTest : DescribeSpec({

    val simpMessageSendingOperations = mockk<SimpMessageSendingOperations>()
    val chatMessageRepositorySpy = ChatMessageRepositorySpy()
    val saveChatMessage = SaveChatMessageService(chatMessageRepositorySpy)

    val sut = ChatMessageEventHandler(
        simpMessageSendingOperations, saveChatMessage
    )

    beforeTest {
        chatMessageRepositorySpy.clear()
    }

    describe("handleChatMessageConsumeEvent") {
        context("채팅 메시지 이벤트를 받았을때") {
            it("채팅 메시지를 저장하고 브로드캐스트 한다") {
                // arrange
                val chatMessage = ChatMessageFixtureFactory.create()
                val chatMessageConsumeEvent = ChatMessageConsumeEvent.from(chatMessage)

                every {
                    simpMessageSendingOperations.convertAndSend(any(), chatMessage)
                } returns Unit

                // act
                runTest {
                    sut.handleChatMessageConsumeEvent(chatMessageConsumeEvent)
                }

                // assert
                chatMessageRepositorySpy.assertSaved(chatMessage)
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
