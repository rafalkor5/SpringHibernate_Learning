package pl.Korman.Spring.Learning.adapter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.Korman.Spring.Learning.model.project.Project;
import pl.Korman.Spring.Learning.model.project.ProjectRepository;
import pl.Korman.Spring.Learning.model.taskgroup.TaskGroup;

import java.util.List;

@Repository
public interface SqlProjectRepository extends ProjectRepository, JpaRepository<Project, Integer> {

    @Override
    @Query("select distinct p from Project p join fetch p.projectsteps ")
    List<Project> findAll();

}
