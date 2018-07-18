package com.sparetimedevs.core.user

import com.sparetimedevs.core.login.Login
import com.sparetimedevs.core.user.exception.UserNotFoundException
import io.kotlintest.matchers.string.contain
import io.kotlintest.should
import io.kotlintest.shouldBe
import io.kotlintest.shouldThrowExactly
import io.kotlintest.specs.StringSpec
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import test.EMAIL_ADDRESS_1
import test.PASSWORD_1
import test.userId1
import java.util.Optional

class UserServiceTest : StringSpec({

	val userRepository: UserRepository = mock(UserRepository::class.java)
	val userService = UserService(userRepository)

	val user = User(userId1, EMAIL_ADDRESS_1)
	val login = Login(user.emailAddress, PASSWORD_1)

	"given login with email address that is findable when get user id then users id is returned" {
		`when`(userRepository.findByEmailAddress(login.emailAddress)).thenReturn(Optional.of(user))

		val userId = userService.getUserId(login)
		userId shouldBe userId1
	}

	"given login with email address that is not findable when get user id then RuntimeException is thrown" {
		`when`(userRepository.findByEmailAddress(login.emailAddress)).thenReturn(Optional.empty())

		val exception = shouldThrowExactly<UserNotFoundException> {
			userService.getUserId(login)
		}
		exception.message should contain("User with e-mail address ")
	}
})
