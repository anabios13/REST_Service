package by.anabios13.repositoryTests;

import by.anabios13.db.DataSource;
import by.anabios13.models.Employee;
import by.anabios13.repositories.impl.EmployeeRepository;
import by.anabios13.util.PostgreSQLContainer;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.testcontainers.ext.ScriptUtils;
import org.testcontainers.jdbc.JdbcDatabaseDelegate;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;


@Testcontainers
@Tag("DockerRequired")
class EmployeeRepositoryTest {

    @Mock
    private DataSource dataSource;
    private EmployeeRepository employeeRepository;
    private static JdbcDatabaseDelegate jdbcDatabaseDelegate;

    @BeforeAll
    static void beforeAll() {
        PostgreSQLContainer.container.start();
        jdbcDatabaseDelegate = new JdbcDatabaseDelegate(PostgreSQLContainer.container, "");
    }

    @AfterAll
    static void afterAll() {
        PostgreSQLContainer.container.stop();
    }

    @BeforeEach
    void setUp() {
        dataSource = new DataSource();
         employeeRepository = new EmployeeRepository(dataSource);
        ScriptUtils.runInitScript(jdbcDatabaseDelegate, PostgreSQLContainer.INIT_SQL);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM employee")) {
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testSave() {
        Employee employee = new Employee("Test Employee");

        employee = employeeRepository.save(employee);
        Employee savedEmployee = employeeRepository.getEmployeeById(employee.getEmployeeId());
        Assertions.assertNotEquals(0,employee.getEmployeeId());
        Assertions.assertEquals(savedEmployee.getEmployeeId(),employee.getEmployeeId());
        Assertions.assertEquals("Test Employee", savedEmployee.getEmployeeName());
    }

    @Test
   void testGetEmployeeById() {
        Employee employee = new Employee("Test Employee");
        employee = employeeRepository.save(employee);

        Employee foundEmployee = employeeRepository.getEmployeeById(employee.getEmployeeId());

        Assertions.assertNotNull(foundEmployee);
        Assertions.assertEquals("Test Employee", foundEmployee.getEmployeeName());
    }

    @Test
  void testGetAllEmployees() {
        Employee employee1 = new Employee("Employee 1");
        Employee employee2 = new Employee("Employee 2");
        employee1 = employeeRepository.save(employee1);
        employee2 = employeeRepository.save(employee2);

        List<Employee> employees = employeeRepository.getAllEmployees();

        Assertions.assertEquals(2, employees.size());
    }

    @Test
    void testUpdateEmployee() {
        Employee employee = new Employee("Test Employee");
        employee = employeeRepository.save(employee);

        employee.setEmployeeName("Updated Employee");
        employeeRepository.updateEmployee(employee);

        Employee updatedEmployee = employeeRepository.getEmployeeById(employee.getEmployeeId());

        Assertions.assertEquals("Updated Employee", updatedEmployee.getEmployeeName());
    }

    @Test
 void testDeleteEmployee() {
        Employee employee = new Employee("Test Employee");
        employee = employeeRepository.save(employee);

        employeeRepository.deleteEmployee(employee.getEmployeeId());

        Employee deletedEmployee = employeeRepository.getEmployeeById(employee.getEmployeeId());

        Assertions.assertNull(deletedEmployee);
    }
}
