package by.anabios13.repositories;

import by.anabios13.models.Project;

import java.sql.SQLException;
import java.util.List;

public interface IProjectRepository {

    List<Project> getAllProjects() throws SQLException;

    Project getProjectById(int projectId);

    Project save(Project project);

    void updateProject(Project project);

    void deleteProject(int projectId);
}


