package pl.Korman.Spring.Learning.model.projectionmodel;


import lombok.Getter;
import lombok.Setter;
import pl.Korman.Spring.Learning.model.project.Project;
import pl.Korman.Spring.Learning.model.project.projectsteps.Projectstep;
import pl.Korman.Spring.Learning.model.taskgroup.TaskGroup;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.List;


@Getter
@Setter
public class ProjectWriteModel {
    @NotBlank(message = "Field description must not be empty")
    private String description;
    @Valid
    private List<Projectstep> projectsteps; // Unikalne projekty

    public Project toProject(){
        var result = new Project();
        result.setDescription(description);
        projectsteps.forEach(step -> step.setProject(result));
        result.setProjectsteps(new HashSet<>(projectsteps));
        return result;
    }

}
