package pl.Korman.Spring.Learning.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;


@NoArgsConstructor // Konstruktor potrzebny do tworzenia encji
@Entity
@Table(name = "tasks") //table daje możliwość zmiany domyślnej nazwy tabeli
public class Task extends BaseAuditableEntity{
    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;

    //validator
    @NotBlank(message = "Field description must not be empty")
    @Getter
    @Setter
    private String description;

    @Getter
    @Setter
    private boolean done;

    @Getter
    @Setter
    private LocalDateTime deadline;

    //methods
    public void updateFrom(final Task source){
        description  = source.description;
        done = source.done;
        deadline = source.deadline;
    }

 }
