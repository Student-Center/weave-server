package com.studentcenter.weave.domain.chat.entity

import com.studentcenter.weave.domain.user.entity.UserFixtureFactory
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

@DisplayName("TextMessageTest")
class TextMessageTest : DescribeSpec({

    describe("채팅 텍스트 메시지 생성") {
        context("내용이 빈 문자열일 경우") {
            it("예외를 발생시킨다") {
                // arrange
                val roomId = ChatRoomFixtureFactory.create().id
                val sendUserId = UserFixtureFactory.create().id
                val content = ""

                // act & assert
                val exception = shouldThrow<IllegalArgumentException> {
                    TextMessage.create(
                        roomId = roomId,
                        sendUserId = sendUserId,
                        content = content,
                    )
                }
                exception.message shouldBe "내용을 입력해 주세요!"
            }
        }

        context("내용이 1000자를 초과할 경우") {
            it("예외를 발생시킨다") {
                // arrange
                val roomId = ChatRoomFixtureFactory.create().id
                val sendUserId = UserFixtureFactory.create().id
                val content = "a".repeat(1001)

                // act & assert
                val exception = shouldThrow<IllegalArgumentException> {
                    TextMessage.create(
                        roomId = roomId,
                        sendUserId = sendUserId,
                        content = content,
                    )
                }
                exception.message shouldBe "1000자 이내로 입력해 주세요!"
            }
        }

        context("내용이 1000자 이내일 경우") {
            it("텍스트 메시지를 생성한다") {
                // arrange
                val roomId = ChatRoomFixtureFactory.create().id
                val sendUserId = UserFixtureFactory.create().id
                val content = "안녕하세요!"

                // act
                val textMessage = TextMessage.create(
                    roomId = roomId,
                    sendUserId = sendUserId,
                    content = content,
                )

                // assert
                textMessage.roomId shouldBe roomId
                textMessage.sendUserId shouldBe sendUserId
                textMessage.content shouldBe content
            }
        }
    }

})
