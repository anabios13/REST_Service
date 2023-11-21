package by.anabios13.mappers;

import by.anabios13.dto.TaskDTO;
import by.anabios13.models.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ITaskMapper {

    @Mapping(source = "taskId", target = "taskId")
    @Mapping(source = "taskName", target = "taskName")
    @Mapping(source = "performers", target = "performers")
    TaskDTO taskToTaskDTO(Task task);

    @Mapping(source = "taskId", target = "taskId")
    @Mapping(source = "taskName", target = "taskName")
    @Mapping(source = "performers", target = "performers")
    Task taskDTOToTask(TaskDTO taskDTO);
}