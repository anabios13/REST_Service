package by.anabios13.serviceTests;


import by.anabios13.repositories.impl.EmployeeRepository;
import by.anabios13.services.impl.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

class EmployeeServiceTest {

    private EmployeeRepository employeeRepository ;


    @InjectMocks
    private EmployeeService employeeService = new EmployeeService();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

//    @Test
//    void saveEmployee() {
//        // Arrange
//        EmployeeDTO employeeDTO = new EmployeeDTO();
//        employeeDTO.setEmployeeName("Test Employee");
//
//        Employee savedEmployee = new Employee();
//        savedEmployee.setEmployeeId(1);
//        savedEmployee.setEmployeeName("Test Employee");
//
//        when(employeeRepository.save(any(Employee.class))).thenReturn(savedEmployee);
//
//        // Act
//        EmployeeDTO result = employeeService.saveEmployee(employeeDTO);
//
//        // Assert
//        assertNotNull(result);
//        assertEquals(1, result.getEmployeeId());
//        assertEquals("Test Employee", result.getEmployeeName());
//
//        verify(employeeRepository, times(1)).save(any(Employee.class));
//    }

//    @Test
//    void getEmployeeById() {
//        // Arrange
//        Employee employee = new Employee();
//        employee.setEmployeeId(1);
//        employee.setEmployeeName("Test Employee");
//
//        when(employeeRepository.getEmployeeById(1)).thenReturn(employee);
//
//        // Act
//        EmployeeDTO result = employeeService.getEmployeeById(1);
//
//        // Assert
//        assertNotNull(result);
//        assertEquals(1, result.getEmployeeId());
//        assertEquals("Test Employee", result.getEmployeeName());
//
//        verify(employeeRepository, times(1)).getEmployeeById(1);
//    }
//
//    @Test
//    void getAllEmployees() {
//        // Arrange
//        Employee employee1 = new Employee();
//        employee1.setEmployeeId(1);
//        employee1.setEmployeeName("Employee 1");
//
//        Employee employee2 = new Employee();
//        employee2.setEmployeeId(2);
//        employee2.setEmployeeName("Employee 2");
//
//        List<Employee> employees = Arrays.asList(employee1, employee2);
//
//        when(employeeRepository.getAllEmployees()).thenReturn(employees);
//
//        // Act
//        List<EmployeeDTO> result = employeeService.getAllEmployees();
//
//        // Assert
//        assertNotNull(result);
//        assertEquals(2, result.size());
//        assertEquals("Employee 1", result.get(0).getEmployeeName());
//        assertEquals("Employee 2", result.get(1).getEmployeeName());
//
//        verify(employeeRepository, times(1)).getAllEmployees();
//    }
//
//    @Test
//    void updateEmployee() {
//        // Arrange
//        EmployeeDTO employeeDTO = new EmployeeDTO();
//        employeeDTO.setEmployeeId(1);
//        employeeDTO.setEmployeeName("Updated Employee");
//
//        Employee existingEmployee = new Employee();
//        existingEmployee.setEmployeeId(1);
//        existingEmployee.setEmployeeName("Old Employee");
//
//        when(employeeRepository.getEmployeeById(1)).thenReturn(existingEmployee);
//
//        // Act
//        employeeService.updateEmployee(employeeDTO);
//
//        // Assert
//        verify(employeeRepository, times(1)).getEmployeeById(1);
//        verify(employeeRepository, times(1)).updateEmployee(existingEmployee);
//
//        assertEquals("Updated Employee", existingEmployee.getEmployeeName());
//    }
//
//    @Test
//    void updateEmployee_NotFound() {
//        // Arrange
//        EmployeeDTO employeeDTO = new EmployeeDTO();
//        employeeDTO.setEmployeeId(1);
//        employeeDTO.setEmployeeName("Updated Employee");
//
//        when(employeeRepository.getEmployeeById(1)).thenReturn(null);
//
//        // Act and Assert
//        assertThrows(UpdateException.class, () -> employeeService.updateEmployee(employeeDTO));
//
//        verify(employeeRepository, times(1)).getEmployeeById(1);
//        verify(employeeRepository, never()).updateEmployee(any(Employee.class));
//    }

}

