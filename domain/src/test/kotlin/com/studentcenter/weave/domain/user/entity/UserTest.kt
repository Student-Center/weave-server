package com.studentcenter.weave.domain.user.entity

import com.studentcenter.weave.domain.user.enums.Gender
import com.studentcenter.weave.domain.user.vo.BirthYear
import com.studentcenter.weave.domain.user.vo.Mbti
import com.studentcenter.weave.domain.user.vo.Nickname
import com.studentcenter.weave.support.common.uuid.UuidCreator
import com.studentcenter.weave.support.common.vo.Email
import com.studentcenter.weave.support.common.vo.Url
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class UserTest : DescribeSpec({

    describe("유저 생성") {
        it("유저 생성") {
            // arrange
            val nickname = Nickname("닉네임")
            val email = Email("test@test.com")
            val gender = Gender.MAN
            val birthYear = BirthYear(1999)
            val universityId = UuidCreator.create()
            val majorId = UuidCreator.create()
            val mbti = Mbti("EnTJ")

            // act
            val user = User.create(
                nickname = nickname,
                email = email,
                gender = gender,
                mbti = mbti,
                birthYear = birthYear,
                universityId = universityId,
                majorId = majorId,
            )

            // assert
            user.nickname shouldBe nickname
            user.email shouldBe email
            user.gender shouldBe gender
            user.mbti shouldBe mbti
            user.birthYear shouldBe birthYear
            user.universityId shouldBe universityId
            user.majorId shouldBe majorId
        }
    }

    describe("프로필 이미지 업데이트") {
        it("프로필 이미지 업데이트") {
            // arrange
            val user = UserFixtureFactory.create()
            val imageId = UuidCreator.create()
            val extension = UserProfileImage.Extension.JPG

            val profileImageUrl = Url("https://test.com")


            // act
            val updatedUser = user.updateProfileImage(
                imageId = imageId,
                extension = extension,
                getProfileImageSourceAction = { _, _ -> profileImageUrl },
            )

            // assert
            updatedUser.profileImages.size shouldBe 1
            updatedUser.profileImages.first().imageUrl shouldBe profileImageUrl
        }
    }

})
