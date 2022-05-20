package pl.Korman.Spring.Learning.controller;

import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.Korman.Spring.Learning.model.task.TaskConfigurationProperties;

//Tu przekazujemy dane do restcontrollera

@AllArgsConstructor //konstruktor w starszych wersjach wymagany z @Autowired
@RestController
@RequestMapping("/info") //dzięki temu w tej klasie nie musimy za każdym razem pisać /info w mapingu
class InfoController {
    private DataSourceProperties dataSource;
    private TaskConfigurationProperties allowMulti;

    @GetMapping("/url")
    String url(){
    return dataSource.getUrl();
    }

    @GetMapping("/prop")
    boolean myProp(){
    return allowMulti.getTemplate().isAllowMultipleTasks();
    }
}
