package pl.Korman.Spring.Learning.logic;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.Korman.Spring.Learning.model.project.ProjectRepository;
import pl.Korman.Spring.Learning.model.task.TaskConfigurationProperties;
import pl.Korman.Spring.Learning.model.task.TaskRepository;
import pl.Korman.Spring.Learning.model.taskgroup.TaskGroupRepository;

@Configuration
class LogicConfigurator {

    @Bean
    ProjectService projectService(
            final ProjectRepository repository,
            final TaskGroupRepository taskGroupRepository,
            final TaskConfigurationProperties config)
    {
        return new ProjectService(repository,taskGroupRepository,config);
    }

    @Bean
    TaskGroupService taskGroupService(
            final TaskGroupRepository taskGroupRepository,
            final TaskRepository taskRepository
    )
    {
        return new TaskGroupService(taskGroupRepository,taskRepository);
    }
}
