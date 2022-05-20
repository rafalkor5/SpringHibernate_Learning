package pl.Korman.Spring.Learning.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.Korman.Spring.Learning.logic.TaskService;
import pl.Korman.Spring.Learning.model.task.Task;
import pl.Korman.Spring.Learning.model.task.TaskRepository;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.concurrent.CompletableFuture;

//Tutaj implementujemy metody
@RequiredArgsConstructor //konstruktor w starszych wersjach wymagany z @Autowired
@RestController
@RequestMapping("/tasks") //dzięki temu w tej klasie nie musimy za każdym razem pisać /tasks w mapingu
class TaskController {
    //Tworzymy logger
    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);
    //pole przechowująco repository
    @NonNull
    private final TaskRepository repository;
    @NonNull
    private final TaskService service;


    //Nadpisane domyślnego zapytania get. Z wyłączeniem paraams
    @GetMapping (params = {"!sort","!page","!size"})
    CompletableFuture<ResponseEntity<List<Task>>> readAllTasks(){
        logger.warn("Read All"); // Komunikat do Loggera
        return service.findAllAsync().thenApply(ResponseEntity::ok);
    }
    //Nadpisane domyślnego zapytania get. bez wyłączania parrams Pagable page oczekuje parametrów
    @GetMapping
    ResponseEntity<List<Task>> readAllTasks(Pageable page){
        logger.warn("Custom Page");// Komunikat do Loggera
        return ResponseEntity.ok(repository.findAll(page).getContent());//zwrócenie dla użytkownika
    }

    //Pobieranie po id
    @GetMapping("/{id}") // Dzięki temu przekazant parametr jest wymagany
    ResponseEntity<Task> readTaskById(@PathVariable int id){
        logger.warn("Read Task By ID");// Komunikat do Loggera
        return repository.findById(id)
                .map(task -> ResponseEntity.ok(task))
                .orElse(ResponseEntity.notFound().build());
    }

    //Jeżeli tu nie ma wymaganego parametru to nie jest konieczny
    @GetMapping("/serach/done") //@RequestParam(required = false) Pobiera przekazany parametr o ile jest przekazany
    ResponseEntity<List<Task>> readDoneTask(@RequestParam(defaultValue = "true") boolean state){
        logger.warn("Read Task By Done");// Komunikat do Loggera
        return ResponseEntity.ok(repository.findByDone(state));
    }

    @Transactional //Transaction.begin // transaction.commit // metoda musi być publiczna
    @PutMapping("/{id}")
    public ResponseEntity<?> updateTask(@PathVariable int id, @RequestBody @Valid Task create){
        if(!repository.existsById(id)){ // jeżeli nie ma obiektu o takim id
            return ResponseEntity.notFound().build(); //zwróć error
        }
        logger.warn("Update Task");// Komunikat do Loggera
        repository.findById(id)
                .ifPresent(task -> task.updateFrom(create));
        return ResponseEntity.noContent().build(); //zwrócenie dla użytkownika
    }

    @PostMapping  // @RequestBody @Valid sprawdza czy przekazany task jest poprawny
    ResponseEntity<?> createTask(@RequestBody @Valid Task add){
        Task result = repository.save(add);
        logger.warn("Insert Task");// Komunikat do Loggera
        return ResponseEntity.created(URI.create("/" + result.getID())).body(result);
    }

    @DeleteMapping("/{id}")  //@PathVariable coś zdefiniowane w adresie {id}
    ResponseEntity<?> deleteTask(@PathVariable int id){
        if(!repository.existsById(id)){ // jeżeli nie ma obiektu o takim id
            return ResponseEntity.notFound().build(); //zwróć error
        }
        repository.deleteById(id);
        logger.warn("Delete Task");// Komunikat do Loggera
        return ResponseEntity.ok().build();
    }

    @Transactional //Transaction.begin // transaction.commit // metoda musi być publiczna
    @PatchMapping("/changeStatus/{id}")
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
