package com.studentcenter.weave.domain.entity

import com.studentcenter.weave.domain.enum.Gender
import com.studentcenter.weave.domain.enum.Mbti
import com.studentcenter.weave.domain.vo.BirthYear
import com.studentcenter.weave.domain.vo.Major
import com.studentcenter.weave.domain.vo.Nickname
import com.studentcenter.weave.domain.vo.University
import com.studentcenter.weave.support.common.vo.Email
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import java.time.Instant
import java.time.LocalDateTime

class UserTest : FunSpec({

    test("유저 생성") {
        // arrange
        val nickname = Nickname("닉네임")
        val email = Email("test@test.com")
        val gender = Gender.MAN
        val birthYear = BirthYear(1999)
        val university = University("서울대학교")
        val major = Major("컴퓨터 공학과")
        val mbti = Mbti.ENFJ

        // act
        val user = User.create(
            nickname = nickname,
            email = email,
            gender = gender,
            mbti = mbti,
            birthYear = birthYear,
            university = university,
            major = major,
        )

        // assert
        user.nickname shouldBe nickname
        user.email shouldBe email
        user.gender shouldBe gender
        user.mbti shouldBe mbti
        user.birthYear shouldBe birthYear
        user.major shouldBe major
    }

    test("test") {
        println(Instant.now())
        println(LocalDateTime.now())
    }


})
