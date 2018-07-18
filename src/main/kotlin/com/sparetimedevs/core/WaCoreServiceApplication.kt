package com.sparetimedevs.core

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@EnableEurekaClient
@ComponentScan(basePackages = ["com.sparetimedevs.core"])
class WaCoreServiceApplication

fun main(args: Array<String>) {
	runApplication<WaCoreServiceApplication>(*args)
}
