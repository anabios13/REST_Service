package by.anabios13.repositories.impl;

import by.anabios13.db.DataSource;
import by.anabios13.exceptions.CreateException;
import by.anabios13.exceptions.DeleteException;
import by.anabios13.exceptions.ReadException;
import by.anabios13.exceptions.UpdateException;
import by.anabios13.models.Project;
import by.anabios13.models.Task;
import by.anabios13.repositories.ITaskRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class TaskRepository implements ITaskRepository {

    public TaskRepository() {
    }

    public Task save(Task task, int project_id) {
        try (Connection connection = DataSource.getConnection()) {
            String sql = "INSERT INTO Task (task_name, project_id) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, task.getTaskName());
                preparedStatement.setInt(2, project_id);
                preparedStatement.executeUpdate();

                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        task.setTaskId(generatedKeys.getInt(1));
                    } else {
                        throw new CreateException("Creating task failed, no ID obtained.");
                    }
                }
            }
        } catch (SQLException e) {
            throw new CreateException(e.getMessage());
        }

        return task;
    }

    public Task findById(int taskId) {
        Task task = null;

        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT * FROM Task WHERE task_id = ?")
        ) {

            preparedStatement.setInt(1, taskId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    task = mapResultSetToTask(resultSet);
                } else return null;
            }

        } catch (SQLException e) {
           throw new ReadException(e.getMessage());
        }
        return task;
    }


    public List<Task> findAll() {
        List<Task> tasks = new ArrayList<>();

        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Task");
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Task task = mapResultSetToTask(resultSet);
                tasks.add(task);
            }

        } catch (SQLException e) {
            throw new ReadException(e.getMessage());
        }

        return tasks;
    }

    public void update(Task task, int project_id) {
        try (Connection connection = DataSource.getConnection()) {
            String sql = "UPDATE Task SET task_name = ?, project_id = ? WHERE task_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, task.getTaskName());
                preparedStatement.setInt(2, project_id);
                preparedStatement.setInt(3, task.getTaskId());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new UpdateException(e.getMessage());
        }
    }

    public void deleteById(int taskId) {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Task WHERE task_id = ?")) {

            preparedStatement.setInt(1, taskId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DeleteException(e.getMessage());
        }
    }


    private Task mapResultSetToTask(ResultSet resultSet) throws SQLException {
        Task task = new Task();
        task.setTaskId(resultSet.getInt("task_id"));
        task.setTaskName(resultSet.getString("task_name"));

        int projectId = resultSet.getInt("project_id");
        Project project = new Project();
        project.setProjectId(projectId);
        return task;
    }
}
