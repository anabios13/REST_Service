package by.anabios13.servlets;

import by.anabios13.db.DataSource;
import by.anabios13.dto.TaskDTO;
import by.anabios13.mappers.TaskMapper;
import by.anabios13.models.Task;
import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


@WebServlet("/tasks")
public class TaskServlet extends HttpServlet {
    public TaskServlet(){}

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Task");
             ResultSet resultSet = preparedStatement.executeQuery()) {

            List<TaskDTO> taskDTOList = new ArrayList<>();
            List<Task> taskList = new ArrayList<>();

            while (resultSet.next()) {
                Task task = new Task();
                task.setTaskId(resultSet.getInt("task_id"));
                task.setTaskName(resultSet.getString("task_name"));
      //          task.setProjectId(resultSet.getInt("project_id"));
                taskList.add(task);
//                TaskDTO taskDTO = TaskMapper.INSTANCE.taskToTaskDTO(task);
//                taskDTOList.add(taskDTO);
            }
            String jsonString = new Gson().toJson(taskList);
            // Отправка списка DTO клиенту, например, в формате JSON
            response.setContentType("application/json");
            response.getWriter().write(jsonString);

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error fetching tasks");
        }
    }

    // Реализация других методов (create, update, delete) аналогичны с использованием PreparedStatement.
}

