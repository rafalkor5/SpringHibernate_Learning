package pl.Korman.Spring.Learning.adapter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.Korman.Spring.Learning.model.project.Project;
import pl.Korman.Spring.Learning.model.project.ProjectRepository;

@Repository
public interface SqlProjectRepository extends ProjectRepository, JpaRepository<Project, Integer> {

}
