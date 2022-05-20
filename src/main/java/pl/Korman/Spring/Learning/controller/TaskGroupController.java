package pl.Korman.Spring.Learning.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.Korman.Spring.Learning.logic.TaskGroupService;
import pl.Korman.Spring.Learning.model.projectionmodel.GroupReadModel;
import pl.Korman.Spring.Learning.model.projectionmodel.GroupWriteModel;
import pl.Korman.Spring.Learning.model.task.Task;
import pl.Korman.Spring.Learning.model.task.TaskRepository;
import pl.Korman.Spring.Learning.model.taskgroup.TaskGroupRepository;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.concurrent.CompletableFuture;

//Tutaj implementujemy metody
@RequiredArgsConstructor //konstruktor w starszych wersjach wymagany z @Autowired
@RestController
@RequestMapping("/tasks") //dzięki temu w tej klasie nie musimy za każdym razem pisać /tasks w mapingu
class TaskGroupController {
    //Tworzymy logger
    private static final Logger logger = LoggerFactory.getLogger(TaskGroupController.class);
    //pole przechowująco repository
    @NonNull
    private final TaskRepository repository;
    @NonNull
    private final TaskGroupService service;


    //Nadpisane domyślnego zapytania get. bez wyłączania parrams Pagable page oczekuje parametrów
    @GetMapping
    ResponseEntity<List<GroupReadModel>> readAllGroups ( Pageable page){
        logger.warn("Read Groups");// Komunikat do Loggera
        return ResponseEntity.ok(service.readAllGroups());//zwrócenie dla użytkownika

    }

    @GetMapping
    ResponseEntity<List<Task>> readAllTasksFromGroup(@PathVariable int id){
        return ResponseEntity.ok(groupRepository.findById(id).g)
    }

    @PostMapping  // @RequestBody @Valid sprawdza czy przekazany task jest poprawny
    ResponseEntity<GroupReadModel> createGroup(@RequestBody @Valid GroupWriteModel add){
        logger.warn("Insert Group");// Komunikat do Loggera
        return ResponseEntity.created(URI.create("/")).body(service.createGroup(add));
    }


    @Transactional //Transaction.begin // transaction.commit // metoda musi być publiczna
    @PatchMapping("/changeStatus/{id}")
    public ResponseEntity<?> toggleGroup(@PathVariable int id){
        service.togglGroup(id);
        return ResponseEntity.noContent().build(); //zwrócenie dla użytkownika
    }





}
