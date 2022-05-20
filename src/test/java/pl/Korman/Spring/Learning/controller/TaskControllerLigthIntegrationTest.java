package pl.Korman.Spring.Learning.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.Korman.Spring.Learning.model.task.Task;
import pl.Korman.Spring.Learning.model.task.TaskRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;



@WebMvcTest(TaskController.class)
class TaskControllerLigthIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean // Zamockowany cały bean repo
    private TaskRepository repo;

    @Test
    void httpGet_returnsGivenTask() throws Exception {
        //given
        String description = "test";
        given(repo.findById(anyInt())).willReturn(Optional.of(new Task("test",LocalDateTime.now())));

        //when + then
        mockMvc.perform(MockMvcRequestBuilders.get("/tasks/123")) // możemy zawołać cokolwiek (anyint
                .andDo(print())
                .andExpect(MockMvcResultMatchers.content().string(containsString(description)));
        //.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

}
