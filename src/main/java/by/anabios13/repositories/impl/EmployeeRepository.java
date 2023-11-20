package by.anabios13.repositories.impl;

import by.anabios13.db.DataSource;
import by.anabios13.exceptions.*;
import by.anabios13.models.Employee;
import by.anabios13.repositories.IEmployeeRepository;

import java.sql.*;
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
                Employee employee = mapResultSetToEmployee(resultSet);
                employees.add(employee);
            }
        } catch (SQLException e) {
            throw new ReadException(e.getMessage());
        }

        return employees;
    }

    @Override
    public Employee getEmployeeById(int employeeId) {
        Employee employee = null;
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT * FROM employee WHERE employee_id=?"
             )) {
            preparedStatement.setInt(1, employeeId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    employee = mapResultSetToEmployee(resultSet);
                } else return null;
            }

        } catch (SQLException e) {
            throw new ReadException(e.getMessage());
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
            throw new CreateException(e.getMessage());
        }
    }

    @Override
    public void updateEmployee(Employee employee) {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "UPDATE employee SET employee_name=? WHERE employee_id=?")) {

            preparedStatement.setString(1, employee.getEmployeeName());
            preparedStatement.setInt(2, employee.getEmployeeId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new UpdateException(e.getMessage());
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
            throw new DeleteException(e.getMessage());
        }
    }

    public Employee save(Employee employee) {
        try (Connection connection = DataSource.getConnection()) {
            String sql = "INSERT INTO employee (employee_name) VALUES (?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, employee.getEmployeeName());
                preparedStatement.executeUpdate();

                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        employee.setEmployeeId(generatedKeys.getInt(1));
                    } else {
                        throw new SQLException("Creating task failed, no ID obtained.");
                    }
                }
            }
        } catch (SQLException e) {
            throw new CreateException(e.getMessage());
        }

        return employee;
    }

    private Employee mapResultSetToEmployee(ResultSet resultSet) throws SQLException {
        Employee employee = new Employee();
        employee.setEmployeeId(resultSet.getInt("employee_id"));
        employee.setEmployeeName(resultSet.getString("employee_name"));

        return employee;
    }
}
