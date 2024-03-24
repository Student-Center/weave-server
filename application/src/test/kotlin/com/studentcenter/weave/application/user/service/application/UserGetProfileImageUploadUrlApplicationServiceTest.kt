package com.studentcenter.weave.application.user.service.application

import com.studentcenter.weave.application.user.port.inbound.UserGetProfileImageUploadUrlUseCase
import com.studentcenter.weave.application.user.port.outbound.UserProfileImageUrlPortStub
import com.studentcenter.weave.application.user.vo.ImageFileExtension
import com.studentcenter.weave.support.common.vo.Url
import com.studentcenter.weave.support.security.context.SecurityContextHolder
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import java.util.*

@DisplayName("유저 프로필 이미지 업로드 URL 생성 테스트")
class UserGetProfileImageUploadUrlApplicationServiceTest : DescribeSpec({

    afterEach {
        SecurityContextHolder.clearContext()
    }


    describe("프로필 이미지 업로드 URL 생성") {
        it("업로드 URL과, 이미지 ID, 이미지 확장자를 반환한다.") {
            // arrange
            val imageFileExtension = ImageFileExtension.JPG
            val imageUrl =
                Url("https://s3.ap-northeast-2.amazonaws.com/weave-profile-image-upload-url")

            val stub: UserProfileImageUrlPortStub = object : UserProfileImageUrlPortStub() {
                override fun getUploadImageUrl(
                    imageId: UUID,
                    imageFileExtension: ImageFileExtension
                ): Url {
                    return imageUrl
                }
            }
            val sut = UserGetProfileImageUploadUrlApplicationService(stub)


            // act
            val result: UserGetProfileImageUploadUrlUseCase.Result = sut.invoke(imageFileExtension)

            // assert
            result.imageId.shouldBeInstanceOf<UUID>()
            result.uploadUrl.shouldBe(imageUrl)
            result.imageFileExtension.shouldBe(imageFileExtension)
        }
    }

})
