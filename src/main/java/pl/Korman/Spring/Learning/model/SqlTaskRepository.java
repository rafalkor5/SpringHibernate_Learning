package pl.Korman.Spring.Learning.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//To musi być aby działało taskrepository
@Repository
interface SqlTaskRepository extends TaskRepository, JpaRepository<Task,Integer> {

}
