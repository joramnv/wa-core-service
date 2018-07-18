package com.sparetimedevs.core.login

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.hateoas.ResourceSupport

data class Login @JsonCreator constructor(
        @param:JsonProperty("emailAddress")
        val emailAddress: String,

        @param:JsonProperty("password")
        val password: String
) : ResourceSupport()
