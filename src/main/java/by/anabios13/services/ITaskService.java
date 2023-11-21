package by.anabios13.services;



import by.anabios13.dto.TaskDTO;

import java.util.List;

public interface ITaskService {
    TaskDTO saveTask(TaskDTO taskDTO);

    TaskDTO getTaskById(int taskId);

    List<TaskDTO> getAllTasks();

    void updateTask(TaskDTO taskDTO);

    void deleteTask(int taskId);
}

