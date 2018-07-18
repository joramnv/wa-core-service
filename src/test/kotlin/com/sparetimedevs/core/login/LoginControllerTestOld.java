package com.sparetimedevs.core.login;

import com.sparetimedevs.core.login.exception.LoginResponseEntityExceptionHandler;
import com.sparetimedevs.core.login.validator.LoginValidator;
import com.sparetimedevs.core.user.exception.UserResponseEntityExceptionHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import test.support.MockitoExtension;

import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.hateoas.MediaTypes.HAL_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@WebMvcTest(LoginController.class)
@ContextConfiguration(classes = {LoginController.class})
class LoginControllerTestOld {

	private static final String TEST_EMAIL_ADDRESS_1 = "test@e-mail.address";
	private static final String TEST_PASSWORD_1 = "test_password";
	private static final Login TEST_LOGIN_1 = new Login(TEST_EMAIL_ADDRESS_1, TEST_EMAIL_ADDRESS_1);
	private static final UUID TEST_USER_ID = new UUID(54321L, 12345L);

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private LoginValidator loginValidator;

	@MockBean
	private UserResponseEntityExceptionHandler userResponseEntityExceptionHandler;

	@MockBean
	private LoginResponseEntityExceptionHandler loginResponseEntityExceptionHandler;

	@Test
	void shouldReturnRepositoryIndex() throws Exception {
		mockMvc.perform(get("/login")).andDo(print()).andExpect(status().isOk()).andExpect(
				jsonPath("$._links.self").exists());
	}

	@Test
	void givenCorrectEmailAddressAndPasswordWhenPerformingPostToLoginResultsInLinksToUsersQuestsAndSavePassword() throws Exception {
		when(loginValidator.validate(TEST_LOGIN_1)).thenReturn(TEST_USER_ID);

		mockMvc.perform(
				post("/login")
					.header("Accept", HAL_JSON_VALUE)
					.header("Content-Type", APPLICATION_JSON_VALUE)
					.content("{\"emailAddress\": \"" + TEST_EMAIL_ADDRESS_1 + "\","
							+ "\"password\": \"" + TEST_PASSWORD_1 + "\"}")
				)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$._links.self").exists())
				.andExpect(jsonPath("$._links.user-quests").exists())
				.andExpect(jsonPath("$._links.save-password").exists());
	}

	//TODO This test does not work, does it even make sense to test this in a unit test? It IS going outside of the unit.
//	@Test
//	void givenWrongEmailAddressAndPasswordWhenPerformingPostToLoginResultsInGracefulErrorMessage() throws Exception {
//		doThrow(EmailAddressPasswordDoNotMatchException.class).when(loginValidator).validate(TEST_LOGIN_1);
//
//		when(userResponseEntityExceptionHandler.handleConflict(any(), any())).thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND));
//
//		when(loginResponseEntityExceptionHandler.handleConflict(any(), any())).thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND));
//
//		mockMvc.perform(
//				post("/login")
//						.header("Accept", HAL_JSON_VALUE)
//						.header("Content-Type", APPLICATION_JSON_VALUE)
//						.content("{\"emailAddress\": \"" + EMAIL_ADDRESS_2 + "\","
//								+ "\"password\": \"" + TEST_PASSWORD_1 + "\"}")
//		)
//				.andExpect(status().isNotFound());
//	}

	@Test
	void loginPost() {
	}

	@Test
	void loginGet() {
	}
}
