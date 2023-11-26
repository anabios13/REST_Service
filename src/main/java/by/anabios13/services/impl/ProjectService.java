package by.anabios13.services.impl;


import by.anabios13.dto.ProjectDTO;
import by.anabios13.mappers.IProjectMapper;
import by.anabios13.models.Project;
import by.anabios13.repositories.impl.ProjectRepository;
import by.anabios13.services.IProjectService;

import java.util.List;
import java.util.stream.Collectors;

public class ProjectService implements IProjectService {

    private final ProjectRepository projectRepository;
    // private final ProjectRepository projectRepository = ProjectRepository.getProjectRepository();
    private final IProjectMapper projectMapper;

    public ProjectService(ProjectRepository projectRepository, IProjectMapper projectMapper) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
    }

    public ProjectDTO saveProject(ProjectDTO projectDTO) {
        Project project = projectMapper.projectDTOToProject(projectDTO);
        Project savedProject = projectRepository.save(project);
        return projectMapper.projectToProjectDTO(savedProject);
    }

    public ProjectDTO getProjectById(int projectId) {
        Project project = projectRepository.getProjectById(projectId);
        return projectMapper.projectToProjectDTO(project);
    }

    public List<ProjectDTO> getAllProjects() {
        List<Project> projects = projectRepository.getAllProjects();
        return projects.stream()
                .map(projectMapper::projectToProjectDTO)
                .collect(Collectors.toList());
    }

    public void updateProject(ProjectDTO projectDTO) {
        Project project = projectMapper.projectDTOToProject(projectDTO);
        projectRepository.updateProject(project);
    }

    public void deleteProject(int projectId) {
        projectRepository.deleteProject(projectId);
    }
}

