package pl.Korman.Spring.Learning.model.task;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.Korman.Spring.Learning.model.taskgroup.TaskGroup;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;


@NoArgsConstructor // Konstruktor potrzebny do tworzenia encji
@Entity
@Table(name = "tasks") //table daje możliwość zmiany domyślnej nazwy tabeli
public class Task {
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


//    @AttributeOverrides({ // tak można nadpisać nazwy kolumn
//            @AttributeOverride(column = @Column(name = "overidecreatedonName"), name = "created_on")
//    })
    @Embedded // oznaczenie obiektu embedded klasy audit
    private Audit audit = new Audit();

    //@ManyToOne(cascade = CascadeType.REMOVE) Mozna ustawić typ relacji
    @Getter(AccessLevel.PACKAGE)
    @Setter(AccessLevel.PACKAGE)
    @ManyToOne // Wiele tasków do jednej taskgroup
    @JoinColumn(name ="task_group_id") // wskazanie kolumny po której dołączamy dane
    private TaskGroup taskGroup;

    //methods
    public void updateFrom(final Task source){
        description  = source.description;
        done = source.done;
        deadline = source.deadline;
        taskGroup = source.taskGroup;
    }

 }
