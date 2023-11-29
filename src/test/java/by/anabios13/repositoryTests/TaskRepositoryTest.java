package by.anabios13.repositoryTests;

import by.anabios13.db.DataSource;
import by.anabios13.models.Employee;
import by.anabios13.models.Project;
import by.anabios13.models.Task;
import by.anabios13.repositories.impl.ProjectRepository;
import by.anabios13.repositories.impl.TaskRepository;
import by.anabios13.util.PostgreSQLContainer;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.testcontainers.ext.ScriptUtils;
import org.testcontainers.jdbc.JdbcDatabaseDelegate;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

@Testcontainers
@Tag("DockerRequired")
class TaskRepositoryTest {

    @Mock
    private DataSource dataSource;

    private TaskRepository taskRepository;
    private ProjectRepository projectRepository;

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
        dataSource = new DataSource();
        projectRepository = new ProjectRepository(dataSource);
        taskRepository = new TaskRepository(dataSource);
        ScriptUtils.runInitScript(jdbcDatabaseDelegate, PostgreSQLContainer.INIT_SQL);
            // Очистка таблицы Task перед каждым тестом
            try (Connection connection = dataSource.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM task")) {
                preparedStatement.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
        }
    }

    @Test
    void testFindAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        Assertions.assertTrue(tasks.isEmpty());
    }

    @Test
    void testSave() {
        Project project = injectProject();
        Task task = new Task(1, "Test Task");

        Task savedTask = taskRepository.save(task,project.getProjectId());

        Assertions.assertNotEquals(0,task.getTaskId());
        Assertions.assertEquals("Test Task", savedTask.getTaskName());
    }

    @Test
    void testFindById() {
        Project project = injectProject();
        Task task = new Task(0, "Test Task");
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee("Test Employee"));
        task.setTaskName("Test Task");
        task.setPerformers(employees);
        task = taskRepository.save(task,project.getProjectId());

        Task foundTask = taskRepository.findById(task.getTaskId());

        Assertions.assertNotNull(foundTask);
        Assertions.assertEquals("Test Task", foundTask.getTaskName());
    }

    @Test
    void testFindAll() {
        Project project = injectProject();
        Task task1 = new Task(1,"");

        Task task2 = new Task(2,"");
        task1 = taskRepository.save(task1,project.getProjectId());
        task2 = taskRepository.save(task2,project.getProjectId());
        List<Task> tasks = taskRepository.findAll();

        Assertions.assertEquals(2, tasks.size());
    }

    @Test
    void testUpdate() {
        Project project = injectProject();
        Task task = new Task(0, "Test Task");
        task = taskRepository.save(task,project.getProjectId());

        task.setTaskName("Updated Task");
        taskRepository.update(task,project.getProjectId());

        Task updatedTask = taskRepository.findById(task.getTaskId());

        Assertions.assertEquals("Updated Task", updatedTask.getTaskName());
    }

    @Test
    void testDeleteById() {
        Project project = injectProject();
        Task task = new Task(1, "Test Task");
        task = taskRepository.save(task,project.getProjectId());

        taskRepository.deleteById(task.getTaskId());

        Task deletedTask = taskRepository.findById(task.getTaskId());

        Assertions.assertNull(deletedTask);
    }

    public Project injectProject(){
        Project project = new Project("test");
        project=projectRepository.save(project);
        return project;
    }

}