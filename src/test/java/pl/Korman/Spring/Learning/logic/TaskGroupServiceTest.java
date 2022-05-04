package pl.Korman.Spring.Learning.logic;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.Korman.Spring.Learning.model.task.TaskRepository;
import pl.Korman.Spring.Learning.model.taskgroup.TaskGroup;
import pl.Korman.Spring.Learning.model.taskgroup.TaskGroupRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class TaskGroupServiceTest {
    @Mock
    private TaskGroupRepository mockTaskGroupRepository;
    @Mock
    private TaskRepository mockTaskRepository;


    @Test
    void togglGroup_If_existsByDoneIsFalse_schould_throw_IllegalStateException() {
        //given
        given(mockTaskRepository.existsByDoneIsFalseAndTaskGroup_ID(anyInt())).willReturn(true);
        var taskGroupService = new TaskGroupService(mockTaskGroupRepository,mockTaskRepository);
        //when
        var exception = catchThrowable(()-> taskGroupService.togglGroup(5));
        //then
        assertThat(exception)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Group has");
    }

    /**
     * > Given a task group with id 5, when I toggle the group, then I should get an IllegalArgumentException with a
     * message containing "TaskGroup with"
     */
    @Test
    void togglGroup_idgroup_not_found_schould_throw_IllegalArgumentException() {
        //given
        given(mockTaskRepository.existsByDoneIsFalseAndTaskGroup_ID(anyInt())).willReturn(false);
        given(mockTaskGroupRepository.findById(anyInt())).willReturn(Optional.empty());
        var taskGroupService = new TaskGroupService(mockTaskGroupRepository,mockTaskRepository);
        //when
        var exception = catchThrowable(()-> taskGroupService.togglGroup(5));

        //then
        assertThat(exception)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("TaskGroup with");
    }
    @Test
    void toggleGroup_worksAsExpected(){
        //given
        var taskgroup = new TaskGroup();
        var beforetoggle = taskgroup.isDone();
        given(mockTaskRepository.existsByDoneIsFalseAndTaskGroup_ID(anyInt())).willReturn(false);
        given(mockTaskGroupRepository.findById(anyInt())).willReturn(Optional.of(taskgroup));
        var taskGroupService = new TaskGroupService(mockTaskGroupRepository,mockTaskRepository);
        //when
        taskGroupService.togglGroup(1);
        //then
        assertThat(taskgroup.isDone()).isEqualTo(!beforetoggle);
    }




}