package exercise.mapper;

import exercise.dto.TaskCreateDTO;
import exercise.dto.TaskDTO;
import exercise.dto.TaskUpdateDTO;
import exercise.model.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class TaskMapper {

    @Mapping(target = "assignee.id", source = "assigneeId")
    public abstract Task map(TaskCreateDTO dto);
    @Mapping(target = "assigneeId", source = "assignee.id")
    public abstract TaskDTO map(Task model);
    @Mapping(target = "assignee.id", source = "assigneeId")
    public abstract Task map(TaskDTO dto);

    public abstract void update(TaskUpdateDTO dto, @MappingTarget Task model);
    public abstract void update(TaskUpdateDTO dto, @MappingTarget TaskDTO taskDTO);
}
