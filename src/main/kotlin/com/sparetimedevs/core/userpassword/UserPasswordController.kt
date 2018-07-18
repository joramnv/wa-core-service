package com.sparetimedevs.core.userpassword

import com.sparetimedevs.core.login.Login
import com.sparetimedevs.core.user.User
import com.sparetimedevs.core.user.UserService
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks
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
import org.springframework.web.bind.annotation.RequestMethod.PUT
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping(path = ["/save-password"])
class UserPasswordController(
		private val repositoryEntityLinks: RepositoryEntityLinks,
		private val userPasswordRepository: UserPasswordRepository,
        private val userService: UserService
) {
    @RequestMapping(method = [POST], consumes = [APPLICATION_JSON_VALUE], produces = [HAL_JSON_VALUE])
    fun createPassword(@RequestBody loginResource: Resource<Login>?): ResponseEntity<Login> {
        val login = loginResource!!.content

	    val userId = userService.getUserId(login)

	    val userPassword = UserPassword(UUID.randomUUID(), userId, login.password)
        try {
            userPasswordRepository.save(userPassword)
        } catch (e: Exception) {
            //TODO catch right hibernate constraint exception.
        }

        login.add(linkTo(methodOn(UserPasswordController::class.java).createPassword(loginResource)).withSelfRel())

        val linkToUser = repositoryEntityLinks.linkToSingleResource(User::class.java, userId)
        login.add(linkToUser)

        return ResponseEntity(login, OK)
    }

    @RequestMapping(method = [PUT], consumes = [APPLICATION_JSON_VALUE], produces = [HAL_JSON_VALUE])
    fun updatePassword(@RequestBody loginResource: Resource<Login>): ResponseEntity<Login> { //TODO do not (miss) use Login object.
        val login = loginResource.content

        val userId = userService.getUserId(login)

        val oldUserPassword = userPasswordRepository.findByUserId(userId)
		        .orElseThrow { RuntimeException("User password for e-mail address " + login.emailAddress + " not found.") } //TODO throw different error.
        val updatedPassword = UserPassword(oldUserPassword.id, oldUserPassword.userId, login.password)
        userPasswordRepository.save(updatedPassword)

        val linkToUser = repositoryEntityLinks.linkToSingleResource(User::class.java, userId)
        login.add(linkToUser)

        return ResponseEntity(login, OK)
    }

    @RequestMapping(method = [GET], produces = [HAL_JSON_VALUE])
    @ResponseBody
    fun savePassword(): ResponseEntity<Resources<*>> {
        val producers = ArrayList<String>()
        val resources = Resources(producers)
        resources.add(linkTo(methodOn(UserPasswordController::class.java).createPassword(null)).withSelfRel())
        return ResponseEntity.ok(resources)
    }
}
