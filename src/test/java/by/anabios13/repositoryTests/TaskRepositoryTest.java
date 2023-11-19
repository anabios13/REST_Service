package by.anabios13.repositoryTests;

import by.anabios13.models.Task;
import by.anabios13.repositories.impl.EmployeeRepository;
import by.anabios13.repositories.impl.ProjectRepository;
import by.anabios13.repositories.impl.TaskRepository;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.ext.ScriptUtils;
import org.testcontainers.jdbc.JdbcDatabaseDelegate;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.Optional;

@Testcontainers
@Tag("DockerRequired")
class TaskRepositoryTest {
    private static final String INIT_SQL = "sql/schema.sql";
    private static int containerPort = 5432;
    private static int localPort = 5432;
    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private ProjectRepository projectRepository;
    private TaskRepository taskRepository = new TaskRepository(projectRepository, employeeRepository);
    @Container
    public static PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:15-alpine")
            .withDatabaseName("aston_rest_service_db")
            .withUsername("postgres")
            .withPassword("12345")
            .withEnv("POSTGRES_PASSWORD", "12345")
            .withExposedPorts(containerPort)
            .withCreateContainerCmdModifier(cmd -> cmd.withHostConfig(
                    new HostConfig().withPortBindings(new PortBinding(Ports.Binding.bindPort(localPort), new ExposedPort(containerPort)))
            ))
            .withInitScript(INIT_SQL);
    private static JdbcDatabaseDelegate jdbcDatabaseDelegate;

    @BeforeAll
    static void beforeAll() {
        container.start();
        jdbcDatabaseDelegate = new JdbcDatabaseDelegate(container, "");
    }

    @AfterAll
    static void afterAll() {
        container.stop();
    }

    @BeforeEach
    void setUp() {
        ScriptUtils.runInitScript(jdbcDatabaseDelegate, INIT_SQL);
    }

    @Test
    void testFindAllTasks() {
        List<Task> tasks = taskRepository.getAllTasks();
        Assertions.assertNotNull(tasks);
        Assertions.assertTrue(tasks.isEmpty());
    }
}