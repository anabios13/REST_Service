package by.anabios13.mappers.impl;

import by.anabios13.dto.TaskDTO;
import by.anabios13.mappers.ITaskMapper;
import by.anabios13.models.Task;

public class TaskMapper implements ITaskMapper {
    @Override
    public TaskDTO taskToTaskDTO(Task task) {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setTaskId(task.getTaskId());
        taskDTO.setTaskName(taskDTO.getTaskName());
        taskDTO.setPerformers(task.getPerformers());
        return  taskDTO;
    }

    @Override
    public Task taskDTOToTask(TaskDTO taskDTO) {
        Task task = new Task();
        task.setTaskId(taskDTO.getTaskId());
        task.setTaskName(taskDTO.getTaskName());
        task.setPerformers(taskDTO.getPerformers());
        return  task;
    }
}
