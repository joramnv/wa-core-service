package com.sparetimedevs.core.login.validator

import com.sparetimedevs.core.login.Login
import com.sparetimedevs.core.login.exception.EmailAddressPasswordDoNotMatchException
import com.sparetimedevs.core.user.UserService
import com.sparetimedevs.core.userpassword.UserPasswordService
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class LoginValidator(
		private val userService: UserService,
		private val userPasswordService: UserPasswordService
) {
    fun validate(login: Login): UUID {
	    val userId = userService.getUserId(login)
        val userPassword = userPasswordService.getUserPassword(userId)

        if (login.password != userPassword.password) {
            throw EmailAddressPasswordDoNotMatchException("E-mail address password combination do not match for e-mail address " + login.emailAddress + ".")
        }
	    return userId
    }
}
