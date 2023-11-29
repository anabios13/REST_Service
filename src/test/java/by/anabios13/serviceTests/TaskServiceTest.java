package by.anabios13.serviceTests;


import by.anabios13.db.DataSource;
import by.anabios13.dto.TaskDTO;
import by.anabios13.exceptions.UpdateException;
import by.anabios13.mappers.impl.TaskMapper;
import by.anabios13.models.Project;
import by.anabios13.models.Task;
import by.anabios13.repositories.impl.TaskRepository;
import by.anabios13.services.impl.TaskService;
import by.anabios13.util.PostgreSQLContainer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testcontainers.ext.ScriptUtils;
import org.testcontainers.jdbc.JdbcDatabaseDelegate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskServiceTest {

    @Mock
    DataSource dataSource;
    @Mock
    private TaskRepository taskRepository;

    private TaskMapper taskMapper=new TaskMapper();
    private TaskService taskService;
    private static JdbcDatabaseDelegate jdbcDatabaseDelegate;

    @BeforeAll
    static void beforeAll() {
        PostgreSQLContainer.container.start();
        jdbcDatabaseDelegate = new JdbcDatabaseDelegate(PostgreSQLContainer.container, "");
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        taskService = new TaskService(taskRepository,taskMapper);
        ScriptUtils.runInitScript(jdbcDatabaseDelegate, PostgreSQLContainer.INIT_SQL);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM employee")) {
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void saveTask() {
        // Arrange
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setTaskName("Test Task");
        Project project = new Project();
        project.setProjectId(1);
        taskDTO.setProject(project);

        Task savedTask = new Task();
        savedTask.setTaskId(1);
        savedTask.setTaskName("Test Task");

        when(taskRepository.save(any(Task.class),eq(taskDTO.getProject().getProjectId()))).thenReturn(savedTask);
        // Act
        TaskDTO result = taskService.saveTask(taskDTO);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getTaskId());
        assertEquals("Test Task", result.getTaskName());

        verify(taskRepository, times(1))
                .save(any(Task.class),eq(taskDTO.getProject().getProjectId()));
    }

    @Test
    void getTaskById() {
        // Arrange
        Task task = new Task();
        task.setTaskId(1);
        task.setTaskName("Test Task");

        when(taskRepository.findById(1)).thenReturn(task);

        // Act
        TaskDTO result = taskService.getTaskById(1);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getTaskId());
        assertEquals("Test Task", result.getTaskName());

        verify(taskRepository, times(1)).findById(1);
    }

    @Test
    void getAllTasks() {
        // Arrange
        Task task1 = new Task();
        task1.setTaskId(1);
        task1.setTaskName("Task 1");

        Task task2 = new Task();
        task2.setTaskId(2);
        task2.setTaskName("Task 2");

        List<Task> tasks = Arrays.asList(task1, task2);

        when(taskRepository.findAll()).thenReturn(tasks);

        // Act
        List<TaskDTO> result = taskService.getAllTasks();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Task 1", result.get(0).getTaskName());
        assertEquals("Task 2", result.get(1).getTaskName());

        verify(taskRepository, times(1)).findAll();
    }

    @Test
    void updateTask() {
        // Arrange
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setTaskId(1);
        taskDTO.setTaskName("Updated Task");
        Project project = new Project();
        project.setProjectId(1);
        taskDTO.setProject(project);

        Task existingTask = new Task();
        existingTask.setTaskId(1);
        existingTask.setTaskName("Old Task");

        when(taskRepository.findById(1)).thenReturn(existingTask);

        // Act
        taskService.updateTask(taskDTO);

        // Assert
        verify(taskRepository, times(1)).findById(1);
        verify(taskRepository, times(1)).update(existingTask,1);

        assertEquals("Updated Task", existingTask.getTaskName());
    }

    @Test
    void updateTask_NotFound() {
        // Arrange
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setTaskId(1);
        taskDTO.setTaskName("Updated Task");

        when(taskRepository.findById(1)).thenThrow(new  UpdateException(""));

        // Act and Assert
        assertThrows(UpdateException.class, () -> taskService.updateTask(taskDTO));

        verify(taskRepository, times(1)).findById(1);
        verify(taskRepository, never()).update(any(Task.class),eq(1));
    }

    @Test
    void deleteTask() {
        // Arrange
        Task existingTask = new Task();
        existingTask.setTaskId(1);
        existingTask.setTaskName("Test Task");

        when(taskRepository.findById(1)).thenReturn(existingTask);

        // Act
        taskService.deleteTask(1);

        // Assert
        verify(taskRepository, times(1)).findById(1);
        verify(taskRepository, times(1)).deleteById(1);
    }
}

