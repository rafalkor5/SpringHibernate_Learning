package pl.Korman.Spring.Learning.model;

import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

@MappedSuperclass
abstract class BaseAuditableEntity {

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
