package pl.Korman.Spring.Learning.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor // Konstruktor potrzebny do tworzenia encji
@Entity
//table daje możliwość zmiany domyślnej nazwy tabeli
@Table(name = "tasks")
public class Task {
    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;

    //validator
    @NotBlank(message = "Field description must not be empty")
    private String description;

    private boolean done;
    private LocalDateTime deadline;

    @Setter(AccessLevel.NONE) // Nie ma getter i setter
    @Getter(AccessLevel.NONE)
    private LocalDateTime created_on;
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private LocalDateTime updated_on;

    public void updateFrom(final Task source){
        description  = source.description;
        done = source.done;
        deadline = source.deadline;
    }

    @PrePersist // Odpala sie przed zapisem do bazy danych
    void prePersist(){
        created_on = LocalDateTime.now();
    }

    @PreUpdate
    void preUpdate(){
        updated_on = LocalDateTime.now();
    }


}
