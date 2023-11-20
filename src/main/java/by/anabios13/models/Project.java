package by.anabios13.models;

import by.anabios13.repositories.impl.TaskRepository;

import java.util.List;

public class Project {

    private int projectId;
    private String projectName;

    private List<Task> tasks;

    public Project(){}



    public Project(String projectName) {
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
        if (tasks == null) {
            tasks = TaskRepository.gatTaskRepository().findAll();
        }
        return tasks;
    }
    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}