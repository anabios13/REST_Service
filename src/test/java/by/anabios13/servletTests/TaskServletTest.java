package by.anabios13.servletTests;

import by.anabios13.dto.TaskDTO;
import by.anabios13.services.ITaskService;
import by.anabios13.servlets.TaskServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

class TaskServletTest {



    @Mock
    private ITaskService taskService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;


    private TaskServlet taskServlet;

    @BeforeEach
    void setUp() throws ServletException {
        MockitoAnnotations.openMocks(this);
        taskServlet = new TaskServlet();
        taskServlet.init();
        taskServlet.setTaskService(taskService);

    }

    @Test
    void testDoGetWithTaskId() throws Exception {
        // Arrange
        int taskId = 1;
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setTaskId(taskId);
        taskDTO.setTaskName("Test Task");

        when(request.getParameter("taskId")).thenReturn(String.valueOf(taskId));
        when(taskService.getTaskById(taskId)).thenReturn(taskDTO);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        // Act
        taskServlet.doGet(request, response);

        // Assert
        Assertions.assertTrue(stringWriter.toString().contains("Test Task"));
        verify(response).setContentType("application/json");
    }

    @Test
    void testDoGetWithoutTaskId() throws Exception {
        // Arrange
        TaskDTO task1 = new TaskDTO();
        task1.setTaskId(1);
        task1.setTaskName("Task 1");

        TaskDTO task2 = new TaskDTO();
        task2.setTaskId(2);
        task2.setTaskName("Task 2");

        List<TaskDTO> tasks = Arrays.asList(task1, task2);

        when(taskService.getAllTasks()).thenReturn(tasks);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        // Act
        taskServlet.doGet(request, response);

        // Assert
        Assertions.assertTrue(stringWriter.toString().contains("Task 1"));
        Assertions.assertTrue(stringWriter.toString().contains("Task 2"));
        verify(response).setContentType("application/json");
    }

    @Test
    void testDoPost() throws Exception {
        // Arrange
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setTaskName("Test Task");

        when(request.getReader()).thenReturn(new BufferedReader(new StringReader("{\"taskName\":\"Test Task\"}")));
        when(taskService.saveTask(any(TaskDTO.class))).thenReturn(taskDTO);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        // Act
        taskServlet.doPost(request, response);

        // Assert
        Assertions.assertTrue(stringWriter.toString().contains("Test Task"));
        verify(response).setContentType("application/json");

    }

    @Test
    void testDoDelete() throws Exception {
        // Arrange
        int taskId = 1;

        when(request.getParameter("taskId")).thenReturn(String.valueOf(taskId));

        // Act
        taskServlet.doDelete(request, response);

        // Assert
        verify(response).setStatus(HttpServletResponse.SC_OK);
        verify(taskService).deleteTask(taskId);
    }
}


