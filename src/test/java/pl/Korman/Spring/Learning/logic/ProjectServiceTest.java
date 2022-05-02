package pl.Korman.Spring.Learning.logic;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.Korman.Spring.Learning.model.project.Project;
import pl.Korman.Spring.Learning.model.project.ProjectRepository;
import pl.Korman.Spring.Learning.model.project.projectsteps.Projectstep;
import pl.Korman.Spring.Learning.model.projectionmodel.GroupReadModel;
import pl.Korman.Spring.Learning.model.task.TaskConfigurationProperties;
import pl.Korman.Spring.Learning.model.taskgroup.TaskGroup;
import pl.Korman.Spring.Learning.model.taskgroup.TaskGroupRepository;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

    @Mock
    private TaskGroupRepository mockTaskGroupRepository;
    @Mock
    private TaskConfigurationProperties.Template mockconfigTemplate;
    @Mock
    private TaskConfigurationProperties mockconfig;
    @Mock
    private ProjectRepository mockProjectRepository;
    @Mock
    private Project mockProject;
    @Mock
    private Projectstep mockProjectStep;

    @Test
    void createGroup_testIf_NoMultipleTasks_TasksIsDone_schould_Throws_IllegalStateException() {
        //given
        configureConfigurationStatus(false);
        configureTaskGroupRepositoryDoneStatus(true);
        var ProjectServiceTest = new ProjectService(null,mockTaskGroupRepository,mockconfig);
        //when
        var exception = catchThrowable(()-> ProjectServiceTest.createGroup(LocalDateTime.now(), 0));
        //then
        assertThat(exception)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("one undone group");
    }

    @Test
    void createGroup_configOk_and_noProject_exists_schould_Throws_IllegalArgumentException() {
        //given
        configureConfigurationStatus(true); // goes through if
        given(mockProjectRepository.findById(anyInt())).willReturn(Optional.empty()); // will return allways empty
        var ProjectServiceTest = new ProjectService(mockProjectRepository,mockTaskGroupRepository,mockconfig); //create project service
        //when
        var exception = catchThrowable(()-> ProjectServiceTest.createGroup(LocalDateTime.now(), 0));
        //then
        assertThat(exception)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("No project");
    }

    @Test
    void createGroup_configNoOk_and_noProjectExists_and_TasksIsUndone_schould_Throws_IllegalArgumentException() {
        //given
        configureConfigurationStatus(false);
        configureTaskGroupRepositoryDoneStatus(false);// goes through if
        given(mockProjectRepository.findById(anyInt())).willReturn(Optional.empty()); // will return allways empty
        var ProjectServiceTest = new ProjectService(mockProjectRepository,mockTaskGroupRepository,mockconfig); //create project service
        //when
        var exception = catchThrowable(()-> ProjectServiceTest.createGroup(LocalDateTime.now(), 0));
        //then
        assertThat(exception)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("No project");
    }

//    @Test
//    void createGroup_configOK_ProjectExists_TasksIsUndone_schould_saveGroup(){
//        //given
//        var today = LocalDate.now().atStartOfDay();
//        configureConfigurationStatus(true);
//        configureTaskGroupRepositoryDoneStatus(false);
//
//        getProject("bar",Set.of(-1,-2));
//        given(mockProjectRepository.findById(1)).willReturn(Optional.of(mockProject));
//
//        InMemoryGroupRepository inMemoryGroupRepo = getinMemoryGroupRepository();
//        int countBeforeCall = inMemoryGroupRepo.count();
//        var ProjectServiceTest = new ProjectService(mockProjectRepository,inMemoryGroupRepo,mockconfig);
//        //when
//        //do naprawy ProjectRepository nie ma projektu o takim numerze
//
//        GroupReadModel result = ProjectServiceTest.createGroup(today,1);
//        //then
//        assertThat(result.getDescription()).isEqualTo("bar");
//        //assertThat(countBeforeCall + 1).isNotEqualTo(inMemoryGroupRepo.count());
//
//    }

    private void getProject(String description, Set<Integer> daysToDeadline){
        Set<Projectstep> steps = daysToDeadline.stream()
                .map(days -> {
                    configureProjectStep("xd",days);
                    return mockProjectStep;
                }).collect(Collectors.toSet());
        given(mockProject.getProjectsteps()).willReturn(steps);
        given(mockProject.getDescription()).willReturn(description);
    }

    private void configureProjectStep(String description, int daysToDeadline){
        given(mockProjectStep.getDays_to_deadline()).willReturn(daysToDeadline);
        given(mockProjectStep.getDescription()).willReturn(description);

    }

    private void configureConfigurationStatus(boolean status) {
        given(mockconfigTemplate.isAllowMultipleTasks()).willReturn(status);
        given(mockconfig.getTemplate()).willReturn(mockconfigTemplate);
    }
    private void configureTaskGroupRepositoryDoneStatus(boolean status){
        given(mockTaskGroupRepository.existsByDoneIsFalseAndProject_Id(anyInt())).willReturn(status);
    }

    private InMemoryGroupRepository getinMemoryGroupRepository(){
        return new InMemoryGroupRepository();
    }

    private static class InMemoryGroupRepository implements TaskGroupRepository{
        private int index = 0;
        private Map<Integer,TaskGroup> map = new HashMap<>();

        public int count(){return map.values().size();}

        @Override
        public List<TaskGroup> findAll() {
            return new ArrayList<>(map.values());
        }

        @Override
        public Optional<TaskGroup> findById(final Integer id) {
            return Optional.ofNullable(map.get(id));
        }

        @Override
        public TaskGroup save(final TaskGroup entity) {
            if(entity.getID() == 0) {
                try {
                    var field = TaskGroup.class.getDeclaredField("ID");
                    field.setAccessible(true);
                    field.set(entity, ++index); // dostęp do pół klasy nielegalny
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
            map.put(entity.getID(),entity);
            return null;
        }

        @Override
        public boolean existsByDoneIsFalseAndProject_Id(final Integer groupid) {
            return map.values().stream()
                    .filter(x -> !x.isDone())
                    .anyMatch(x -> x.getProject() != null && x.getProject().getId() == groupid);
        }
    }
}