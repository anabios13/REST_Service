package by.anabios13.models;

public class EmployeeTask {

    private int employeeId;
    private int taskId;

    public EmployeeTask(){}

    public EmployeeTask(int employeeId, int taskId) {
        this.employeeId = employeeId;
        this.taskId = taskId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }
}

