package by.anabios13.repositories.impl;

import by.anabios13.db.DataSource;
import by.anabios13.exceptions.*;
import by.anabios13.models.Project;
import by.anabios13.repositories.IProjectRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectRepository implements IProjectRepository {

    public ProjectRepository() {
    }

    public List<Project> getAllProjects() {
        List<Project> projects = new ArrayList<>();
        String query = "SELECT * FROM project";

        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Project project = mapResultSetToProject(resultSet);
                projects.add(project);
            }
        } catch (SQLException e) {
            throw new ReadException(e.getMessage());
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
                        throw new CreateException("Creating task failed, no ID obtained.");
                    }
                }
            }
        } catch (SQLException e) {
            throw new CreateException(e.getMessage());
        }
        return project;
    }

    @Override
    public Project getProjectById(int projectId) {
        Project project = new Project();

        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT * FROM project WHERE project_id=?")) {

            preparedStatement.setInt(1, projectId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    project = mapResultSetToProject(resultSet);
                }
                else return null;
            }

        } catch (SQLException e) {
            throw new ReadException(e.getMessage());
        }
        return project;
    }

    @Override
    public void updateProject(Project project) {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "UPDATE project SET project_name=? WHERE project_id = ?")) {

            preparedStatement.setString(1, project.getProjectName());
            preparedStatement.setInt(2, project.getProjectId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new UpdateException(e.getMessage());
        }
    }

    @Override
    public void deleteProject(int projectId) {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "DELETE  FROM project WHERE project_id = ?")) {

            preparedStatement.setInt(1, projectId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DeleteException(e.getMessage());
        }
    }

    private Project mapResultSetToProject(ResultSet resultSet) throws SQLException {
        Project project = new Project();
        project.setProjectId(resultSet.getInt("project_id"));
        project.setProjectName(resultSet.getString("project_name"));
        return project;
    }
}

