package by.anabios13.servlets;

import by.anabios13.db.DataSource;
import by.anabios13.dto.EmployeeDTO;
import by.anabios13.mappers.impl.EmployeeMapper;
import by.anabios13.repositories.impl.EmployeeRepository;
import by.anabios13.services.impl.EmployeeService;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/employees")
public class EmployeeServlet extends HttpServlet {
    private EmployeeService employeeService;

    public EmployeeServlet() {
    }

    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public void init() throws ServletException {
        EmployeeMapper employeeMapper = new EmployeeMapper();
        DataSource dataSource = new DataSource();
        EmployeeRepository employeeRepository = new EmployeeRepository(dataSource);
        employeeService = new EmployeeService(employeeRepository,employeeMapper);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String employeeIdParam = request.getParameter("employeeId");

        if (employeeIdParam != null && !employeeIdParam.isEmpty()) {
            int employeeId = Integer.parseInt(employeeIdParam);
            EmployeeDTO employeeDTO = employeeService.getEmployeeById(employeeId);

            if (employeeDTO != null) {
                String employeeJson = new Gson().toJson(employeeDTO);
                response.setContentType("application/json");
                response.getWriter().write(employeeJson);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } else {
            // Получение списка всех сотрудников
            List<EmployeeDTO> employees = employeeService.getAllEmployees();
            String employeesJson = new Gson().toJson(employees);
            response.setContentType("application/json");
            response.getWriter().write(employeesJson);
        }
    }
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Создание или обновление сотрудника
        String employeeName = request.getParameter("employeeName");

        if (employeeName != null && !employeeName.isEmpty()) {
            String employeeIdParam = request.getParameter("employeeId");

            if (employeeIdParam != null && !employeeIdParam.isEmpty()) {
                // Обновление сотрудника
                int employeeId = Integer.parseInt(employeeIdParam);
                EmployeeDTO existingEmployee = employeeService.getEmployeeById(employeeId);

                if (existingEmployee != null) {
                    existingEmployee.setEmployeeName(employeeName);
                    employeeService.updateEmployee(existingEmployee);
                    String updatedEmployeeJson = new Gson().toJson(existingEmployee);
                    response.setContentType("application/json");
                    response.getWriter().write(updatedEmployeeJson);
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            } else {
                // Создание нового сотрудника
                EmployeeDTO newEmployee = new EmployeeDTO();
                newEmployee.setEmployeeName(employeeName);
                EmployeeDTO createdEmployee = employeeService.saveEmployee(newEmployee);
                String createdEmployeeJson = new Gson().toJson(createdEmployee);
                response.setContentType("application/json");
                response.getWriter().write(createdEmployeeJson);
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Employee name cannot be empty");
        }
    }

    @Override
    public void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String employeeIdParam = request.getParameter("employeeId");
        String employeeName = request.getParameter("employeeName");

        if (employeeIdParam != null && !employeeIdParam.isEmpty() && employeeName != null && !employeeName.isEmpty()) {
            int employeeId = Integer.parseInt(employeeIdParam);
            EmployeeDTO employeeDTO = employeeService.getEmployeeById(employeeId);

            if (employeeDTO != null) {
                employeeDTO.setEmployeeName(employeeName);
                employeeService.updateEmployee(employeeDTO);
                String updatedEmployeeJson = new Gson().toJson(employeeDTO);
                response.setContentType("application/json");
                response.getWriter().write(updatedEmployeeJson);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Employee ID and name are required for update");
        }
    }

    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String employeeIdParam = request.getParameter("employeeId");

        if (employeeIdParam != null && !employeeIdParam.isEmpty()) {
            int employeeId = Integer.parseInt(employeeIdParam);
            employeeService.deleteEmployee(employeeId);
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Employee ID is required for deletion");
        }
    }
}

