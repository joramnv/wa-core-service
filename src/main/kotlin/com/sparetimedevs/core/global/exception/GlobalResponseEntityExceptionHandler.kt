package com.sparetimedevs.core.global.exception

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class GlobalResponseEntityExceptionHandler : ResponseEntityExceptionHandler() {

	@ExceptionHandler(value = [IllegalArgumentException::class, IllegalStateException::class])
	fun handleConflict(ex: RuntimeException, request: WebRequest): ResponseEntity<Any> {
		return handleExceptionInternal(ex, ex.message, HttpHeaders(), HttpStatus.CONFLICT, request) //TODO change this status
	}
}
