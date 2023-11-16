package by.anabios13.repositories;

import by.anabios13.models.Employee;

import java.util.List;

public interface IEmployeeRepository {

    List<Employee> getAllEmployees();

    Employee getEmployeeById(int employeeId);

    void addEmployee(Employee employee);

    void updateEmployee(Employee employee);

    void deleteEmployee(int employeeId);
}
