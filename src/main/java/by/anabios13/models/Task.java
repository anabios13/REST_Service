package by.anabios13.models;

import java.util.List;

public class Task {

    private int taskId;
    private String taskName;
    private Project project;

    private List<Employee> performers;

    public Task() {
    }

    public Task(int taskId, String taskName, Project project) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.project = project;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public List<Employee> getPerformers() {
        return performers;
    }

    public void setPerformers(List<Employee> performers) {
        this.performers = performers;
    }
}


