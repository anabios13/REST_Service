package by.anabios13.mappers.impl;

import by.anabios13.dto.EmployeeDTO;
import by.anabios13.mappers.IEmployeeMapper;
import by.anabios13.models.Employee;

public class EmployeeMapper implements IEmployeeMapper {
    @Override
    public EmployeeDTO employeeToEmployeeDTO(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setEmployeeId(employee.getEmployeeId());
        employeeDTO.setEmployeeName(employee.getEmployeeName());
        employeeDTO.setTasks(employee.getTasks());
             return employeeDTO;
    }

    @Override
    public Employee employeeDTOToEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setEmployeeId(employeeDTO.getEmployeeId());
        employee.setEmployeeName(employeeDTO.getEmployeeName());
        employee.setTasks(employeeDTO.getTasks());
        return employee;
    }
}
