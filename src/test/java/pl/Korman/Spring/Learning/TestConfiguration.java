package pl.Korman.Spring.Learning;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import pl.Korman.Spring.Learning.model.task.Task;
import pl.Korman.Spring.Learning.model.task.TaskRepository;
import pl.Korman.Spring.Learning.model.taskgroup.TaskGroup;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Configuration
class TestConfiguration {

    @Bean
    @Primary
    @Profile("!integration")
    DataSource testDataE2ESource(){
        var result = new DriverManagerDataSource("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1","","");
        result.setDriverClassName("org.h2.Driver");
        return result;
    }


    @Bean // Baza danych trzymana w pamięci z własna interpretacja
    @Primary // After change profile this repo is primary
    @Profile("integration")
    TaskRepository testRepo(){
        return new TaskRepository(){
            private Map<Integer, Task> tasks = new HashMap<>();

            @Override
            public List<Task> findAll() {
                return tasks.values().stream().toList();
            }

            @Override
            public Page<Task> findAll(final Pageable page) {
                return null;
            }

            @Override
            public Optional<Task> findById(final Integer id) {
                return Optional.ofNullable(tasks.get(id));
            }

            @Override
            public boolean existsById(final Integer id) {
                return tasks.containsKey(id);
            }

            @Override
            public List<Task> findByDone(final boolean done) {
                return null;
            }

            @Override
            public Task save(final Task entity) {
                int key = tasks.size() +1;
                try {
                    var field = Task.class.getDeclaredField("ID");
                    field.setAccessible(true);
                    field.set(entity,key);// dostęp do pół klasy nielegalny
                } catch (NoSuchFieldException | IllegalAccessException e){
                    throw new RuntimeException(e);
                }
                tasks.put(key,entity);
                return tasks.get(key);
            }

            @Override
            public void deleteById(final int id) {
                tasks.remove(id);
            }

            @Override
            public boolean existsByDoneIsFalseAndTaskGroup_ID(final Integer groupid) {
                return false;
            }

            @Override
            public List<Task> findAllByTaskGroup_ID(final Integer groupID) {
                return List.of();
            }
        };
    }
}
