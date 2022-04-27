package pl.Korman.Spring.Learning.model.project;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.Korman.Spring.Learning.model.project.projectsteps.Projectstep;
import pl.Korman.Spring.Learning.model.taskgroup.TaskGroup;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@NoArgsConstructor // Konstruktor potrzebny do tworzenia encji
@Entity
@Table(name = "projects") //table daje możliwość zmiany domyślnej nazwy tabeli
public class Project {
    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //validator
    @NotBlank(message = "Field description must not be empty")
    @Getter
    @Setter
    private String description;

    @Getter
    @Setter(AccessLevel.PACKAGE)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "project")
    // Dociągane dopiero po zawołaniu gettera// cascade // wskazanie pola z project odpowiadającego za relacje
    private Set<TaskGroup> groups; // Unikalne projekty

    @Getter
    @Setter(AccessLevel.PACKAGE)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "projects_steps")
    // Dociągane dopiero po zawołaniu gettera// cascade // wskazanie pola z project odpowiadającego za relacje
    private Set<Projectstep> projectsteps; // Unikalne projekty


}
