package by.anabios13.dto;

import by.anabios13.models.Task;

import java.util.List;

public class ProjectDTO {

    private int projectId;
    private String projectName;

    private List<Task> tasks;

    public ProjectDTO(){}

    public ProjectDTO(String projectName) {
        this.projectName = projectName;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}