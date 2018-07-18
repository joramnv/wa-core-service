package integrationtest.com.sparetimedevs.core.functional.quest

import com.sparetimedevs.core.WaCoreServiceApplication
import com.sparetimedevs.core.quest.Quest
import com.sparetimedevs.core.quest.QuestRepository
import com.sparetimedevs.core.user.User
import com.sparetimedevs.core.user.UserRepository
import io.kotlintest.specs.StringSpec
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import test.EMAIL_ADDRESS_1
import test.data.testQuest1
import test.data.testQuest2
import test.userId1

@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = [WaCoreServiceApplication::class])
@AutoConfigureMockMvc
class QuestRepositoryIT(
		private val mockMvc: MockMvc,
		private val questRepository: QuestRepository,
		private val userRepository: UserRepository
) : StringSpec() {

	@BeforeEach
	@Throws(Exception::class)
	fun setUp() {
		val user1 = setUpUser()
		val quest = setUpQuestsForUser()
	}

	private fun setUpUser(): User {
		val user = User(userId1, EMAIL_ADDRESS_1)
		return userRepository.save(user)
	}

	private fun setUpQuestsForUser(): List<Quest> {
		questRepository.save(testQuest1)
		questRepository.save(testQuest2)
		val quests = ArrayList<Quest>()
		quests.add(testQuest1)
		quests.add(testQuest2)
		return quests
	}

	@AfterEach
	@Throws(Exception::class)
	fun tearDown() {
		//TODO should clean up db
	}

	init {
		"should return repository index" {
			val mvcResult = mockMvc.perform(get("/quests"))
					.andDo(print())
					.andExpect(status().isOk)
					.andExpect(jsonPath("$._links.self").exists())
					.andReturn()
		}

		"given I write more tests when I have time then I can determine if I like this" {

			//TODO write more tests.

		}
	}
}
