package pl.Korman.Spring.Learning.logic;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.Korman.Spring.Learning.model.project.Project;
import pl.Korman.Spring.Learning.model.project.ProjectRepository;
import pl.Korman.Spring.Learning.model.projectionmodel.GroupReadModel;
import pl.Korman.Spring.Learning.model.task.Task;
import pl.Korman.Spring.Learning.model.task.TaskConfigurationProperties;
import pl.Korman.Spring.Learning.model.taskgroup.TaskGroup;
import pl.Korman.Spring.Learning.model.taskgroup.TaskGroupRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
class ProjectService {
    private ProjectRepository repository;
    private TaskGroupRepository taskGroupRepository;
    private TaskConfigurationProperties config;

    //korzysta z crudreposql
    public List<Project> readAll() {
        return repository.findAll();
    }

    //korzysta z crudreposql
    public Project save(final Project tosave){
        return repository.save(tosave);
    }

    public GroupReadModel createGroup(LocalDateTime deadline, int projectId) {
        if (!config.getTemplate().isAllowMultipleTasks() && taskGroupRepository.existsByDoneIsFalseAndProject_Id(projectId)) {
            throw new IllegalStateException("Only one undone group from project is allowed");
        }
        TaskGroup result = repository.findById(projectId)
                .map(project -> {
                    var targetGroup = new TaskGroup();
                    targetGroup.setDescription(project.getDescription());
                    targetGroup.setTasks(
                            project.getProjectsteps().stream()
                                    .map(projectStep -> new Task(
                                            projectStep.getDescription(),
                                            deadline.plusDays(projectStep.getDays_to_deadline()))
                                    ).collect(Collectors.toSet())
                    );
                    targetGroup.setProject(project);
                    return taskGroupRepository.save(targetGroup);
                }).orElseThrow(() -> new IllegalArgumentException("Project with given id not found"));
        return new GroupReadModel(result);
    }

    //????????????????????????????????????????????
//    public GroupReadModel createGroup(LocalDateTime deadline, int projectId){
//        if(!config.getTemplate().isAllowMultipleTasks() && taskGroupRepository.existsByDoneIsFalseAndProject_Id(projectId)){
//            throw new IllegalStateException("Only one undone group from project is allowed");
//        }
//        TaskGroup targetresult = repository.findById(projectId)
//                .map(buildproject -> {
//                    var result = new TaskGroup();
//                    result.setDescription(buildproject.getDescription());
//                    result.setTasks(
//                            buildproject.getProjectsteps().stream()
//                                    .map(step -> new Task(
//                                            step.getDescription(),
//                                            deadline.plusDays(step.getDays_to_deadline())))
//                                    .collect(Collectors.toSet()));
//                    result.setProject(buildproject);
//                    return taskGroupRepository.save(result);
//                }).orElseThrow(()-> new IllegalArgumentException("No project with this id"));
//    return new GroupReadModel(targetresult);
//    }

}
