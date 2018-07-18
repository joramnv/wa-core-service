package com.sparetimedevs.core.login

import com.sparetimedevs.core.login.validator.LoginValidator
import com.sparetimedevs.core.quest.QuestController
import com.sparetimedevs.core.userpassword.UserPasswordController
import org.springframework.hateoas.MediaTypes.HAL_JSON_VALUE
import org.springframework.hateoas.Resource
import org.springframework.hateoas.Resources
import org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo
import org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn
import org.springframework.http.HttpStatus.OK
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod.GET
import org.springframework.web.bind.annotation.RequestMethod.POST
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import java.util.ArrayList

@RestController
@RequestMapping(value = ["/login"])
class LoginController(
		private val loginValidator: LoginValidator
) {
	@RequestMapping(method = [POST], consumes = [APPLICATION_JSON_VALUE], produces = [HAL_JSON_VALUE])
	fun login(@RequestBody loginResource: Resource<Login>?): ResponseEntity<Login> {
		val login = loginResource!!.content

		val userId = loginValidator.validate(login)

		val linkToSelf = linkTo(methodOn(LoginController::class.java).login(loginResource)).withSelfRel()
		val linkToUserQuests = linkTo(methodOn(QuestController::class.java).findUserQuests(userId)).withRel("user-quests")
		val linkToUserPassword = linkTo(UserPasswordController::class.java).withRel("save-password")

		login.add(linkToSelf)
		login.add(linkToUserQuests)
		login.add(linkToUserPassword)

		return ResponseEntity(login, OK)
	}

	@RequestMapping(method = [GET], produces = [HAL_JSON_VALUE])
	@ResponseBody
	fun login(): ResponseEntity<*> {
		val producers = ArrayList<String>()
		val resources = Resources(producers)
		resources.add(linkTo(methodOn(LoginController::class.java).login()).withSelfRel())
		return ResponseEntity.ok(resources)
	}
}
