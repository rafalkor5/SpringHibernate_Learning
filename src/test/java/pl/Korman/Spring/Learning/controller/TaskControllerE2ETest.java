package pl.Korman.Spring.Learning.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import pl.Korman.Spring.Learning.model.task.Task;
import pl.Korman.Spring.Learning.model.task.TaskRepository;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

//@ActiveProfiles("integration") // Change for use the testconfiguration repo

//Creates a web application context (reactive or servlet based) and sets a server.port=0
// Environment property (which usually triggers listening on a random port).
// Often used in conjunction with a @LocalServerPort injected field on the test.
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TaskControllerE2ETest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;


    @Autowired
    TaskRepository repo;

    @Test
    void httpReturnAllTasks(){
        //given
        int initial = repo.findAll().size();
        repo.save(new Task("test", LocalDateTime.now()));
        repo.save(new Task("test2", LocalDateTime.now()));


        //when
        Task[] result = restTemplate.getForObject("http://localhost:" + port + "/tasks", Task[].class);

        //then
        assertThat(result).hasSize(initial +2 );
    }

}