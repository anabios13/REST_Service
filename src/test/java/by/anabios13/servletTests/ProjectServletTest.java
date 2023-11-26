package by.anabios13.servletTests;

import by.anabios13.dto.ProjectDTO;
import by.anabios13.services.impl.ProjectService;
import by.anabios13.servlets.ProjectServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

class ProjectServletTest {

    @Mock
    private ProjectService projectService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @InjectMocks
    private ProjectServlet projectServlet;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        projectServlet = new ProjectServlet();
        projectServlet.init();
        projectServlet.setProjectService(projectService);
    }

    @Test
    void testDoGetWithProjectId() throws Exception {
        // Arrange
        int projectId = 1;
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setProjectId(projectId);
        projectDTO.setProjectName("Test Project");

        when(request.getParameter("projectId")).thenReturn(String.valueOf(projectId));
        when(projectService.getProjectById(projectId)).thenReturn(projectDTO);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        // Act
        projectServlet.doGet(request, response);

        // Assert
        Assertions.assertTrue(stringWriter.toString().contains("Test Project"));
        verify(response).setContentType("application/json");

    }

    @Test
    void testDoGetWithoutProjectId() throws Exception {
        // Arrange
        ProjectDTO project1 = new ProjectDTO();
        project1.setProjectId(1);
        project1.setProjectName("Project 1");

        ProjectDTO project2 = new ProjectDTO();
        project2.setProjectId(2);
        project2.setProjectName("Project 2");

        List<ProjectDTO> projects = Arrays.asList(project1, project2);

        when(projectService.getAllProjects()).thenReturn(projects);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        // Act
        projectServlet.doGet(request, response);

        // Assert
        Assertions.assertTrue(stringWriter.toString().contains("Project 1"));
        Assertions.assertTrue(stringWriter.toString().contains("Project 2"));
        verify(response).setContentType("application/json");
    }

    @Test
    void testDoPost() throws Exception {
        // Arrange
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setProjectName("Test Project");

        when(request.getParameter("projectName")).thenReturn("Test Project");
        when(projectService.saveProject(any(ProjectDTO.class))).thenReturn(projectDTO);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        // Act
        projectServlet.doPost(request, response);

        // Assert
        Assertions.assertTrue(stringWriter.toString().contains("Test Project"));
        verify(response).setContentType("application/json");
    }

    @Test
    void testDoPut() throws Exception {
        // Arrange
        int projectId = 1;
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setProjectId(projectId);
        projectDTO.setProjectName("Updated Project");

        when(request.getParameter("projectId")).thenReturn(String.valueOf(projectId));
        when(request.getParameter("projectName")).thenReturn("Updated Project");
        when(projectService.getProjectById(projectId)).thenReturn(projectDTO);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        // Act
        projectServlet.doPut(request, response);

        // Assert
        Assertions.assertTrue(stringWriter.toString().contains("Updated Project"));
        verify(response).setContentType("application/json");
    }

    @Test
    void testDoDelete() throws Exception {
        // Arrange
        int projectId = 1;

        when(request.getParameter("projectId")).thenReturn(String.valueOf(projectId));

        // Act
        projectServlet.doDelete(request, response);

        // Assert
        verify(response).setStatus(HttpServletResponse.SC_OK);
        verify(projectService).deleteProject(projectId);
    }
}

