package pl.Korman.Spring.Learning.model.projectionmodel;

import lombok.Getter;
import lombok.Setter;
import pl.Korman.Spring.Learning.model.task.Task;
import pl.Korman.Spring.Learning.model.taskgroup.TaskGroup;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;


@Getter
@Setter
public class GroupReadModel {
    private String description;
    private int Id;
    private boolean Done;
    /**
     * Deadline from the latest task in group.
     */
    private LocalDateTime deadline;
    private Set<TaskReadModel> tasks;

    public GroupReadModel(TaskGroup source){
        Id = source.getID();
        description = source.getDescription();
        Done = source.isDone();
        source.getTasks().stream()
                .map(Task::getDeadline)
                .max(LocalDateTime::compareTo)
                .ifPresent(date -> deadline = date);
        tasks = source.getTasks().stream()
                .map(TaskReadModel::new)
                .collect(Collectors.toSet());
    }


}
