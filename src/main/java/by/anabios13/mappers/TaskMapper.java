package by.anabios13.mappers;

import by.anabios13.dto.TaskDTO;
import by.anabios13.models.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TaskMapper {

    TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);


    @Mapping(target = "projectId", source = "task.projectId")
    TaskDTO taskToTaskDTO(Task task);
}

