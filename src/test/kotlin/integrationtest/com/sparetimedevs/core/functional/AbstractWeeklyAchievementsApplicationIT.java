package integrationtest.com.sparetimedevs.core.functional;

import com.sparetimedevs.core.WaCoreServiceApplication;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = WaCoreServiceApplication.class)
@AutoConfigureMockMvc
public abstract class AbstractWeeklyAchievementsApplicationIT {

	public static final String TEST_BASE_URL = "http://localhost";

	@Autowired
	private MockMvc mockMvc;

	protected MockMvc getMockMvc() {
		return mockMvc;
	}
}
