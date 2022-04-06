package pl.Korman.Spring.Learning.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

//Tu umieszczamy nasze dostępne metody do repository
public interface TaskRepository {
    List<Task> findAll();
    Page<Task> findAll(Pageable page);
    Optional<Task> findById(Integer id);
    boolean existsById(Integer id);

    List<Task> findByDone(@Param("state") boolean done);

    Task save(Task entity);

    void deleteById(int id);


}
