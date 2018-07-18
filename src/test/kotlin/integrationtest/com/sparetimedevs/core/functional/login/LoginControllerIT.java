package integrationtest.com.sparetimedevs.core.functional.login;

import com.sparetimedevs.core.user.User;
import com.sparetimedevs.core.user.UserRepository;
import com.sparetimedevs.core.userpassword.UserPassword;
import com.sparetimedevs.core.userpassword.UserPasswordRepository;
import integrationtest.com.sparetimedevs.core.functional.AbstractWeeklyAchievementsApplicationIT;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.MediaTypes.HAL_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static test.TestDataKt.EMAIL_ADDRESS_1;
import static test.TestDataKt.EMAIL_ADDRESS_2;
import static test.TestDataKt.PASSWORD_1;
import static test.TestDataKt.PASSWORD_2;
import static test.TestDataKt.getUserId1;
import static test.TestDataKt.getUserPasswordId1;

class LoginControllerIT extends AbstractWeeklyAchievementsApplicationIT {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserPasswordRepository userPasswordRepository;

	private MockMvc mockMvc;

	@BeforeEach
	void setUp() throws Exception {
		mockMvc = getMockMvc();
		User user = setUpUser();
		setUpUserPassword(user.getId());
	}

	private User setUpUser() {
		Optional<User> optionalUser = userRepository.findByEmailAddress(EMAIL_ADDRESS_1);
		if (optionalUser.isPresent()) {
			return optionalUser.get();
		} else {
			User user = new User(getUserId1(), EMAIL_ADDRESS_1);
			user = userRepository.save(user);
			return user;
		}
	}

	private void setUpUserPassword(UUID userId) {
		Optional<UserPassword> optionalUserPassword = userPasswordRepository.findByUserId(userId);
		if (!optionalUserPassword.isPresent()) {
			UserPassword userPassword = new UserPassword(getUserPasswordId1(), userId, PASSWORD_1);
			userPasswordRepository.save(userPassword);
		}
	}

	@Test
	void shouldReturnRepositoryIndex() throws Exception {
		mockMvc.perform(get("/login"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$._links.self").exists());
	}

	@Test
	void givenCorrectEmailAddressAndCorrectPasswordWhenPerformingPostToLoginResultsInLinksToUsersQuestsAndSavePassword() throws Exception {
		mockMvc.perform(
				post("/login")
					.header("Accept", HAL_JSON_VALUE)
					.header("Content-Type", APPLICATION_JSON_VALUE)
					.content("{\"emailAddress\": \"" + EMAIL_ADDRESS_1 + "\","
							+ "\"password\": \"" + PASSWORD_1 + "\"}")
				)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$._links.self").exists())
				.andExpect(jsonPath("$._links.user-quests").exists())
				.andExpect(jsonPath("$._links.save-password").exists());
	}

	@Test
	void givenWrongEmailAddressAndCorrectPasswordWhenPerformingPostToLoginResultsInStatusIsNotFound() throws Exception {
		mockMvc.perform(
				post("/login")
						.header("Accept", HAL_JSON_VALUE)
						.header("Content-Type", APPLICATION_JSON_VALUE)
						.content("{\"emailAddress\": \"" + EMAIL_ADDRESS_2 + "\","
								+ "\"password\": \"" + PASSWORD_1 + "\"}")
		)
				.andExpect(status().isNotFound());
	}

	@Test
	void givenWrongEmailAddressAndWrongPasswordWhenPerformingPostToLoginResultsInStatusIsNotFound() throws Exception {
		mockMvc.perform(
				post("/login")
						.header("Accept", HAL_JSON_VALUE)
						.header("Content-Type", APPLICATION_JSON_VALUE)
						.content("{\"emailAddress\": \"" + EMAIL_ADDRESS_2 + "\","
								+ "\"password\": \"" + PASSWORD_2 + "\"}")
		)
				.andExpect(status().isNotFound());
	}

	@Test
	void givenCorrectEmailAddressAndWrongPasswordWhenPerformingPostToLoginResultsInStatusIsConflict() throws Exception {
		mockMvc.perform(
				post("/login")
						.header("Accept", HAL_JSON_VALUE)
						.header("Content-Type", APPLICATION_JSON_VALUE)
						.content("{\"emailAddress\": \"" + EMAIL_ADDRESS_1 + "\","
								+ "\"password\": \"" + PASSWORD_2 + "\"}")
		)
				.andExpect(status().isUnauthorized());
	}

	@Test
	void loginPost() {
	}

	@Test
	void loginGet() {
	}
}
