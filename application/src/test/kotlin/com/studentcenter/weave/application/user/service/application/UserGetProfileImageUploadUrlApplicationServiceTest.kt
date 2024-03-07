package com.studentcenter.weave.application.user.service.application

import com.studentcenter.weave.application.common.security.context.UserSecurityContext
import com.studentcenter.weave.application.user.port.outbound.UserProfileImageUrlPortStub
import com.studentcenter.weave.application.user.vo.ImageFileExtension
import com.studentcenter.weave.application.user.vo.UserAuthenticationFixtureFactory
import com.studentcenter.weave.domain.user.entity.User
import com.studentcenter.weave.domain.user.entity.UserFixtureFactory
import com.studentcenter.weave.support.common.exception.CustomException
import com.studentcenter.weave.support.common.vo.Url
import com.studentcenter.weave.support.security.context.SecurityContextHolder
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import java.util.*

@DisplayName("유저 프로필 이미지 업로드 URL 생성 테스트")
class UserGetProfileImageUploadUrlApplicationServiceTest : DescribeSpec({

    afterEach {
        SecurityContextHolder.clearContext()
    }


    describe("프로필 이미지 업로드 URL 생성") {
        context("[성공] 회원이 인증된 사용자일 경우") {
            it("프로필 이미지 업로드 URL을 응답한다") {
                // arrange
                val user: User = UserFixtureFactory.create()
                UserAuthenticationFixtureFactory.create(user)
                    .let { SecurityContextHolder.setContext(UserSecurityContext(it)) }

                val imageFileExtension = ImageFileExtension.JPG
                val imageUrl =
                    Url("https://s3.ap-northeast-2.amazonaws.com/weave-profile-image-upload-url")

                val stub: UserProfileImageUrlPortStub = object : UserProfileImageUrlPortStub() {
                    override fun getUploadUrlByUserId(
                        userId: UUID,
                        imageFileExtension: ImageFileExtension
                    ): Url {
                        return imageUrl
                    }
                }
                val sut = UserGetProfileImageUploadUrlApplicationService(stub)


                // act
                val result: Url = sut.invoke(imageFileExtension)

                // assert
                result shouldBe imageUrl
            }
        }

        context("[실패] 인증된 사용자가 아닐경우") {
            it("CustomException을 발생시킨다") {
                // arrange
                val imageFileExtension = ImageFileExtension.JPG
                val sut =
                    UserGetProfileImageUploadUrlApplicationService(UserProfileImageUrlPortStub())

                // act & assert
                shouldThrow<CustomException> {
                    sut.invoke(imageFileExtension)
                }

            }
        }


    }

})
