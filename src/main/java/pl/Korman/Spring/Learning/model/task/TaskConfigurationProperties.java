package pl.Korman.Spring.Learning.model.task;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

// Ta klasa automatycznie pobiera dane z application.yaml
@Configuration
@ConfigurationProperties("task")
public class TaskConfigurationProperties {

    @Getter
    @Setter
    private Template template;

    @Getter
    @Setter
    public static class Template {
        private boolean allowMultipleTasks;

    }
}
