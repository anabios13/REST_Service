package by.anabios13.services;

import by.anabios13.dto.ProjectDTO;

import java.util.List;

public interface IProjectService {
    ProjectDTO saveProject(ProjectDTO projectDTO);

    ProjectDTO getProjectById(int projectId);

    List<ProjectDTO> getAllProjects();

    void updateProject(ProjectDTO projectDTO);

    void deleteProject(int projectId);
}
