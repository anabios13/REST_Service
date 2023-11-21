package by.anabios13.mappers.impl;


import by.anabios13.dto.ProjectDTO;
import by.anabios13.mappers.IProjectMapper;
import by.anabios13.models.Project;

public class ProjectMapper implements IProjectMapper {
    @Override
    public ProjectDTO projectToProjectDTO(Project project) {
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setProjectId(project.getProjectId());
        projectDTO.setProjectName(project.getProjectName());
        projectDTO.setTasks(project.getTasks());
        return projectDTO;
    }

    @Override
    public Project projectDTOToProject(ProjectDTO projectDTO) {
        Project project = new Project();
        project.setProjectId(projectDTO.getProjectId());
        project.setProjectName(projectDTO.getProjectName());
        project.setTasks(projectDTO.getTasks());
        return project;
    }
}
