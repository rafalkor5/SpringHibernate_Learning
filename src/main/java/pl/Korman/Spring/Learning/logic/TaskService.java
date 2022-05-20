package pl.Korman.Spring.Learning.logic;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import pl.Korman.Spring.Learning.model.task.Task;
import pl.Korman.Spring.Learning.model.task.TaskRepository;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@AllArgsConstructor
public class TaskService {
    private final TaskRepository repository;
    private static final Logger logger = LoggerFactory.getLogger(TaskService.class);

    @Async
    public CompletableFuture<List<Task>> findAllAsync(){
        logger.info("Supply async");
        return CompletableFuture.supplyAsync(repository::findAll);
    }
}
