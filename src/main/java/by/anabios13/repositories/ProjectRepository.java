package by.anabios13.repositories;

import by.anabios13.dto.ProjectDTO;
import by.anabios13.models.Project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProjectRepository {

    private Connection connection;

    public ProjectRepository(Connection connection) {
        this.connection = connection;
    }

    public List<Project> getAllProjects() throws SQLException {
        List<Project> projects = new ArrayList<>();
        String query = "SELECT * FROM Project";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Project project = new Project();
                project.setProjectId(resultSet.getInt("project_id"));
                project.setProjectName(resultSet.getString("project_name"));
                projects.add(project);
            }
        }
        return projects;
    }

    // Добавьте методы для создания, обновления и удаления проектов по аналогии с getAllProjects
}

