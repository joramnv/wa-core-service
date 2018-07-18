package io.kotlintest.provided

import io.kotlintest.AbstractProjectConfig
import io.kotlintest.extensions.ProjectLevelExtension
import io.kotlintest.extensions.TestListener
import io.kotlintest.spring.SpringAutowireConstructorExtension
import io.kotlintest.spring.SpringListener
import java.lang.System.currentTimeMillis
import java.lang.System.lineSeparator

/**
 * Project-wide configuration. Extensions returned by an
 * instance of this class will be applied to all io.kotlintest.Spec and io.kotlintest.TestCase.
 *
 * KotlinTest will detect its presence and use it when executing tests.
 */
object ProjectConfig : AbstractProjectConfig() {

	/**
	 * List of project wide extensions, ie instances of [ProjectLevelExtension]
	 * TODO make this comment more clear
	 * SpringAutowireConstructorExtension makes it possible to use constructor autowiring in test classes
	 */
	override fun extensions() = listOf(SpringAutowireConstructorExtension)

	/**
	 * List of project wide [TestListener]s.
	 * TODO make this comment more clear
	 * Also not sure if this is still needed if I only would use constructor autowiring in test classes.
	 */
	override fun listeners() = listOf(SpringListener)

	private var started: Long = 0

	override fun beforeAll() {
		started = currentTimeMillis()
	}

	override fun afterAll() {
		val timeInMilliseconds = currentTimeMillis() - started
		val timeInSeconds = (timeInMilliseconds / 1000) % 60
		println("overall time in seconds: " + timeInSeconds + lineSeparator())
	}
}
