package by.anabios13.servlets;

import by.anabios13.services.impl.TaskService;
import com.google.gson.Gson;
import by.anabios13.dto.TaskDTO;
import by.anabios13.services.ITaskService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;
import java.util.List;

@WebServlet("/tasks")
public class TaskServlet extends HttpServlet {

    private final ITaskService taskService;
    private final Gson gson = new Gson();

    public TaskServlet() {
        this.taskService = new TaskService();
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // Получение всех задач
        if (req.getParameter("taskId") != null) {
            // Получение задачи по идентификатору
            int taskId = Integer.parseInt(req.getParameter("taskId"));
            TaskDTO task = taskService.getTaskById(taskId);
            String json = gson.toJson(task);
            resp.setContentType("application/json");
            resp.getWriter().write(json);
        } else {
            // Получение всех задач
            List<TaskDTO> tasks = taskService.getAllTasks();
            String json = gson.toJson(tasks);
            resp.setContentType("application/json");
            resp.getWriter().write(json);
        }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // Создание новой задачи
        TaskDTO taskDTO = gson.fromJson(req.getReader(), TaskDTO.class);
        TaskDTO createdTask = taskService.saveTask(taskDTO);
        String json = gson.toJson(createdTask);
        resp.setContentType("application/json");
        resp.getWriter().write(json);
    }

    @Override
    public void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // Обновление существующей задачи
        TaskDTO taskDTO = gson.fromJson(req.getReader(), TaskDTO.class);
        taskService.updateTask(taskDTO);
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    public void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // Удаление задачи по идентификатору
        int taskId = Integer.parseInt(req.getParameter("taskId"));
        taskService.deleteTask(taskId);
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
