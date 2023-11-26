package by.anabios13.models;

import by.anabios13.repositories.impl.TaskRepository;

import java.util.List;

public class Employee {

    private int employeeId;
    private String employeeName;

    private List<Task> tasks;

    private TaskRepository taskRepository=new TaskRepository();

    public Employee(){}

    public Employee(String employeeName) {
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
        if (tasks == null) {
            tasks = taskRepository.findAll();
        }
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}

