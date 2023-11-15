package by.anabios13.dto;

public class TaskDTO {

    private int taskId;
    private String taskName;
    private int projectId;

    public TaskDTO(){}

    public TaskDTO(int taskId, String taskName, int projectId) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.projectId = projectId;
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

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }
}


