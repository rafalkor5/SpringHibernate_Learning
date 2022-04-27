package pl.Korman.Spring.Learning.model.taskgroup;

import java.util.List;
import java.util.Optional;

//dostępne metody
public interface TaskGroupRepository {
    List<TaskGroup> findAll();

    Optional<TaskGroup> findById(Integer id);

    TaskGroup save(TaskGroup entity);


}
