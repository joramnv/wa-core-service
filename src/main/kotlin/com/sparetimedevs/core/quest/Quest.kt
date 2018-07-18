package com.sparetimedevs.core.quest

import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class Quest(
		@Id
		@Column(name = "ID", nullable = false,  updatable = false, unique = true)
		val id: UUID = UUID.randomUUID(),

		val userId: UUID,

        val name: String,

        val description: String,

        val achievementPoint: Long
)
