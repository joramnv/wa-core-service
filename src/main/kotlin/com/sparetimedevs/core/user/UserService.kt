package com.sparetimedevs.core.user

import com.sparetimedevs.core.login.Login
import com.sparetimedevs.core.user.exception.UserNotFoundException
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class UserService(
		private val userRepository: UserRepository
) {
	fun getUserId(login: Login): UUID {
		val user = userRepository.findByEmailAddress(login.emailAddress)
				.orElseThrow { UserNotFoundException("User with e-mail address " + login.emailAddress + " not found.") }
		return user.id
	}
}
