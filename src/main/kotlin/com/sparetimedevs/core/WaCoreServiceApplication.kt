package com.sparetimedevs.core

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient

@SpringBootApplication(scanBasePackages = ["com.sparetimedevs.core"])
@EnableEurekaClient
class WaCoreServiceApplication

fun main(args: Array<String>) {
	runApplication<WaCoreServiceApplication>(*args)
}
