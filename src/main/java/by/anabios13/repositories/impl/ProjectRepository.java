package by.anabios13.repositories.impl;

import by.anabios13.db.DataSource;
import by.anabios13.exceptions.CRUDException;
import by.anabios13.models.Project;
import by.anabios13.repositories.IProjectRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectRepository implements IProjectRepository {

    private static ProjectRepository projectRepository;

    private ProjectRepository() {
    }

    public static synchronized ProjectRepository getProjectRepository() {
        if (projectRepository == null) {
            projectRepository = new ProjectRepository();
        }
        return projectRepository;
    }

    public List<Project> getAllProjects(){
        List<Project> projects = new ArrayList<>();
        String query = "SELECT * FROM project";

        try (Connection connection= DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Project project = new Project();
                int projectId =resultSet.getInt("project_id");
                project.setProjectId(projectId);
                project.setProjectName(resultSet.getString("project_name"));
                projects.add(project);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projects;
    }

    public Project save(Project project) {
        try (Connection connection = DataSource.getConnection()) {
            String sql = "INSERT INTO Project (project_name) VALUES (?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, project.getProjectName());
                preparedStatement.executeUpdate();

                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                       project.setProjectId(generatedKeys.getInt(1));
                    } else {
                        throw new SQLException("Creating task failed, no ID obtained.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Обработка ошибок
        }
        return project;
    }

    @Override
    public Project getProjectById(int projectId) {
        Project project = new Project();
        String query = "SELECT * FROM project WHERE project_id="+projectId;

        try (Connection connection= DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

                project.setProjectId(resultSet.getInt("project_id"));
                project.setProjectName(resultSet.getString("project_name"));

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new CRUDException("An error occurred while trying to get projects");
        }
        return project;
    }

    @Override
    public void addProject(Project project) {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO project (project_name) VALUES (?)")) {

            preparedStatement.setString(1, project.getProjectName());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getStackTrace());
            throw new CRUDException("An error occurred while trying to add project");
        }
    }

    @Override
    public void updateProject(Project project)  {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "UPDATE project (project_name) VALUES (?)")) {

            preparedStatement.setString(1, project.getProjectName());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new CRUDException("An error occurred while trying to update project");
        }
    }

    @Override
    public void deleteProject(int projectId) {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "DELETE FROM project WHERE project_id = ?")) {

            preparedStatement.setInt(1, projectId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
           throw new CRUDException("An error occurred while trying to delete project");
        }
    }
}

