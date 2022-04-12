package pl.Korman.Spring.Learning.model;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;

    //validator
    @NotBlank(message = "Field description must not be empty")
    private String description;

    private boolean done;

    private LocalDateTime deadline;

}
