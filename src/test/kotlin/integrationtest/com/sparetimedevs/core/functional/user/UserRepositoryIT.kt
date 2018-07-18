package integrationtest.com.sparetimedevs.core.functional.user

import com.sparetimedevs.core.WaCoreServiceApplication
import com.sparetimedevs.core.user.UserRepository
import integrationtest.com.sparetimedevs.core.functional.AbstractWeeklyAchievementsApplicationIT.TEST_BASE_URL
import io.kotlintest.matchers.string.contain
import io.kotlintest.should
import io.kotlintest.shouldNot
import io.kotlintest.shouldNotBe
import io.kotlintest.specs.StringSpec
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.header
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import test.EMAIL_ADDRESS_1
import test.EMAIL_ADDRESS_2
import test.userId1

@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = [WaCoreServiceApplication::class])
@AutoConfigureMockMvc
class UserRepositoryIT(
		private val mockMvc: MockMvc,
		private val userRepository: UserRepository
) : StringSpec() {

	private fun tearDown(emailAddress: String) {
		val optionalUser = userRepository.findByEmailAddress(emailAddress)
		optionalUser.ifPresent { user -> userRepository.delete(user) }
	}

	private fun getUserIdFromLocation(location: String): String {
		return location.split("/".toRegex()).last()
	}

	init {
		//TODO change names of tests to given when then.

		"should return repository index" {
			mockMvc.perform(get("/users"))
					.andDo(print())
					.andExpect(status().isOk)
					.andExpect(jsonPath("$._links.self").exists())
		}

		"should create entity" {
			val mvcResult = mockMvc.perform(post("/users").content(
					"{\"emailAddress\": \"$EMAIL_ADDRESS_1\"}"))
					.andExpect(status().isCreated)
					.andReturn()

			val location = mvcResult.response.getHeader("Location")!!
			val userId = getUserIdFromLocation(location)
			userId shouldNotBe null

			tearDown(EMAIL_ADDRESS_1)
		}

		"should retrieve entity" {
			val mvcResult = mockMvc.perform(post("/users").content(
					"{\"emailAddress\": \"$EMAIL_ADDRESS_1\"}")).andExpect(
					status().isCreated).andReturn()

			val location = mvcResult.response.getHeader("Location")!!
			val userId = getUserIdFromLocation(location)
			mockMvc.perform(get(location))
					.andExpect(status().isOk)
					.andExpect(content().json("{\n" +
							"  \"_links\" : {\n" +
							"    \"self\" : {\n" +
							"      \"href\" : \"$TEST_BASE_URL/users/$userId\"\n" +
							"    },\n" +
							"    \"user\" : {\n" +
							"      \"href\" : \"$TEST_BASE_URL/users/$userId\"\n" +
							"    }\n" +
							"  }\n" +
							"}"))

			tearDown(EMAIL_ADDRESS_1)
		}

		"should update email address of the user with put" {
			val mvcResult = mockMvc.perform(post("/users").content(
					"{\"emailAddress\": \"$EMAIL_ADDRESS_1\"}"))
					.andExpect(status().isCreated).andReturn()

			val location = mvcResult.response.getHeader("Location")!!

			mockMvc.perform(put(location).content(
					"{\"emailAddress\": \"$EMAIL_ADDRESS_2\"}"))
					.andExpect(status().isNoContent)

			val userId = getUserIdFromLocation(location)

			val mvcResult2 = mockMvc.perform(get(location))
					.andExpect(status().isOk)
					.andExpect(content().json("{\n" +
							"  \"_links\" : {\n" +
							"    \"self\" : {\n" +
							"      \"href\" : \"$TEST_BASE_URL/users/$userId\"\n" +
							"    },\n" +
							"    \"user\" : {\n" +
							"      \"href\" : \"$TEST_BASE_URL/users/$userId\"\n" +
							"    }\n" +
							"  }\n" +
							"}"))
					.andReturn()

			val jsonResponseBody = mvcResult2.response.contentAsString
			jsonResponseBody should contain(EMAIL_ADDRESS_2)
			jsonResponseBody shouldNot contain(EMAIL_ADDRESS_1)

			tearDown(EMAIL_ADDRESS_2)
		}

		"should update email address of the user with patch" {
			val mvcResult = mockMvc.perform(post("/users").content(
					"{\"emailAddress\": \"$EMAIL_ADDRESS_1\"}"))
					.andExpect(status().isCreated)
					.andReturn()

			val location = mvcResult.response.getHeader("Location")!!

			mockMvc.perform(patch(location).content(
					"{\"emailAddress\": \"$EMAIL_ADDRESS_2\"}"))
					.andExpect(status().isNoContent)

			val userId = getUserIdFromLocation(location)

			val mvcResult2 = mockMvc.perform(get(location))
					.andExpect(status().isOk)
					.andExpect(content().json("{\n" +
							"  \"_links\" : {\n" +
							"    \"self\" : {\n" +
							"      \"href\" : \"$TEST_BASE_URL/users/$userId\"\n" +
							"    },\n" +
							"    \"user\" : {\n" +
							"      \"href\" : \"$TEST_BASE_URL/users/$userId\"\n" +
							"    }\n" +
							"  }\n" +
							"}"))
					.andReturn()

			val jsonResponseBody = mvcResult2.response.contentAsString
			jsonResponseBody should contain(EMAIL_ADDRESS_2)
			jsonResponseBody shouldNot contain(EMAIL_ADDRESS_1)

			tearDown(EMAIL_ADDRESS_2)
		}

		"should delete entity" {
			val mvcResult = mockMvc.perform(post("/users").content(
					"{\"emailAddress\": \"$EMAIL_ADDRESS_1\"}"))
					.andExpect(status().isCreated)
					.andReturn()

			val location = mvcResult.response.getHeader("Location")!!
			mockMvc.perform(delete(location))
					.andExpect(status().isNoContent)

			mockMvc.perform(get(location))
					.andExpect(status().isNotFound)
		}

		"should return status is not found when trying to get an entity that is not present in the database" {
			val url = "http://localhost/users/$userId1"

			mockMvc.perform(get(url))
					.andExpect(status().isNotFound)
		}

		"given user resource exists when retrieving user resource then ETag is also returned" {
			val mvcResult = mockMvc.perform(post("/users").content(
					"{\"emailAddress\": \"$EMAIL_ADDRESS_1\"}"))
					.andExpect(status().isCreated)
					.andReturn()

			val location = mvcResult.response.getHeader("Location")!!
			val userId = getUserIdFromLocation(location)
			mockMvc.perform(get(location))
					.andExpect(status().isOk)
					.andExpect(content().json("{\n" +
							"  \"_links\" : {\n" +
							"    \"self\" : {\n" +
							"      \"href\" : \"$TEST_BASE_URL/users/$userId\"\n" +
							"    },\n" +
							"    \"user\" : {\n" +
							"      \"href\" : \"$TEST_BASE_URL/users/$userId\"\n" +
							"    }\n" +
							"  }\n" +
							"}"))
					.andExpect(header().exists("ETag"))

			tearDown(EMAIL_ADDRESS_1)
		}

		"given user resource was retrieved when retrieving again with ETag then not modified is returned" {
			val mvcResult = mockMvc.perform(post("/users").content(
					"{\"emailAddress\": \"$EMAIL_ADDRESS_1\"}")).andExpect(
					status().isCreated).andReturn()

			val location = mvcResult.response.getHeader("Location")!!
			val userId = getUserIdFromLocation(location)
			val mvcResult2 = mockMvc.perform(get(location))
					.andExpect(status().isOk)
					.andExpect(content().json("{\n" +
							"  \"_links\" : {\n" +
							"    \"self\" : {\n" +
							"      \"href\" : \"$TEST_BASE_URL/users/$userId\"\n" +
							"    },\n" +
							"    \"user\" : {\n" +
							"      \"href\" : \"$TEST_BASE_URL/users/$userId\"\n" +
							"    }\n" +
							"  }\n" +
							"}"))
					.andExpect(header().exists("ETag"))
					.andReturn()

			val etagValue = mvcResult2.response.getHeaderValue("ETag")!!

			mockMvc.perform(get(location).header("If-None-Match", etagValue))
					.andExpect(status().isNotModified)
					.andExpect(header().exists("ETag"))
					.andReturn()

			tearDown(EMAIL_ADDRESS_1)
		}

		"given user resource was retrieved then modified when retrieving again with ETag then user resource is returned" { //TODO write this etag tests..., see http://www.baeldung.com/etags-for-rest-with-spring
			val mvcResult = mockMvc.perform(post("/users").content(
					"{\"emailAddress\": \"$EMAIL_ADDRESS_1\"}"))
					.andExpect(status().isCreated)
					.andReturn()

			val location = mvcResult.response.getHeader("Location")!!
			val userId = getUserIdFromLocation(location)
			val mvcResult2 = mockMvc.perform(get(location))
					.andExpect(status().isOk)
					.andExpect(content().json("{\n" +
							"  \"_links\" : {\n" +
							"    \"self\" : {\n" +
							"      \"href\" : \"$TEST_BASE_URL/users/$userId\"\n" +
							"    },\n" +
							"    \"user\" : {\n" +
							"      \"href\" : \"$TEST_BASE_URL/users/$userId\"\n" +
							"    }\n" +
							"  }\n" +
							"}"))
					.andExpect(header().exists("ETag"))
					.andReturn()

			val etagValue = mvcResult2.response.getHeaderValue("ETag")!!

			//TODO change user object.
			mockMvc.perform(put(location).content(
					"{\"emailAddress\": \"$EMAIL_ADDRESS_2\"}"))
					.andExpect(status().isNoContent)

			mockMvc.perform(get(location).header("If-None-Match", etagValue))
					.andExpect(status().isOk)
					.andExpect(content().json("{\n" +
							"  \"_links\" : {\n" +
							"    \"self\" : {\n" +
							"      \"href\" : \"$TEST_BASE_URL/users/$userId\"\n" +
							"    },\n" +
							"    \"user\" : {\n" +
							"      \"href\" : \"$TEST_BASE_URL/users/$userId\"\n" +
							"    }\n" +
							"  }\n" +
							"}"))
					.andExpect(header().exists("ETag"))
					.andReturn()

			tearDown(EMAIL_ADDRESS_1)
		}
	}
}
