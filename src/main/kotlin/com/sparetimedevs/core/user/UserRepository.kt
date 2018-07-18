package com.sparetimedevs.core.user

import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.repository.query.Param
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import java.util.Optional
import java.util.UUID

@RepositoryRestResource(collectionResourceRel = "users", path = "/users")
interface UserRepository : PagingAndSortingRepository<User, UUID> {

	fun findByEmailAddress(@Param("EMAIL_ADDRESS") emailAddress: String): Optional<User>
}
