package by.anabios13.models;

import by.anabios13.repositories.impl.EmployeeRepository;

import java.util.List;

public class Task {

    private int taskId;
    private String taskName;

    private List<Employee> performers;

    public Task() {
    }

    public Task(int taskId, String taskName) {
        this.taskId = taskId;
        this.taskName = taskName;
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

    public List<Employee> getPerformers() {

        if (performers == null) {
            performers = EmployeeRepository.getEmployeeRepository().getAllEmployees();
        }
        return performers;
    }

    public void setPerformers(List<Employee> performers) {
        this.performers = performers;
    }
}


