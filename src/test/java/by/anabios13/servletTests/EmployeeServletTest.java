package by.anabios13.servletTests;

import by.anabios13.dto.EmployeeDTO;
import by.anabios13.services.IEmployeeService;
import by.anabios13.services.impl.EmployeeService;
import by.anabios13.servlets.EmployeeServlet;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

class EmployeeServletTest {

    @Mock
    private EmployeeService employeeService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @InjectMocks
    private EmployeeServlet employeeServlet;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        employeeServlet = new EmployeeServlet();
        employeeServlet.init();
        employeeServlet.setEmployeeService(employeeService);
    }

    @Test
    void testDoGetWithEmployeeId() throws Exception {
        // Arrange
        int employeeId = 1;
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setEmployeeId(employeeId);
        employeeDTO.setEmployeeName("Test Employee");

        when(request.getParameter("employeeId")).thenReturn(String.valueOf(employeeId));
        when(employeeService.getEmployeeById(employeeId)).thenReturn(employeeDTO);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        // Act
        employeeServlet.doGet(request, response);

        // Assert
        assertTrue(stringWriter.toString().contains("Test Employee"));
        verify(response).setContentType("application/json");
    }

    @Test
    void testDoGetWithoutEmployeeId() throws Exception {
        // Arrange
        EmployeeDTO employee1 = new EmployeeDTO();
        employee1.setEmployeeId(1);
        employee1.setEmployeeName("Employee 1");

        EmployeeDTO employee2 = new EmployeeDTO();
        employee2.setEmployeeId(2);
        employee2.setEmployeeName("Employee 2");

        List<EmployeeDTO> employees = Arrays.asList(employee1, employee2);

        when(employeeService.getAllEmployees()).thenReturn(employees);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        // Act
        employeeServlet.doGet(request, response);

        // Assert
        assertTrue(stringWriter.toString().contains("Employee 1"));
        assertTrue(stringWriter.toString().contains("Employee 2"));
        verify(response).setContentType("application/json");
    }

    @Test
    void testDoPost() throws Exception {
        // Arrange
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setEmployeeName("Test Employee");

        when(request.getParameter("employeeName")).thenReturn("Test Employee");
        when(employeeService.saveEmployee(any(EmployeeDTO.class))).thenReturn(employeeDTO);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        // Act
        employeeServlet.doPost(request, response);

        // Assert
        assertTrue(stringWriter.toString().contains("Test Employee"));
        verify(response).setContentType("application/json");
    }

    @Test
    void testDoPut() throws Exception {
        // Arrange
        int employeeId = 1;
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setEmployeeId(employeeId);
        employeeDTO.setEmployeeName("Updated Employee");

        when(request.getParameter("employeeId")).thenReturn(String.valueOf(employeeId));
        when(request.getParameter("employeeName")).thenReturn("Updated Employee");
        when(employeeService.getEmployeeById(employeeId)).thenReturn(employeeDTO);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        // Act
        employeeServlet.doPut(request, response);

        // Assert
        assertTrue(stringWriter.toString().contains("Updated Employee"));
        verify(response).setContentType("application/json");
    }

    @Test
    void testDoDelete() throws Exception {
        // Arrange
        int employeeId = 1;

        when(request.getParameter("employeeId")).thenReturn(String.valueOf(employeeId));

        // Act
        employeeServlet.doDelete(request, response);

        // Assert
        verify(response).setStatus(HttpServletResponse.SC_OK);
        verify(employeeService).deleteEmployee(employeeId);
    }
}
