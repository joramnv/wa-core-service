package com.sparetimedevs.core.login.exception

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus.UNAUTHORIZED
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class LoginResponseEntityExceptionHandler : ResponseEntityExceptionHandler() {

	@ExceptionHandler(value = [EmailAddressPasswordDoNotMatchException::class, IllegalArgumentException::class, IllegalStateException::class])
	fun handleConflict(ex: RuntimeException, request: WebRequest): ResponseEntity<Any> {
		return handleExceptionInternal(ex, ex.message, HttpHeaders(), UNAUTHORIZED, request)
	}
}
