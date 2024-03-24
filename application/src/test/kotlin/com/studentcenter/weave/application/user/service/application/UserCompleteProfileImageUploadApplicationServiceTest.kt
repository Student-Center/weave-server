package com.studentcenter.weave.application.user.service.application

import com.studentcenter.weave.application.common.security.context.UserSecurityContext
import com.studentcenter.weave.application.user.port.inbound.UserCompleteProfileImageUploadUseCase
import com.studentcenter.weave.application.user.port.outbound.UserProfileImageUrlPortStub
import com.studentcenter.weave.application.user.port.outbound.UserRepositorySpy
import com.studentcenter.weave.application.user.vo.UserAuthenticationFixtureFactory
import com.studentcenter.weave.domain.user.entity.UserFixtureFactory
import com.studentcenter.weave.domain.user.entity.UserProfileImage
import com.studentcenter.weave.support.common.exception.CustomException
import com.studentcenter.weave.support.common.vo.Url
import com.studentcenter.weave.support.security.context.SecurityContextHolder
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import java.util.*

@DisplayName("UserCompleteProfileImageUploadApplicationServiceTest")
class UserCompleteProfileImageUploadApplicationServiceTest : DescribeSpec({

    val userRepository = UserRepositorySpy()

    afterEach {
        SecurityContextHolder.clearContext()
        userRepository.clear()
    }

    describe("유저 프로필 이미지 업로드 완료") {
        context("[성공] 로그인 한 상태인 경우") {
            it("유저의 프로필 이미지를 업데이트 한다") {
                // arrange
                val user = UserFixtureFactory
                    .create()
                    .also { userRepository.save(it) }
                UserAuthenticationFixtureFactory.create(user)
                    .also { SecurityContextHolder.setContext(UserSecurityContext(it)) }

                val profileImageUrl = Url("http://localhost:8080/image.jpg")

                val userProfileImageUrlPortStub = object : UserProfileImageUrlPortStub() {
                    override fun findByIdAndExtension(
                        imageId: UUID,
                        extension: UserProfileImage.Extension
                    ): Url {
                        return profileImageUrl
                    }
                }

                val sut = UserCompleteProfileImageUploadApplicationService(
                    userProfileImageUrlPortStub,
                    userRepository
                )

                // act
                sut.invoke(
                    UserCompleteProfileImageUploadUseCase.Command(
                        imageId = UUID.randomUUID(),
                        extension = UserProfileImage.Extension.JPEG
                    )
                )

                // assert
                val updatedUser = userRepository.getById(user.id)
                updatedUser.avatar shouldBe profileImageUrl
            }
        }

        context("[실패] 로그인 한 상태가 아닌 경우") {
            it("CustomException(type=AUTH-002)이 발생한다") {
                // arrange
                val sut = UserCompleteProfileImageUploadApplicationService(
                    UserProfileImageUrlPortStub(),
                    userRepository
                )

                // act & assert
                val exception = shouldThrow<CustomException> {
                    sut.invoke(
                        UserCompleteProfileImageUploadUseCase.Command(
                            imageId = UUID.randomUUID(),
                            extension = UserProfileImage.Extension.JPEG
                        )
                    )
                }
                exception.type.code shouldBe "AUTH-002"
            }

        }
    }

})
