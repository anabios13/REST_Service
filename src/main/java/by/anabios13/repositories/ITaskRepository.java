package by.anabios13.repositories;

import by.anabios13.models.Task;

import java.util.List;

public interface ITaskRepository {

    List<Task> getAllTasks();

    List<Task> getAllTasksByProject(int projectId);

    Task getTaskById(int taskId);

    void addTask(Task task);

    void updateTask(Task task);

    void deleteTask(int taskId);
}