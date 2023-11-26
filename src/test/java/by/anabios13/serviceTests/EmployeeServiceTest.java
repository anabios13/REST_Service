package by.anabios13.serviceTests;


import by.anabios13.dto.EmployeeDTO;
import by.anabios13.mappers.impl.EmployeeMapper;
import by.anabios13.models.Employee;
import by.anabios13.repositories.impl.EmployeeRepository;
import by.anabios13.services.impl.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    private EmployeeMapper employeeMapper=new EmployeeMapper();

    private EmployeeService employeeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        employeeService = new EmployeeService(employeeRepository, employeeMapper);
    }

    @Test
    void saveEmployee() {
        // Arrange
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setEmployeeName("Test Employee");

        Employee savedEmployee = new Employee();
        savedEmployee.setEmployeeId(1);
        savedEmployee.setEmployeeName("Test Employee");

        when(employeeRepository.save(any(Employee.class))).thenReturn(savedEmployee);

        // Act
        EmployeeDTO result = employeeService.saveEmployee(employeeDTO);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getEmployeeId());
        assertEquals("Test Employee", result.getEmployeeName());

        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    void getEmployeeById() {
        // Arrange
        Employee employee = new Employee();
        employee.setEmployeeId(1);
        employee.setEmployeeName("Test Employee");

        when(employeeRepository.getEmployeeById(1)).thenReturn(employee);

        // Act
        EmployeeDTO result = employeeService.getEmployeeById(1);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getEmployeeId());
        assertEquals("Test Employee", result.getEmployeeName());

        verify(employeeRepository, times(1)).getEmployeeById(1);
    }

    @Test
    void getAllEmployees() {
        // Arrange
        Employee employee1 = new Employee();
        employee1.setEmployeeId(1);
        employee1.setEmployeeName("Employee 1");

        Employee employee2 = new Employee();
        employee2.setEmployeeId(2);
        employee2.setEmployeeName("Employee 2");

        List<Employee> employees = Arrays.asList(employee1, employee2);

        when(employeeRepository.getAllEmployees()).thenReturn(employees);

        // Act
        List<EmployeeDTO> result = employeeService.getAllEmployees();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Employee 1", result.get(0).getEmployeeName());
        assertEquals("Employee 2", result.get(1).getEmployeeName());

        verify(employeeRepository, times(1)).getAllEmployees();
    }

}

