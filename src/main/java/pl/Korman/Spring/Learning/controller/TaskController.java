package pl.Korman.Spring.Learning.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import pl.Korman.Spring.Learning.model.TaskRepository;

@RepositoryRestController
class TaskController {
    //Tworzymy logger
    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);
    //pole przechowująco repository
    private final TaskRepository repository;

    @Autowired // Autowired jest opcjonalnie nie trzeba pisać teraz bez działa
    TaskController(final TaskRepository repository) {
        this.repository = repository;
    }

    //Nadpisane domyślnego zapytania get. Z wyłączeniem paraams
    @GetMapping (value = "/tasks", params = {"!sort","!page","!size"})
    ResponseEntity<?> readAllTasks(){
        logger.warn("Nadpisanie get"); // Komunikat do Loggera
        return ResponseEntity.ok(repository.findAll());
    }
    //Nadpisane domyślnego zapytania get.
    @GetMapping ("/tasks")
    ResponseEntity<?> readAllTasks(Pageable page){
        logger.warn("Nadpisanie get");// Komunikat do Loggera
        return ResponseEntity.ok(repository.findAll(page).getContent());
    }
}
