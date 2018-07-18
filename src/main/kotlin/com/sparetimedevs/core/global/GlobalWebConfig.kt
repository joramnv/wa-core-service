package com.sparetimedevs.core.global

import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import org.springframework.web.filter.ShallowEtagHeaderFilter
import javax.servlet.Filter

@Configuration
class GlobalWebConfig {

	@Bean
	fun objectMapperBuilder(): Jackson2ObjectMapperBuilder = Jackson2ObjectMapperBuilder().modulesToInstall(KotlinModule())

	@Bean
	fun shallowEtagHeaderFilter(): Filter = ShallowEtagHeaderFilter()
}
