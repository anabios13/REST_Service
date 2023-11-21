package by.anabios13.servletTest;

import by.anabios13.dto.TaskDTO;
import by.anabios13.services.ITaskService;
import by.anabios13.servlets.TaskServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;

import static org.mockito.Mockito.*;

class TaskServletTest {

    @Mock
    private ITaskService taskService;

    @InjectMocks
    private TaskServlet taskServlet;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

//    @Test
//    void testDoGetWithTaskIdParameter() throws ServletException, IOException {
//        // Arrange
//        HttpServletRequest request = mock(HttpServletRequest.class);
//        HttpServletResponse response = mock(HttpServletResponse.class);
//        when(request.getParameter("taskId")).thenReturn("1");
//
//        TaskDTO taskDTO = new TaskDTO();
//        taskDTO.setTaskId(1);
//        taskDTO.setTaskName("Test Task");
//
//        when(taskService.getTaskById(1)).thenReturn(taskDTO);
//
//        // Act
//        taskServlet.doGet(request, response);
//
//        // Assert
//        verify(response).setContentType("application/json");
//        verify(response.getWriter()).write(anyString());
//    }
//
//    @Test
//    void testDoGetWithoutTaskIdParameter() throws ServletException, IOException {
//        // Arrange
//        HttpServletRequest request = mock(HttpServletRequest.class);
//        HttpServletResponse response = mock(HttpServletResponse.class);
//        when(request.getParameter("taskId")).thenReturn(null);
//
//        TaskDTO task1 = new TaskDTO();
//        task1.setTaskId(1);
//        task1.setTaskName("Task 1");
//
//        TaskDTO task2 = new TaskDTO();
//        task2.setTaskId(2);
//        task2.setTaskName("Task 2");
//
//        when(taskService.getAllTasks()).thenReturn(Arrays.asList(task1, task2));
//
//        // Act
//        taskServlet.doGet(request, response);
//
//        // Assert
//        verify(response).setContentType("application/json");
//        verify(response.getWriter()).write(anyString());
//    }
//
//    @Test
//    void testDoPost() throws ServletException, IOException {
//        // Arrange
//        HttpServletRequest request = mock(HttpServletRequest.class);
//        HttpServletResponse response = mock(HttpServletResponse.class);
//        when(request.getReader()).thenReturn(new BufferedReader(new StringReader("{\"taskName\":\"New Task\"}")));
//
//        TaskDTO createdTask = new TaskDTO();
//        createdTask.setTaskId(1);
//        createdTask.setTaskName("New Task");
//
//        when(taskService.saveTask(any(TaskDTO.class))).thenReturn(createdTask);
//
//        // Act
//        taskServlet.doPost(request, response);
//
//        // Assert
//        verify(response).setContentType("application/json");
//        verify(response.getWriter()).write(anyString());
//    }
//
//    @Test
//    void testDoPut() throws ServletException, IOException {
//        // Arrange
//        HttpServletRequest request = mock(HttpServletRequest.class);
//        HttpServletResponse response = mock(HttpServletResponse.class);
//        when(request.getReader()).thenReturn(new BufferedReader(new StringReader("{\"taskId\":1, \"taskName\":\"Updated Task\"}")));
//
//        // Act
//        taskServlet.doPut(request, response);
//
//        // Assert
//        verify(response).setStatus(HttpServletResponse.SC_OK);
//    }
//
//    @Test
//    void testDoDelete() throws ServletException, IOException {
//        // Arrange
//        HttpServletRequest request = mock(HttpServletRequest.class);
//        HttpServletResponse response = mock(HttpServletResponse.class);
//        when(request.getParameter("taskId")).thenReturn("1");
//
//        // Act
//        taskServlet.doDelete(request, response);
//
//        // Assert
//        verify(response).setStatus(HttpServletResponse.SC_OK);
//    }
}

