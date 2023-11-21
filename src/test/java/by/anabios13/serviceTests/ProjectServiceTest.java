package by.anabios13.serviceTests;

import by.anabios13.dto.ProjectDTO;
import by.anabios13.exceptions.DeleteException;
import by.anabios13.exceptions.UpdateException;
import by.anabios13.models.Project;
import by.anabios13.repositories.impl.ProjectRepository;
import by.anabios13.services.impl.ProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private ProjectService projectService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

//    @Test
//    void saveProject() {
//        // Arrange
//        ProjectDTO projectDTO = new ProjectDTO();
//        projectDTO.setProjectName("Test Project");
//
//        Project savedProject = new Project();
//        savedProject.setProjectId(1);
//        savedProject.setProjectName("Test Project");
//
//        when(projectRepository.save(any(Project.class))).thenReturn(savedProject);
//
//        // Act
//        ProjectDTO result = projectService.saveProject(projectDTO);
//
//        // Assert
//        assertNotNull(result);
//        assertEquals(1, result.getProjectId());
//        assertEquals("Test Project", result.getProjectName());
//
//        verify(projectRepository, times(1)).save(any(Project.class));
//    }
//
//    @Test
//    void getProjectById() {
//        // Arrange
//        Project project = new Project();
//        project.setProjectId(1);
//        project.setProjectName("Test Project");
//
//        when(projectRepository.getProjectById(1)).thenReturn(project);
//
//        // Act
//        ProjectDTO result = projectService.getProjectById(1);
//
//        // Assert
//        assertNotNull(result);
//        assertEquals(1, result.getProjectId());
//        assertEquals("Test Project", result.getProjectName());
//
//        verify(projectRepository, times(1)).getProjectById(1);
//    }
//
//    @Test
//    void getAllProjects() {
//        // Arrange
//        Project project1 = new Project();
//        project1.setProjectId(1);
//        project1.setProjectName("Project 1");
//
//        Project project2 = new Project();
//        project2.setProjectId(2);
//        project2.setProjectName("Project 2");
//
//        List<Project> projects = Arrays.asList(project1, project2);
//
//        when(projectRepository.getAllProjects()).thenReturn(projects);
//
//        // Act
//        List<ProjectDTO> result = projectService.getAllProjects();
//
//        // Assert
//        assertNotNull(result);
//        assertEquals(2, result.size());
//        assertEquals("Project 1", result.get(0).getProjectName());
//        assertEquals("Project 2", result.get(1).getProjectName());
//
//        verify(projectRepository, times(1)).getAllProjects();
//    }
//
//    @Test
//    void updateProject() {
//        // Arrange
//        ProjectDTO projectDTO = new ProjectDTO();
//        projectDTO.setProjectId(1);
//        projectDTO.setProjectName("Updated Project");
//
//        Project existingProject = new Project();
//        existingProject.setProjectId(1);
//        existingProject.setProjectName("Old Project");
//
//        when(projectRepository.getProjectById(1)).thenReturn(existingProject);
//
//        // Act
//        projectService.updateProject(projectDTO);
//
//        // Assert
//        verify(projectRepository, times(1)).getProjectById(1);
//        verify(projectRepository, times(1)).updateProject(existingProject);
//
//        assertEquals("Updated Project", existingProject.getProjectName());
//    }
//
//    @Test
//    void updateProject_NotFound() {
//        // Arrange
//        ProjectDTO projectDTO = new ProjectDTO();
//        projectDTO.setProjectId(1);
//        projectDTO.setProjectName("Updated Project");
//
//        when(projectRepository.getProjectById(1)).thenReturn(null);
//
//        // Act and Assert
//        assertThrows(UpdateException.class, () -> projectService.updateProject(projectDTO));
//
//        verify(projectRepository, times(1)).getProjectById(1);
//        verify(projectRepository, never()).updateProject(any(Project.class));
//    }
//
//    @Test
//    void deleteProject() {
//        // Arrange
//        Project existingProject = new Project();
//        existingProject.setProjectId(1);
//        existingProject.setProjectName("Test Project");
//
//        when(projectRepository.getProjectById(1)).thenReturn(existingProject);
//
//        // Act
//        projectService.deleteProject(1);
//
//        // Assert
//        verify(projectRepository, times(1)).getProjectById(1);
//        verify(projectRepository, times(1)).deleteProject(1);
//    }
}

