package pl.Korman.Spring.Learning.model.project;

import pl.Korman.Spring.Learning.model.taskgroup.TaskGroup;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository {
    List<Project> findAll();

    Optional<Project> findById(Integer id);

    Project save(Project entity);
}
