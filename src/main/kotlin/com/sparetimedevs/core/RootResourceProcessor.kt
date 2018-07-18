package com.sparetimedevs.core

import com.sparetimedevs.core.login.LoginController
import org.springframework.data.rest.webmvc.RepositoryLinksResource
import org.springframework.hateoas.ResourceProcessor
import org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo
import org.springframework.stereotype.Component

@Component
class RootResourceProcessor : ResourceProcessor<RepositoryLinksResource> {

	override fun process(resource: RepositoryLinksResource): RepositoryLinksResource {
		resource.add(linkTo(LoginController::class.java).withRel("login"))
		return resource
	}
}
