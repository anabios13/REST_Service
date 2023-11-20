package by.anabios13.repositories.impl;

import by.anabios13.db.DataSource;
import by.anabios13.models.Project;
import by.anabios13.models.Task;
import by.anabios13.repositories.ITaskRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class TaskRepository implements ITaskRepository {


    private static TaskRepository taskRepository;

    private TaskRepository(){}

    public static synchronized TaskRepository gatTaskRepository() {
        if (taskRepository == null) {
            taskRepository = new TaskRepository();
        }
        return taskRepository;
    }

    public Task save(Task task,int project_id) {
        try (Connection connection = DataSource.getConnection()) {
            String sql = "INSERT INTO Task (task_name, project_id) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, task.getTaskName());
                preparedStatement.setInt(2,project_id);
                preparedStatement.executeUpdate();

                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        task.setTaskId(generatedKeys.getInt(1));
                    } else {
                        throw new SQLException("Creating task failed, no ID obtained.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return task;
    }

    public Task findById(int taskId) {
        Task task = null;

        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Task WHERE task_id = ?")) {

            preparedStatement.setInt(1, taskId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    task = mapResultSetToTask(resultSet);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Обработка ошибок
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
            e.printStackTrace();
            // Обработка ошибок
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
            e.printStackTrace();
            // Обработка ошибок
        }
    }

    public void deleteById(int taskId) {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Task WHERE task_id = ?")) {

            preparedStatement.setInt(1, taskId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            // Обработка ошибок
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
