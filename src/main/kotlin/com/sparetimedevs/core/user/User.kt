package com.sparetimedevs.core.user

import java.io.Serializable
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class User(
		@Id
		@Column(name = "ID", nullable = false, updatable = false)
		val id: UUID = UUID.randomUUID(),

		@Column(name = "EMAIL_ADDRESS", unique = true, nullable = false, updatable = true)
		val emailAddress: String
) : Serializable
