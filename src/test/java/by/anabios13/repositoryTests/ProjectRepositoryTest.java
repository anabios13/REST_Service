package by.anabios13.repositoryTests;

import by.anabios13.db.DataSource;
import by.anabios13.models.Project;
import by.anabios13.models.Task;
import by.anabios13.repositories.impl.ProjectRepository;
import by.anabios13.repositories.impl.TaskRepository;
import by.anabios13.util.PostgreSQLContainer;
import by.anabios13.util.PropertiesUtil;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import org.junit.jupiter.api.*;
import org.testcontainers.ext.ScriptUtils;
import org.testcontainers.jdbc.JdbcDatabaseDelegate;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.Connection;
import java.sql.PreparedStatement;

@Testcontainers
@Tag("DockerRequired")
public class ProjectRepositoryTest {


    private TaskRepository taskRepository = TaskRepository.gatTaskRepository();
    private ProjectRepository projectRepository = ProjectRepository.getProjectRepository();

    private static JdbcDatabaseDelegate jdbcDatabaseDelegate;

    @BeforeAll
    static void beforeAll() {

        PostgreSQLContainer.container.start();
        jdbcDatabaseDelegate = new JdbcDatabaseDelegate(PostgreSQLContainer.container, "");
    }

    @AfterAll
    static void afterAll() {
        PostgreSQLContainer.container.stop();
    }

    @BeforeEach
    void setUp() {
        ScriptUtils.runInitScript(jdbcDatabaseDelegate, PostgreSQLContainer.INIT_SQL);
        // Очистка таблицы Task перед каждым тестом
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM project")) {
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    void testSave() {

        Project project = new Project("test");
       project= projectRepository.save(project);

        //  Task savedTask = taskRepository.save(task,);

        Assertions.assertNotNull(project.getProjectId());
        Assertions.assertEquals("test", project.getProjectName());
    }

}
