package by.anabios13.serviceTests;

import by.anabios13.db.DataSource;
import by.anabios13.dto.ProjectDTO;
import by.anabios13.exceptions.DeleteException;
import by.anabios13.exceptions.UpdateException;
import by.anabios13.mappers.impl.ProjectMapper;
import by.anabios13.models.Project;
import by.anabios13.repositories.impl.ProjectRepository;
import by.anabios13.services.impl.ProjectService;
import by.anabios13.util.PostgreSQLContainer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
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

class ProjectServiceTest {

    @Mock
    DataSource dataSource;

    @Mock
    private ProjectRepository projectRepository;

    private  ProjectMapper projectMapper = new ProjectMapper();

    private static JdbcDatabaseDelegate jdbcDatabaseDelegate;

    @InjectMocks
    private ProjectService projectService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        projectService = new ProjectService(projectRepository,projectMapper);
        ScriptUtils.runInitScript(jdbcDatabaseDelegate, PostgreSQLContainer.INIT_SQL);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM employee")) {
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @BeforeAll
    static void beforeAll() {
        PostgreSQLContainer.container.start();
        jdbcDatabaseDelegate = new JdbcDatabaseDelegate(PostgreSQLContainer.container, "");
    }

    @Test
    void saveProject() {
        // Arrange
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setProjectName("Test Project");

        Project savedProject = new Project();
        savedProject.setProjectId(1);
        savedProject.setProjectName("Test Project");

        when(projectRepository.save(any(Project.class))).thenReturn(savedProject);

        // Act
        ProjectDTO result = projectService.saveProject(projectDTO);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getProjectId());
        assertEquals("Test Project", result.getProjectName());

        verify(projectRepository, times(1)).save(any(Project.class));
    }

    @Test
    void getProjectById() {
        // Arrange
        Project project = new Project();
        project.setProjectId(1);
        project.setProjectName("Test Project");

        when(projectRepository.getProjectById(1)).thenReturn(project);

        // Act
        ProjectDTO result = projectService.getProjectById(1);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getProjectId());
        assertEquals("Test Project", result.getProjectName());

        verify(projectRepository, times(1)).getProjectById(1);
    }

    @Test
    void getAllProjects() {
        // Arrange
        Project project1 = new Project();
        project1.setProjectId(1);
        project1.setProjectName("Project 1");

        Project project2 = new Project();
        project2.setProjectId(2);
        project2.setProjectName("Project 2");

        List<Project> projects = Arrays.asList(project1, project2);

        when(projectRepository.getAllProjects()).thenReturn(projects);

        // Act
        List<ProjectDTO> result = projectService.getAllProjects();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Project 1", result.get(0).getProjectName());
        assertEquals("Project 2", result.get(1).getProjectName());

        verify(projectRepository, times(1)).getAllProjects();
    }

    @Test
    void deleteProject() {
        // Arrange
        Project existingProject = new Project();
        existingProject.setProjectId(1);
        existingProject.setProjectName("Test Project");

        when(projectRepository.getProjectById(1)).thenReturn(existingProject);

        // Act
        projectService.deleteProject(1);

        // Assert
        verify(projectRepository, times(1)).deleteProject(1);
    }
}

