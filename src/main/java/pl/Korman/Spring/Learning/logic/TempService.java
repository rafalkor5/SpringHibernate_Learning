package pl.Korman.Spring.Learning.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.Korman.Spring.Learning.model.task.Task;
import pl.Korman.Spring.Learning.model.taskgroup.TaskGroupRepository;

import java.util.stream.Collectors;

@Service
class TempService {

    @Autowired
    void temp(TaskGroupRepository repository){
        //fixme n+1
        repository.findAll().stream()
                .flatMap(taskGroup -> taskGroup.getTasks().stream())
                .map(Task::getDescription)
                .collect(Collectors.toList());
    }
}
