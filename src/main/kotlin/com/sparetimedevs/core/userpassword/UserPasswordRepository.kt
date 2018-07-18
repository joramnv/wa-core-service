package com.sparetimedevs.core.userpassword

import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import java.util.Optional
import java.util.UUID

@RepositoryRestResource(exported = false)
interface UserPasswordRepository : CrudRepository<UserPassword, UUID> {

	fun findByUserId(@Param("USER_ID") userId: UUID): Optional<UserPassword>
}
