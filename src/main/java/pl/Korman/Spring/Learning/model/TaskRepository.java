package pl.Korman.Spring.Learning.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

//@RepositoryRestResource(path = "siema", collectionResourceRel = "siema")
@RepositoryRestResource
public interface TaskRepository extends JpaRepository<Task,Integer> {
    @RestResource(path ="done", rel = "done")
    List<Task> findByDone(@Param("state") boolean done);
}
