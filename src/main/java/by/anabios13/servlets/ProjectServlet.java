package by.anabios13.servlets;

import by.anabios13.dto.ProjectDTO;
import by.anabios13.services.impl.ProjectService;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/projects")
public class ProjectServlet extends HttpServlet {
    private final ProjectService projectService = new ProjectService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String projectIdParam = request.getParameter("projectId");

        if (projectIdParam != null && !projectIdParam.isEmpty()) {
            // Получение проекта по ID
            int projectId = Integer.parseInt(projectIdParam);
            ProjectDTO projectDTO = projectService.getProjectById(projectId);

            if (projectDTO != null) {
                String projectJson = new Gson().toJson(projectDTO);
                response.setContentType("application/json");
                response.getWriter().write(projectJson);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } else {
            // Получение списка всех проектов
            List<ProjectDTO> projects = projectService.getAllProjects();
            String projectsJson = new Gson().toJson(projects);
            response.setContentType("application/json");
            response.getWriter().write(projectsJson);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Создание или обновление проекта
        String projectName = request.getParameter("projectName");

        if (projectName != null && !projectName.isEmpty()) {
            String projectIdParam = request.getParameter("projectId");

            if (projectIdParam != null && !projectIdParam.isEmpty()) {
                // Обновление проекта
                int projectId = Integer.parseInt(projectIdParam);
                ProjectDTO existingProject = projectService.getProjectById(projectId);

                if (existingProject != null) {
                    existingProject.setProjectName(projectName);
                    projectService.updateProject(existingProject);
                    String updatedProjectJson = new Gson().toJson(existingProject);
                    response.setContentType("application/json");
                    response.getWriter().write(updatedProjectJson);
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            } else {
                // Создание нового проекта
                ProjectDTO newProject = new ProjectDTO();
                newProject.setProjectName(projectName);
                ProjectDTO createdProject = projectService.saveProject(newProject);
                String createdProjectJson = new Gson().toJson(createdProject);
                response.setContentType("application/json");
                response.getWriter().write(createdProjectJson);
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Project name cannot be empty");
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String projectIdParam = request.getParameter("projectId");
        String projectName = request.getParameter("projectName");

        if (projectIdParam != null && !projectIdParam.isEmpty() && projectName != null && !projectName.isEmpty()) {
            int projectId = Integer.parseInt(projectIdParam);
            ProjectDTO projectDTO = projectService.getProjectById(projectId);

            if (projectDTO != null) {
                projectDTO.setProjectName(projectName);
                projectService.updateProject(projectDTO);
                String updatedProjectJson = new Gson().toJson(projectDTO);
                response.setContentType("application/json");
                response.getWriter().write(updatedProjectJson);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Project ID and name are required for update");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String projectIdParam = request.getParameter("projectId");

        if (projectIdParam != null && !projectIdParam.isEmpty()) {
            int projectId = Integer.parseInt(projectIdParam);
            projectService.deleteProject(projectId);
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Project ID is required for deletion");
        }
    }
}
