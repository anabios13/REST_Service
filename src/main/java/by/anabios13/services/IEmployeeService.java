package by.anabios13.services;

import by.anabios13.dto.EmployeeDTO;

import java.util.List;

public interface IEmployeeService {
    EmployeeDTO saveEmployee(EmployeeDTO employeeDTO);

    EmployeeDTO getEmployeeById(int employeeId);

    List<EmployeeDTO> getAllEmployees();

    void updateEmployee(EmployeeDTO employeeDTO);

    void deleteEmployee(int employeeId);
}
