package com.studentcenter.weave.application.suggestion.service

import com.studentcenter.weave.application.suggestion.outbound.SuggestionRepositorySpy
import com.studentcenter.weave.application.suggestion.port.inbound.CreateSuggestion
import com.studentcenter.weave.application.user.vo.UserAuthenticationFixtureFactory
import com.studentcenter.weave.domain.user.entity.UserFixtureFactory
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

@DisplayName("CreateSuggestionService")
class CreateSuggestionServiceTest : DescribeSpec({

    val suggestionRepository = SuggestionRepositorySpy()
    val sut = CreateSuggestionService(suggestionRepository)

    afterEach {
        suggestionRepository.clear()
    }

    describe("제안 생성") {
        it("제안을 생성하고 DB에 저장한다") {
            // arrange
            val userFixture = UserFixtureFactory.create()
            val userAuthentication = UserAuthenticationFixtureFactory.create(userFixture)

            val command = CreateSuggestion.Command(
                userAuthentication = userAuthentication,
                contents = "contents"
            )

            // act
            sut.invoke(command)

            // assert
            suggestionRepository.count() shouldBe 1
        }
    }


})
