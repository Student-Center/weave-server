package com.studentcenter.weave.domain.user.entity

import com.studentcenter.weave.domain.user.exception.UserException
import com.studentcenter.weave.support.common.uuid.UuidCreator
import com.studentcenter.weave.support.common.vo.Url
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import java.util.*

@DisplayName("UserProfileImage")
class UserProfileImageTest : DescribeSpec({


    describe("회원 프로필 이미지 생성") {
        context("회원 프로필 이미지 URL 조회시 예외가 발생했을 경우") {
            it("프로필 이미지 예외를 발생시킨다") {
                // arrange
                val imageId = UuidCreator.create()
                val extension = UserProfileImage.Extension.JPG
                val getProfileImageUrl: (UUID, UserProfileImage.Extension) -> Url =
                    { _, _ -> throw Exception() }

                // act, assert
                shouldThrow<UserException.UserProfileImageUploadFailed> {
                    UserProfileImage.create(imageId, extension, getProfileImageUrl)
                }
            }
        }

        context("회원 프로필 이미지 URL 조회시 정상적으로 조회되었을 경우") {
            it("회원 프로필 이미지를 생성한다.") {
                // arrange
                val imageId = UuidCreator.create()
                val extension = UserProfileImage.Extension.JPG
                val imageUrl = Url("http://localhost:8080/image.jpg")
                val getProfileImageUrl: (UUID, UserProfileImage.Extension) -> Url =
                    { _, _ -> imageUrl }

                // act
                val userProfileImage =
                    UserProfileImage.create(imageId, extension, getProfileImageUrl)

                // assert
                userProfileImage.id shouldBe imageId
                userProfileImage.extension shouldBe extension
                userProfileImage.imageUrl shouldBe imageUrl
            }
        }
    }

})
