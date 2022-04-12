package pl.Korman.Spring.Learning.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.Korman.Spring.Learning.model.Task;
import pl.Korman.Spring.Learning.model.TaskRepository;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;

//Tutaj implementujemy metody
@RestController
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
        logger.warn("Read All"); // Komunikat do Loggera
        return ResponseEntity.ok(repository.findAll()); //zwrócenie dla użytkownika
    }
    //Nadpisane domyślnego zapytania get.
    @GetMapping ("/tasks")
    ResponseEntity<?> readAllTasks(Pageable page){
        logger.warn("Custom Page");// Komunikat do Loggera
        return ResponseEntity.ok(repository.findAll(page).getContent());//zwrócenie dla użytkownika
    }

    //Pobieranie po id
    @GetMapping("/tasks/{id}")
    ResponseEntity<?> readTaskById(@PathVariable int id){
        logger.warn("Read Task By ID");// Komunikat do Loggera
        return repository.findById(id)
                .map(task -> ResponseEntity.ok(task))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/tasks/{id}")
    ResponseEntity<?> updateTask(@PathVariable int id, @RequestBody @Valid Task update){
        if(!repository.existsById(id)){ // jeżeli nie ma obiektu o takim id
            return ResponseEntity.notFound().build(); //zwróć error
        }
        logger.warn("Update Task");// Komunikat do Loggera

        update.setID(id);//ustawienie wygenerowanego na podane z adresu URL
        repository.save(update);
        return ResponseEntity.noContent().build(); //zwrócenie dla użytkownika
    }

    @PostMapping("/tasks")
    ResponseEntity<?> createTask(@RequestBody @Valid Task add){
        Task result = repository.save(add);
        logger.warn("Insert Task");// Komunikat do Loggera
        return ResponseEntity.created(URI.create("/" + result.getID())).body(result);
    }

    @DeleteMapping("/tasks/{id}")
    ResponseEntity<?> deleteTask(@PathVariable int id){
        if(!repository.existsById(id)){ // jeżeli nie ma obiektu o takim id
            return ResponseEntity.notFound().build(); //zwróć error
        }
        repository.deleteById(id);
        logger.warn("Delete Task");// Komunikat do Loggera
        return ResponseEntity.ok().build();
    }

    @Transactional //Transaction.begin // transaction.commit // metoda musi być publiczna
    @PatchMapping("/tasks/{id}")
    public ResponseEntity<?> toggleTask(@PathVariable int id){
        //@Transactional begin
        if(!repository.existsById(id)){ // jeżeli nie ma obiektu o takim id
            return ResponseEntity.notFound().build(); //zwróć error
        }
        repository.findById(id)
                .ifPresent(task -> task.setDone(!task.isDone()));   // !task.isDone() zmiana na przeciwieństwo
        logger.warn("Toggle done - Task " + id);// Komunikat do Loggera
        //@Transactional commit
        return ResponseEntity.noContent().build(); //zwrócenie dla użytkownika
    }





}
