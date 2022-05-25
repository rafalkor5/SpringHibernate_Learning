package pl.Korman.Spring.Learning.logic;

import lombok.AllArgsConstructor;

import pl.Korman.Spring.Learning.model.project.Project;
import pl.Korman.Spring.Learning.model.project.ProjectRepository;
import pl.Korman.Spring.Learning.model.projectionmodel.GroupReadModel;
import pl.Korman.Spring.Learning.model.projectionmodel.TaskWriteModel;
import pl.Korman.Spring.Learning.model.projectionmodel.GroupWriteModel;
import pl.Korman.Spring.Learning.model.task.TaskConfigurationProperties;
import pl.Korman.Spring.Learning.model.taskgroup.TaskGroupRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
//@Service Konfiguracja zrobiona ręcznie w LogicConfigurator
class ProjectService {
    private ProjectRepository repository;
    private TaskGroupRepository taskGroupRepository;
    private TaskConfigurationProperties config;
    private TaskGroupService taskGroupService;


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
        return repository.findById(projectId)
                .map(project -> {
                    var targetGroup = new GroupWriteModel();
                    targetGroup.setDescription(project.getDescription());
                    targetGroup.setTasks(
                            project.getProjectsteps().stream()
                                    .map(projectStep -> {
                                                 var task = new TaskWriteModel();
                                                 task.setDescription(projectStep.getDescription());
                                                 task.setDeadline(deadline.plusDays(projectStep.getDays_to_deadline()));
                                                 return task;
                                            }
                                    ).collect(Collectors.toSet()));
                    return taskGroupService.createGroup(targetGroup);
                }).orElseThrow(() -> new IllegalArgumentException("Project with given id not found"));
    }



}
