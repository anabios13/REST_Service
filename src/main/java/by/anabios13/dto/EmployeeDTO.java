package by.anabios13.dto;

import by.anabios13.models.Task;

import java.util.List;

public class EmployeeDTO {

    private int employeeId;
    private String employeeName;

    private List<Task> tasks;

    public EmployeeDTO(){}

    public EmployeeDTO(String employeeName) {
        this.employeeName = employeeName;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}

