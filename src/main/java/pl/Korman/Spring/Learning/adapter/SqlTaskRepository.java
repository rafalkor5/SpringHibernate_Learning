package pl.Korman.Spring.Learning.adapter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.Korman.Spring.Learning.model.task.Task;
import pl.Korman.Spring.Learning.model.task.TaskRepository;

//To musi być aby działało taskrepository ( Impelentacja metod z taskrepository)
@Repository
interface SqlTaskRepository extends TaskRepository, JpaRepository<Task,Integer> {

    @Query(nativeQuery = true, value="select  count(*) > 0 from tasks where id=:id")
    @Override
    boolean existsById(@Param("id") Integer id);

    @Override
    boolean existsByDoneIsFalseAndTaskGroup_ID(Integer groupid);

}
