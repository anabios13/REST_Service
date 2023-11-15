package by.anabios13.dto;

public class EmployeeTaskDTO {

    private int employeeId;
    private int taskId;

    public EmployeeTaskDTO(){}

    public EmployeeTaskDTO(int employeeId, int taskId) {
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

