package integrationtest.com.sparetimedevs.core.functional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class WeeklyAchievementsApplicationIT extends AbstractWeeklyAchievementsApplicationIT {

	private MockMvc mockMvc;

	@BeforeEach
	void setUp() throws Exception {
		mockMvc = getMockMvc();
	}

	@Test
	void checkIfApplicationCanRun() throws Exception {
		mockMvc.perform(get("/"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$._links.profile").exists());
	}
}
