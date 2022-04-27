package pl.Korman.Spring.Learning.model.taskgroup;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.Korman.Spring.Learning.model.project.Project;
import pl.Korman.Spring.Learning.model.task.Task;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@NoArgsConstructor // Konstruktor potrzebny do tworzenia encji
@Entity
@Table(name = "tasks_groups") //table daje możliwość zmiany domyślnej nazwy tabeli
public class TaskGroup {
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
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "taskGroup")
    // Dociągane dopiero po zawołaniu gettera// cascade // wskazanie pola z task odpowiadającego za relacje
    private Set<Task> tasks; // Unikalne taski

    @Getter(AccessLevel.PACKAGE)
    @Setter(AccessLevel.PACKAGE)
    @ManyToOne // Wiele taskgroyp do jednego projektu
    @JoinColumn(name ="project_id") // wskazanie kolumny po której dołączamy dane
    private Project project;
}
