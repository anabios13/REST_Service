package by.anabios13.repositories.impl;

import by.anabios13.db.DataSource;
import by.anabios13.exceptions.CRUDException;
import by.anabios13.exceptions.CreateException;
import by.anabios13.exceptions.DeleteException;
import by.anabios13.exceptions.ReadException;
import by.anabios13.models.Employee;
import by.anabios13.repositories.IEmployeeRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository implements IEmployeeRepository {
    private List<Employee> employees;
    @Override
    public List<Employee> getAllEmployees() {
        employees = new ArrayList<>();

        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT * FROM employee"
             );
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Employee employee = new Employee();
                employee.setEmployeeId(resultSet.getInt("employee_id"));
                employee.setEmployeeName(resultSet.getString("employee_name"));
               // employee.setTasks();
                employees.add(employee);
            }
        } catch (SQLException e) {
            System.out.println(e.getStackTrace());
            throw new CRUDException("");
        }

        return employees;
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
            System.out.println(e.getStackTrace());
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
