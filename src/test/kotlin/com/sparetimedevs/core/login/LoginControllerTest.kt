package com.sparetimedevs.core.login

import com.sparetimedevs.core.login.validator.LoginValidator
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.springframework.hateoas.Resource
import org.springframework.http.HttpStatus
import test.EMAIL_ADDRESS_1
import test.PASSWORD_1
import test.userId1

internal class LoginControllerTest : StringSpec({

	val loginValidator: LoginValidator = mock(LoginValidator::class.java)
	val loginController = LoginController(loginValidator)

	"given matching email address and password when login then response login is returned??" { //TODO probably should not return a login object
		val login = Login(EMAIL_ADDRESS_1, PASSWORD_1)
		val loginResource: Resource<Login> = Resource(login)

		`when`(loginValidator.validate(login)).thenReturn(userId1)

		val response = loginController.login(loginResource)

		response.body!!.emailAddress shouldBe EMAIL_ADDRESS_1
		response.body!!.password shouldBe PASSWORD_1
		response.statusCode shouldBe HttpStatus.OK
	}

	//TODO write more tests.

})
