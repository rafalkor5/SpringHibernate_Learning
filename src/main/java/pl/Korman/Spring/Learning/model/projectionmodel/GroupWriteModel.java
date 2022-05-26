package pl.Korman.Spring.Learning.model.projectionmodel;

import lombok.Getter;
import lombok.Setter;
import pl.Korman.Spring.Learning.model.project.Project;
import pl.Korman.Spring.Learning.model.taskgroup.TaskGroup;

import java.util.Set;
import java.util.stream.Collectors;


@Getter
@Setter
public class GroupWriteModel {
    private String description;
    private Set<TaskWriteModel> tasks;

    public TaskGroup toGroup(final Project project) {
        TaskGroup result = new TaskGroup();
        result.setDescription(description);
        result.setTasks(
                tasks.stream()
                        .map(source -> source.toTask(result))
                        .collect(Collectors.toSet())
        );
        result.setProject(project);
        return result;
    }

}
