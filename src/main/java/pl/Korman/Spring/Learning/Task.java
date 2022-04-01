package pl.Korman.Spring.Learning;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@Entity
//table daje możliwość zmiany domyślnej nazwy tabeli
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;

    @NotBlank(message = "Field description must not be empty")
    @Setter
    private String description;

    @Setter
    private boolean done;

}
