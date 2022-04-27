package pl.Korman.Spring.Learning.logic;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.Korman.Spring.Learning.model.projectionmodel.GroupReadModel;
import pl.Korman.Spring.Learning.model.projectionmodel.GroupWriteModel;
import pl.Korman.Spring.Learning.model.task.TaskRepository;
import pl.Korman.Spring.Learning.model.taskgroup.TaskGroup;
import pl.Korman.Spring.Learning.model.taskgroup.TaskGroupRepository;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
class TaskGroupService {
    private TaskGroupRepository repository;
    private TaskRepository taskRepository;


    public GroupReadModel createGroup(GroupWriteModel source){
        TaskGroup result = repository.save(source.toGroup());
        return new GroupReadModel(result);
    }

    public List<GroupReadModel> readAll(){
    return repository.findAll().stream()
            .map(GroupReadModel::new)
            .collect(Collectors.toList());
    }

    public void togglGroup(int groupID){
        if(taskRepository.existsByDoneIsFalseAndTaskGroup_ID(groupID)){
            throw new IllegalStateException("Group has undone tasks");
        }
        TaskGroup result = repository.findById(groupID)
                .orElseThrow(()-> new IllegalArgumentException("TaskGroup with given id not found"));
        result.setDone(!result.isDone());
    }


}
