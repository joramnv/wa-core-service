package com.sparetimedevs.core.userpassword

import java.io.Serializable
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "USER_PASSWORD")
data class UserPassword(
		@Id
		@Column(name = "ID", nullable = false, updatable = false)
		val id: UUID = UUID.randomUUID(),

		val userId: UUID,

		val password: String
) : Serializable
