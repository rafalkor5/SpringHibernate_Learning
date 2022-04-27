package pl.Korman.Spring.Learning.controller;

import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.Korman.Spring.Learning.model.task.TaskConfigurationProperties;

//Tu przekazujemy dane do restcontrollera
@AllArgsConstructor
@RestController
class InfoController {

    private DataSourceProperties dataSource;
    private TaskConfigurationProperties allowMulti;

    @GetMapping("/info/url")
    String url(){
    return dataSource.getUrl();
    }

    @GetMapping("/info/prop")
    boolean myProp(){
    return allowMulti.getTemplate().isAllowMultipleTasks();
    }
}
