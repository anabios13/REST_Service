package by.anabios13.repositoryTests;

import by.anabios13.db.DataSource;
import by.anabios13.models.Project;
import by.anabios13.repositories.impl.ProjectRepository;
import by.anabios13.util.PostgreSQLContainer;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.testcontainers.ext.ScriptUtils;
import org.testcontainers.jdbc.JdbcDatabaseDelegate;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

@Testcontainers
@Tag("DockerRequired")
class ProjectRepositoryTest {

    @Mock
    private DataSource dataSource;
    private ProjectRepository projectRepository;

    private static JdbcDatabaseDelegate jdbcDatabaseDelegate;

    ProjectRepositoryTest() {
    }

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
       dataSource = new DataSource();
        projectRepository = new ProjectRepository(dataSource);
        ScriptUtils.runInitScript(jdbcDatabaseDelegate, PostgreSQLContainer.INIT_SQL);
        try (Connection connection = dataSource.getConnection();
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

       Project savedProject = projectRepository.getProjectById(project.getProjectId());

       Assertions.assertNotEquals(0,project.getProjectId());
        Assertions.assertEquals(project.getProjectId(),savedProject.getProjectId());
        Assertions.assertEquals("test",savedProject.getProjectName());
    }

    @Test
    void testGetProjectById() {
        Project project = new Project("Test Project");
        project = projectRepository.save(project);

        Project foundProject = projectRepository.getProjectById(project.getProjectId());

        Assertions.assertNotNull(foundProject);
        Assertions.assertEquals("Test Project", foundProject.getProjectName());
    }

    @Test
    void testGetAllProjects() {
        Project project1 = new Project( "Project 1");
        Project project2 = new Project( "Project 2");
        project1 = projectRepository.save(project1);
        project2 = projectRepository.save(project2);

        List<Project> projects = projectRepository.getAllProjects();

        Assertions.assertEquals(2, projects.size());
    }

    @Test
    void testUpdateProject() {
        Project project = new Project("Test Project");
        project = projectRepository.save(project);

        project.setProjectName("Updated Project");
        projectRepository.updateProject(project);

        Project updatedProject = projectRepository.getProjectById(project.getProjectId());

        Assertions.assertEquals("Updated Project", updatedProject.getProjectName());
    }

    @Test
    void testDeleteProject() {
        Project project = new Project("Test Project");
        project = projectRepository.save(project);

        projectRepository.deleteProject(project.getProjectId());

        Project deletedProject = projectRepository.getProjectById(project.getProjectId());

        Assertions.assertNull(deletedProject);
    }

}
