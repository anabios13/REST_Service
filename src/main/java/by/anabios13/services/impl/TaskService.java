package by.anabios13.services.impl;

import by.anabios13.dto.TaskDTO;
import by.anabios13.exceptions.CRUDException;
import by.anabios13.mappers.ITaskMapper;
import by.anabios13.models.Task;
import by.anabios13.repositories.impl.TaskRepository;
import by.anabios13.services.ITaskService;


import java.util.List;
import java.util.stream.Collectors;

public class TaskService implements ITaskService {
    private final TaskRepository taskRepository;
    private final ITaskMapper taskMapper;

    public TaskService(TaskRepository taskRepository, ITaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }


    public TaskDTO saveTask(TaskDTO taskDTO) {
        Task task = taskMapper.taskDTOToTask(taskDTO);
        Task savedTask = taskRepository.save(task,taskDTO.getProject().getProjectId() );
        return taskMapper.taskToTaskDTO(savedTask);
    }

    public TaskDTO getTaskById(int taskId) {
        Task task = taskRepository.findById(taskId);
        return taskMapper.taskToTaskDTO(task);
    }

    public List<TaskDTO> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        return tasks.stream()
                .map(taskMapper::taskToTaskDTO)
                .collect(Collectors.toList());
    }


    public void updateTask(TaskDTO taskDTO) {
        Task existingTask = taskRepository.findById(taskDTO.getTaskId());

        if (existingTask != null) {
            // Маппим поля, которые нужно обновить
            existingTask.setTaskName(taskDTO.getTaskName());

            // Обновляем сущность в репозитории
            taskRepository.update(existingTask,taskDTO.getProject().getProjectId());
        } else {
            // Обработка случая, если задача не найдена
            throw new CRUDException("Task not found with ID: " + taskDTO.getTaskId());
        }
    }


    public void deleteTask(int taskId) {
        Task existingTask = taskRepository.findById(taskId);

        if (existingTask != null) {
            // Удаляем сущность из репозитория
            taskRepository.deleteById(taskId);
        }
    }

}
