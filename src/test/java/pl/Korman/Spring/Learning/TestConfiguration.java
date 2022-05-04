package pl.Korman.Spring.Learning;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.Korman.Spring.Learning.model.task.Task;
import pl.Korman.Spring.Learning.model.task.TaskRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Configuration
class TestConfiguration {
    @Bean
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
                return tasks.put(tasks.size() + 1, entity );
            }

            @Override
            public void deleteById(final int id) {
                tasks.remove(id);
            }

            @Override
            public boolean existsByDoneIsFalseAndTaskGroup_ID(final Integer groupid) {
                return false;
            }
        };
    }
}
