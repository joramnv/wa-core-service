package com.sparetimedevs.core.quest

import com.sparetimedevs.core.user.User
import io.kotlintest.specs.StringSpec
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks
import org.springframework.hateoas.Link
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import test.data.testQuest1
import test.data.testQuest2
import test.support.MockitoExtension
import test.userId1
import java.util.ArrayList

@ExtendWith(SpringExtension::class, MockitoExtension::class)
@WebMvcTest(QuestController::class)
@ContextConfiguration(classes = [QuestController::class])
internal class QuestControllerTest(
		private val mockMvc: MockMvc,
		private val repositoryEntityLinks: RepositoryEntityLinks,
		private val questRepository: QuestRepository
) : StringSpec() {

	@TestConfiguration
	internal class EmployeeServiceImplTestContextConfiguration {

		@Bean
		fun repositoryEntityLinks(): RepositoryEntityLinks {
			return Mockito.mock(RepositoryEntityLinks::class.java)
		}

		@Bean
		fun questRepository(): QuestRepository {
			return Mockito.mock(QuestRepository::class.java)
		}
	}

	init {

		"given valid user id when find user quests then response containing user quests is returned" {
			val quests = ArrayList<Quest>()
			quests.add(testQuest1)
			quests.add(testQuest2)
			`when`(questRepository.findByUserId(userId1)).thenReturn(quests)

			val link = Link("test_string")
			`when`(repositoryEntityLinks.linkToSingleResource(User::class.java, userId1)).thenReturn(link)

			mockMvc.perform(get("/quests/user/" + userId1.toString()))
					.andExpect(status().isOk)
					.andExpect(content().json(
							"""
							|{
							|	"_embedded" : {
							|		"quests" : [
							|			{
							|				"id" : "${testQuest1.id}",
							|				"userId" : "${testQuest1.userId}",
							|				"name" : "${testQuest1.name}",
							|				"description" : "${testQuest1.description}",
							|				"achievementPoint" : ${testQuest1.achievementPoint}
							|			},
							|			{
							|				"id" : "${testQuest2.id}",
							|				"userId" : "${testQuest2.userId}",
							|				"name" : "${testQuest2.name}",
							|				"description" : "${testQuest2.description}",
							|				"achievementPoint" : ${testQuest2.achievementPoint}
							|			}
							|		]
							|	},
							|	"_links" : {
							|		"self" : {
							|			"href" : "${link.href}"
							|		}
							|	}
							|}

							""".trimMargin()
					))
		}

		"given I write more tests when I have time then I can determine if I like this" {

			//TODO write more tests.

		}
	}
}
