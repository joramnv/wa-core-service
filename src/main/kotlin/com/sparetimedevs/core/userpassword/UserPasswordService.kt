package com.sparetimedevs.core.userpassword

import org.springframework.stereotype.Component
import java.util.UUID

@Component
class UserPasswordService(
		private val userPasswordRepository: UserPasswordRepository
) {
	fun getUserPassword(userId: UUID): UserPassword {
		return userPasswordRepository.findByUserId(userId)
				.orElseThrow { RuntimeException("User password not found.") } //TODO throw different error.
	}
}
