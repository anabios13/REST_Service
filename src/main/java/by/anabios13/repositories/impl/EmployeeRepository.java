package by.anabios13.repositories.impl;

import by.anabios13.models.Task;
import by.anabios13.repositories.IEmployeeRepository;
import by.anabios13.db.DataSource;
import by.anabios13.exceptions.CRUDException;
import by.anabios13.exceptions.CreateException;
import by.anabios13.exceptions.DeleteException;
import by.anabios13.exceptions.ReadException;
import by.anabios13.models.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository implements IEmployeeRepository {

    private static EmployeeRepository employeeRepository;

    private EmployeeRepository() {
    }

    public static synchronized EmployeeRepository getEmployeeRepository() {
        if (employeeRepository == null) {
            employeeRepository = new EmployeeRepository();
        }
        return employeeRepository;
    }

    @Override
    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();

        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT * FROM employee"
             );
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Employee employee = new Employee();
                employee.setEmployeeId(resultSet.getInt("employee_id"));
                employee.setEmployeeName(resultSet.getString("employee_name"));
                employee.setTasks(getTasksForEmployee(employee.getEmployeeId()));
                employees.add(employee);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new CRUDException("");
        }

        return employees;
    }

    public List<Task> getTasksForEmployee(int employeeId) {
        List<Task> taskList = new ArrayList<>();

        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT t.* FROM Task t " +
                             "JOIN TaskEmployee te ON t.task_id = te.task_id " +
                             "WHERE te.employee_id = ?")) {

            preparedStatement.setInt(1, employeeId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Task task = new Task();
                task.setTaskId(resultSet.getInt("task_id"));
                task.setTaskName(resultSet.getString("task_name"));
                taskList.add(task);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Обработка ошибок
        }

        return taskList;
    }

    @Override
    public Employee getEmployeeById(int employeeId) {
        Employee employee = new Employee();
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT * FROM employee Where empl" +
                             "");
             ResultSet resultSet = preparedStatement.executeQuery()) {
                if(resultSet.getInt("employee_id")==0)
                    throw new ReadException("Employee wasn't found");
                employee.setEmployeeId(resultSet.getInt("employee_id"));
                employee.setEmployeeName(resultSet.getString("employee_name"));
        } catch (SQLException e) {
            throw new CRUDException("");
        }

        return employee;
    }

    @Override
    public void addEmployee(Employee employee) {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO employee (employee_name) VALUES (?)")) {

            preparedStatement.setString(1, employee.getEmployeeName());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            throw new CreateException("");
        }
    }

    @Override
    public void updateEmployee(Employee employee) {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "UPDATE Employee (employee_name) VALUES (?)")) {

            preparedStatement.setString(1, employee.getEmployeeName());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            // Обработка ошибок
        }
    }

    @Override
    public void deleteEmployee(int employeeId) {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "DELETE FROM Employee WHERE employee_id = ?")) {

            preparedStatement.setInt(1, employeeId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DeleteException("");
        }
    }
}
