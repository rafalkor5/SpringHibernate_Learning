package pl.Korman.Spring.Learning.model.projectionmodel;

import lombok.Getter;
import lombok.Setter;
import pl.Korman.Spring.Learning.model.task.Task;

@Getter
@Setter
public class TaskReadModel {
    private String description;
    private boolean done;

    public TaskReadModel(Task source){
        description = source.getDescription();
        done = source.isDone();
    }

}
