package by.anabios13.repositories.impl;

import by.anabios13.db.DataSource;
import by.anabios13.models.Employee;
import by.anabios13.models.Task;
import by.anabios13.exceptions.CRUDException;
import by.anabios13.repositories.ITaskRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TaskRepository implements ITaskRepository {

    private ProjectRepository projectRepository;
    private EmployeeRepository employeeRepository;
    private static TaskRepository taskRepository;
    private List<Task> taskList;

    public TaskRepository(ProjectRepository projectRepository, EmployeeRepository employeeRepository) {
        this.projectRepository = projectRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<Task> getAllTasks() {
       taskList = new ArrayList<>();

        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT * FROM task"
             );
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Task task = new Task();
                int projectId = resultSet.getInt("project_id");
                task.setTaskId(resultSet.getInt("task_id"));
                task.setTaskName(resultSet.getString("task_name"));
                task.setProject(projectRepository.getProjectById(projectId));
                // task.setPerformers();

                taskList.add(task);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

        return taskList;
    }

    @Override
    public List<Task> getAllTasksByProject(int projectId) {
        taskList = new ArrayList<>();

        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT * FROM task WHERE  project_id="+projectId
             );
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Task task = new Task();
                task.setTaskId(resultSet.getInt("task_id"));
                task.setTaskName(resultSet.getString("task_name"));
                task.setPerformers(getEmployeesForTask(task.getTaskId()));
                // task.setPerformers();

                taskList.add(task);
            }

        } catch (SQLException e) {
            System.out.println(e.getStackTrace());
            throw new CRUDException("An error occurred while trying to get tasks by the project");
        }

        return taskList;
    }

    @Override
    public Task getTaskById(int taskId) {
        Task task = new Task();
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT * FROM Task WHERE task_id=" + taskId
             );
             ResultSet resultSet = preparedStatement.executeQuery()) {
             int projectId =resultSet.getInt("project_id");
             task.setTaskId(resultSet.getInt("task_id"));
             task.setTaskName(resultSet.getString("task_name"));
             task.setProject(projectRepository.getProjectById(projectId));

        } catch (SQLException e) {
            System.out.println(e.getStackTrace());
            e.printStackTrace();
            throw new CRUDException("An error occurred while trying to get task by id\n"+e.getMessage());
        }

        return task;
    }


        public List<Task> getTaskByEmployee(int employeeId) {
        taskList = new ArrayList<>();

        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT * FROM  employee_task WHERE  employee_id="+employeeId
             );
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Task task = new Task();
                int projectId =resultSet.getInt("project_id");
                task = getTaskById(resultSet.getInt("task_id"));
                task.setTaskName(resultSet.getString("task_name"));
                task.setProject(projectRepository.getProjectById(projectId));
                //  task.setPerformers(employeeRepository.getEmployee);
                taskList.add(task);
            }

        } catch (SQLException e) {
            System.out.println(e.getStackTrace());
            throw new CRUDException("An error occurred while trying to get tasks by the project");
        }

        return taskList;
    }

    public List<Employee> getEmployeesForTask(int taskId) {
        List<Employee> employeeList = new ArrayList<>();

        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT e.* FROM Employee e " +
                             "JOIN TaskEmployee te ON e.employee_id = te.employee_id " +
                             "WHERE te.task_id = ?")) {

            preparedStatement.setInt(1, taskId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Employee employee = new Employee();
                employee.setEmployeeId(resultSet.getInt("employee_id"));
                employee.setEmployeeName(resultSet.getString("employee_name"));
                employeeList.add(employee);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Обработка ошибок
        }

        return employeeList;
    }

    @Override
    public void addTask(Task task) {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO task (task_name, project_id) VALUES (?, ?)")) {

            if(task.getProject().equals(null)||task.getTaskName().equals(null))
                throw new CRUDException(
                        "An error occurred while trying to add new task." +
                         "Incorrect data may have been entered"
                );

            preparedStatement.setString(1, task.getTaskName());
            preparedStatement.setInt(2, task.getProject().getProjectId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getStackTrace());
            throw new CRUDException("An error occurred while trying to add task");
        }
    }

    @Override
    public void updateTask(Task changedTask) {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "UPDATE task (task_id, task_name, project_id) VALUES (?, ?, ?)")) {
            if(changedTask.getProject().equals(null)||changedTask.getTaskName().equals(null))
                throw new CRUDException(
                        "An error occurred while trying to add new task." +
                                "Incorrect data may have been entered"
                );

            preparedStatement.setInt(1, changedTask.getTaskId());
            preparedStatement.setString(2, changedTask.getTaskName());
            preparedStatement.setInt(3, changedTask.getProject().getProjectId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getStackTrace());
            throw new CRUDException("An error occurred while trying to update task");
        }
    }

    @Override
    public void deleteTask(int taskId) {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "DELETE FROM task WHERE task_id = ?")) {

            preparedStatement.setInt(1, taskId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getStackTrace());
            throw new CRUDException("An error occurred while trying to delete task");
        }
    }
}
