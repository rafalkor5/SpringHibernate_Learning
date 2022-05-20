package pl.Korman.Spring.Learning.model.projectionmodel;

import lombok.Getter;
import lombok.Setter;
import pl.Korman.Spring.Learning.model.task.Task;
import pl.Korman.Spring.Learning.model.taskgroup.TaskGroup;

import java.time.LocalDateTime;

@Getter
@Setter
public class GroupTaskWriteModel {
    private String description;
    private LocalDateTime deadline;

    public Task toTask(final TaskGroup group){
        return new Task(description,deadline,group);
    }
}
