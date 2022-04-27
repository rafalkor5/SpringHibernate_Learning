package pl.Korman.Spring.Learning.model.task;

import javax.persistence.Embeddable;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

@Embeddable
class Audit {

    private LocalDateTime created_on;
    private LocalDateTime updated_on;

    @PrePersist // Odpala sie przed zapisem do bazy danych
    void prePersist(){
        created_on = LocalDateTime.now();
    }

    @PreUpdate
    void preUpdate(){
        updated_on = LocalDateTime.now();
    }
}
