package com.studentcenter.weave.application.user.service.application

import com.studentcenter.weave.application.common.security.context.UserSecurityContext
import com.studentcenter.weave.application.user.port.outbound.UserProfileImageUrlPortStub
import com.studentcenter.weave.application.user.port.outbound.UserRepositorySpy
import com.studentcenter.weave.application.user.vo.UserAuthenticationFixtureFactory
import com.studentcenter.weave.domain.user.entity.UserFixtureFactory
import com.studentcenter.weave.support.common.exception.CustomException
import com.studentcenter.weave.support.common.vo.Url
import com.studentcenter.weave.support.security.context.SecurityContextHolder
import io.kotest.assertions.asClue
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

    describe("유저 프로필 이미지 업로드 완료 테스트") {
        context("[실패] 업로드된 프로필 이미지가 없는경우") {
            it("CustomException(type: UserExceptionType.USER_PROFILE_IMAGE_UPLOAD_FAILED)이 발생한다") {
                // arrange
                val user = UserFixtureFactory
                    .create()
                    .also { userRepository.save(it) }
                UserAuthenticationFixtureFactory.create(user)
                    .also { SecurityContextHolder.setContext(UserSecurityContext(it)) }

                val userProfileImageUrlPortStub = object : UserProfileImageUrlPortStub() {
                    override fun findAllByUserId(userId: UUID): List<Url> {
                        return emptyList()
                    }
                }

                val sut = UserCompleteProfileImageUploadApplicationService(
                    userProfileImageUrlPortStub,
                    userRepository
                )

                // act & assert
                shouldThrow<CustomException> { sut.invoke() }
            }
        }

        context("[성공] 업로드된 프로필 이미지가 1개 있고, avatar가 null인 경우") {
            it("avatar가 업로드된 프로필 이미지로 업데이트 된다") {
                // arrange
                val imageUrl = "http://profile-image.com/1.png"
                val user = UserFixtureFactory
                    .create()
                    .also { userRepository.save(it) }
                UserAuthenticationFixtureFactory.create(user)
                    .also { SecurityContextHolder.setContext(UserSecurityContext(it)) }

                val userProfileImageUrlPortStub = object : UserProfileImageUrlPortStub() {
                    override fun findAllByUserId(userId: UUID): List<Url> {
                        return listOf(Url(imageUrl))
                    }
                }

                val sut = UserCompleteProfileImageUploadApplicationService(
                    userProfileImageUrlPortStub,
                    userRepository
                )

                // act
                sut.invoke()

                // assert
                userRepository
                    .findById(user.id)
                    .asClue { it!!.avatar shouldBe Url(imageUrl) }
            }
        }

        context("[성공] 업로드된 프로필 이미지가 1개 있고, avartar와 일치하는 경우") {
            it("아무런 작업을 하지 않는다.") {
                // arrange
                val imageUrl = "http://profile-image.com/1.png"
                val user = UserFixtureFactory
                    .create()
                    .also { userRepository.save(it) }
                UserAuthenticationFixtureFactory.create(user)
                    .also { SecurityContextHolder.setContext(UserSecurityContext(it)) }

                val userProfileImageUrlPortStub = object : UserProfileImageUrlPortStub() {
                    override fun findAllByUserId(userId: UUID): List<Url> {
                        return listOf(Url(imageUrl))
                    }
                }

                val sut = UserCompleteProfileImageUploadApplicationService(
                    userProfileImageUrlPortStub,
                    userRepository
                )

                // act
                sut.invoke()

                // assert
                userRepository
                    .findById(user.id)
                    .asClue { it!!.avatar shouldBe Url(imageUrl) }
            }
        }

        context("[실패] 업로드된 프로필 이미지가 1개 있고, avatar와 다른 경우") {
            it(
                """
                업로드된 모든 프로필 이미지를 삭제하고,
                CustomException(type: UserExceptionType.USER_PROFILE_IMAGE_UPLOAD_FAILED)이 발생한다
            """.trimIndent()
            ) {
                // arrange
                val imageUrl = "http://profile-image.com/1.png"
                val anotherImageUrl = "http://profile-image.com/2.png"
                var deleteCalled = false

                val user = UserFixtureFactory
                    .create(avatar = Url(imageUrl))
                    .also { userRepository.save(it) }
                UserAuthenticationFixtureFactory.create(user)
                    .also { SecurityContextHolder.setContext(UserSecurityContext(it)) }

                val userProfileImageUrlPortStub = object : UserProfileImageUrlPortStub() {
                    override fun findAllByUserId(userId: UUID): List<Url> {
                        return listOf(Url(anotherImageUrl))
                    }

                    override fun deleteByUrl(url: Url) {
                        if (url == Url(anotherImageUrl)) {
                            deleteCalled = true
                        }
                    }
                }

                val sut = UserCompleteProfileImageUploadApplicationService(
                    userProfileImageUrlPortStub,
                    userRepository
                )

                // act & assert
                shouldThrow<CustomException> { sut.invoke() }
            }
        }

        context("[실패] 업로드된 프로필 이미지가 2개 있고, avatar가 null인 경우") {
            it(
                """
                업로드된 모든 프로필 이미지를 삭제하고, 
                CustomException(type: UserExceptionType.USER_PROFILE_IMAGE_UPLOAD_FAILED)이 발생한다
                """.trimIndent()
            ) {
                // arrange
                val imageUrl1 = "http://profile-image.com/1.png"
                val imageUrl2 = "http://profile-image.com/2.png"
                var deleteCalledCount = 0

                val user = UserFixtureFactory
                    .create()
                    .also { userRepository.save(it) }
                UserAuthenticationFixtureFactory.create(user)
                    .also { SecurityContextHolder.setContext(UserSecurityContext(it)) }

                val userProfileImageUrlPortStub = object : UserProfileImageUrlPortStub() {
                    override fun findAllByUserId(userId: UUID): List<Url> {
                        return listOf(Url(imageUrl1), Url(imageUrl2))
                    }

                    override fun deleteByUrl(url: Url) {
                        deleteCalledCount++
                    }
                }

                val sut = UserCompleteProfileImageUploadApplicationService(
                    userProfileImageUrlPortStub,
                    userRepository
                )

                // act & assert
                shouldThrow<CustomException> { sut.invoke() }
                    .also { deleteCalledCount shouldBe 2 }
            }
        }

        context("[성공] 업로드된 프로필 이미지가 2개 있고, avatar가 이미지 중 하나와 일치하는 경우") {
            it("avatar와 일치하는 이미지를 삭제하고, 나머지 이미지로 avatar를 업데이트 한다") {
                // arrange
                val imageUrl1 = "http://profile-image.com/1.png"
                val imageUrl2 = "http://profile-image.com/2.png"
                var deleteCalled = false

                val user = UserFixtureFactory
                    .create(avatar = Url(imageUrl1))
                    .also { userRepository.save(it) }
                UserAuthenticationFixtureFactory.create(user)
                    .also { SecurityContextHolder.setContext(UserSecurityContext(it)) }

                val userProfileImageUrlPortStub = object : UserProfileImageUrlPortStub() {
                    override fun findAllByUserId(userId: UUID): List<Url> {
                        return listOf(Url(imageUrl1), Url(imageUrl2))
                    }

                    override fun deleteByUrl(url: Url) {
                        if (url == Url(imageUrl1)) {
                            deleteCalled = true
                        }
                    }
                }

                val sut = UserCompleteProfileImageUploadApplicationService(
                    userProfileImageUrlPortStub,
                    userRepository
                )

                // act
                sut.invoke()

                // assert
                userRepository
                    .findById(user.id)
                    .asClue { it!!.avatar shouldBe Url(imageUrl2) }
                    .also { deleteCalled shouldBe true }
            }
        }

        context("[실패] 업로드된 프로필 이미지가 2개 있고, avatar가 이미지와 모두 일치하지 않는 경우") {
            it(
                """
                업로드된 모든 프로필 이미지를 삭제하고,
                CustomException(type: UserExceptionType.USER_PROFILE_IMAGE_UPLOAD_FAILED)이 발생한다
                """.trimIndent()
            ) {
                // arrange
                val imageUrl1 = "http://profile-image.com/1.png"
                val imageUrl2 = "http://profile-image.com/2.png"
                val anotherImageUrl = "http://profile-image.com/3.png"
                var deleteCalledCount = 0

                val user = UserFixtureFactory
                    .create(avatar = Url(anotherImageUrl))
                    .also { userRepository.save(it) }
                UserAuthenticationFixtureFactory.create(user)
                    .also { SecurityContextHolder.setContext(UserSecurityContext(it)) }

                val userProfileImageUrlPortStub = object : UserProfileImageUrlPortStub() {
                    override fun findAllByUserId(userId: UUID): List<Url> {
                        return listOf(Url(imageUrl1), Url(imageUrl2))
                    }

                    override fun deleteByUrl(url: Url) {
                        deleteCalledCount++
                    }
                }

                val sut = UserCompleteProfileImageUploadApplicationService(
                    userProfileImageUrlPortStub,
                    userRepository
                )

                // act & assert
                shouldThrow<CustomException> { sut.invoke() }
                    .also { deleteCalledCount shouldBe 2 }
            }
        }

        context("[성공] 업로드된 프로필 이미지가 3개 이상인 경우") {
            it("avatar와 일치하지 않는 이미지들을 모두 삭제한다") {
                // arrange
                val imageUrl1 = "http://profile-image.com/1.png"
                val imageUrl2 = "http://profile-image.com/2.png"
                val imageUrl3 = "http://profile-image.com/3.png"
                val deletedImageUrls = mutableListOf<Url>()

                val user = UserFixtureFactory
                    .create(avatar = Url(imageUrl1))
                    .also { userRepository.save(it) }
                UserAuthenticationFixtureFactory.create(user)
                    .also { SecurityContextHolder.setContext(UserSecurityContext(it)) }

                val userProfileImageUrlPortStub = object : UserProfileImageUrlPortStub() {
                    override fun findAllByUserId(userId: UUID): List<Url> {
                        return listOf(Url(imageUrl1), Url(imageUrl2), Url(imageUrl3))
                    }

                    override fun deleteByUrl(url: Url) {
                        deletedImageUrls.add(url)
                    }
                }

                val sut = UserCompleteProfileImageUploadApplicationService(
                    userProfileImageUrlPortStub,
                    userRepository
                )

                // act & assert
                shouldThrow<CustomException> { sut.invoke() }
                    .also {
                        deletedImageUrls shouldBe listOf(Url(imageUrl2), Url(imageUrl3))
                    }
            }
        }
    }


})
