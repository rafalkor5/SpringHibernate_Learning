package pl.Korman.Spring.Learning.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

//To musi być aby działało taskrepository
@Repository
interface SqlTaskRepository extends TaskRepository, JpaRepository<Task,Integer> {
    @Query(nativeQuery = true, value="select  count(*) > 0 from tasks where id=:id")
    @Override
    boolean existsById(@Param("id") Integer id);
}
