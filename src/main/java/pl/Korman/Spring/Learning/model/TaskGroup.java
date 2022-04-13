package pl.Korman.Spring.Learning.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@NoArgsConstructor // Konstruktor potrzebny do tworzenia encji
@Entity
@Table(name = "tasks_groups") //table daje możliwość zmiany domyślnej nazwy tabeli
class TaskGroup {
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
    @Setter(AccessLevel.PACKAGE)
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "taskGroup")
    // Dociągane dopiero po zawołaniu gettera// cascade // wskazanie pola z task odpowiadającego za relacje
    private Set<Task> tasks; // Unikalne taski

}
