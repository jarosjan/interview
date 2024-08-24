package interview.jj;

import com.fasterxml.jackson.databind.ObjectMapper;
import interview.jj.dao.TbProjectRepository;
import interview.jj.dao.TbUserRepository;
import interview.jj.security.JwtTokenProvider;
import interview.jj.service.ProjectService;
import interview.jj.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestcontainersConfiguration.class)
@AutoConfigureMockMvc
public class BaseWebTest {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected TbUserRepository tbUserRepository;

    @Autowired
    protected TbProjectRepository tbProjectRepository;

    @Autowired
    protected UserService userService;

    @Autowired
    protected ProjectService projectService;

    protected String jwtToken;

    @BeforeEach
    void setUp() {
        tbUserRepository.deleteAll();
        userService.registerUser("testUser", "password", "testUser@example.com");
        jwtToken = jwtTokenProvider.generateToken("testUser@example.com");
    }

    protected String createUser(String name, String password, String email) {
        userService.registerUser(name, password, email);
        return jwtTokenProvider.generateToken(email);
    }

}
