package by.anabios13.services.impl;

import by.anabios13.dto.EmployeeDTO;
import by.anabios13.mappers.impl.EmployeeMapper;
import by.anabios13.mappers.IEmployeeMapper;
import by.anabios13.models.Employee;
import by.anabios13.repositories.impl.EmployeeRepository;
import by.anabios13.services.IEmployeeService;

import java.util.List;
import java.util.stream.Collectors;

public class EmployeeService implements IEmployeeService {
    private final EmployeeRepository employeeRepository = EmployeeRepository.getEmployeeRepository();
    private final IEmployeeMapper employeeMapper = new EmployeeMapper();

    public EmployeeDTO saveEmployee(EmployeeDTO employeeDTO) {
        Employee employee = employeeMapper.employeeDTOToEmployee(employeeDTO);
        Employee savedEmployee = employeeRepository.save(employee);
        return employeeMapper.employeeToEmployeeDTO(savedEmployee);
    }

    public EmployeeDTO getEmployeeById(int employeeId) {
        Employee employee = employeeRepository.getEmployeeById(employeeId);
        return employeeMapper.employeeToEmployeeDTO(employee);
    }

    public List<EmployeeDTO> getAllEmployees() {
        List<Employee> employees = employeeRepository.getAllEmployees();
        return employees.stream()
                .map(employeeMapper::employeeToEmployeeDTO)
                .collect(Collectors.toList());
    }

    public void updateEmployee(EmployeeDTO employeeDTO) {
        Employee employee = employeeMapper.employeeDTOToEmployee(employeeDTO);
        employeeRepository.updateEmployee(employee);
    }

    public void deleteEmployee(int employeeId) {
        employeeRepository.deleteEmployee(employeeId);
    }
}

