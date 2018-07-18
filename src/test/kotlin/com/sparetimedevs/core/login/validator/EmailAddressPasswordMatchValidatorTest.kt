package com.sparetimedevs.core.login.validator

import com.sparetimedevs.core.login.Login
import com.sparetimedevs.core.login.exception.EmailAddressPasswordDoNotMatchException
import com.sparetimedevs.core.user.UserService
import com.sparetimedevs.core.userpassword.UserPassword
import com.sparetimedevs.core.userpassword.UserPasswordService
import io.kotlintest.matchers.string.contain
import io.kotlintest.should
import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import io.kotlintest.shouldThrowExactly
import io.kotlintest.specs.StringSpec
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import test.EMAIL_ADDRESS_1
import test.PASSWORD_1
import test.PASSWORD_2
import test.userId1
import test.userPasswordId1

class EmailAddressPasswordMatchValidatorTest : StringSpec({

	val userService: UserService = mock(UserService::class.java)
	val userPasswordService: UserPasswordService = mock(UserPasswordService::class.java)
	val loginValidator = LoginValidator(userService, userPasswordService)

	val userPassword = UserPassword(userPasswordId1, userId1, PASSWORD_1)

	"given matching email address and password when validate then users id is returned" {
		val login = Login(EMAIL_ADDRESS_1, PASSWORD_1)

		`when`(userService.getUserId(login)).thenReturn(userId1)
		`when`(userPasswordService.getUserPassword(userId1)).thenReturn(userPassword)

		val returnedUserId = loginValidator.validate(login)

		userPassword.password shouldBe PASSWORD_1
		returnedUserId shouldBe userId1
	}

	"given matching email address and password when validate then EmailAddressPasswordDoNotMatchException is thrown" {
		val login = Login(EMAIL_ADDRESS_1, PASSWORD_2)

		`when`(userService.getUserId(login)).thenReturn(userId1)
		`when`(userPasswordService.getUserPassword(userId1)).thenReturn(userPassword)

		val exception = shouldThrowExactly<EmailAddressPasswordDoNotMatchException> {
			loginValidator.validate(login)
		}
		exception.message should contain("E-mail address password combination do not match for e-mail address ")
		userPassword.password shouldNotBe PASSWORD_2
	}
})
