package by.anabios13.repositories;

import by.anabios13.models.Task;

import java.util.List;

public interface ITaskRepository {

    Task save(Task task,int project_id);
    Task findById(int taskId);
    List<Task> findAll();
    void update(Task task, int project_id);
    void deleteById(int taskId);
}