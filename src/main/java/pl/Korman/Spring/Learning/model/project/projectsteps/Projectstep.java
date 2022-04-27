package pl.Korman.Spring.Learning.model.project.projectsteps;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.Korman.Spring.Learning.model.project.Project;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@NoArgsConstructor // Konstruktor potrzebny do tworzenia encji
@Entity
@Table(name = "project_steps") //table daje możliwość zmiany domyślnej nazwy tabeli
public class Projectstep {
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
    private int days_to_deadline;

    @Getter(AccessLevel.PACKAGE)
    @Setter(AccessLevel.PACKAGE)
    @ManyToOne // Wiele taskgroyp do jednego projektu
    @JoinColumn(name ="project_id") // wskazanie kolumny po której dołączamy dane
    private Project projects_steps;
}
