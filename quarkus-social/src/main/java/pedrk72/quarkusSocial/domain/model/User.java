package pedrk72.quarkusSocial.domain.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Entity //To indicate that is an entity of the database.
@Table(name = "users") // To indicate the respective table name on DB.
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private Integer age;

    //Method equals is important to the comparison between objects.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(name, user.name) && Objects.equals(age, user.age);
    }

    //Method hashCode is important to the comparison between objects.
    @Override
    public int hashCode() {
        return Objects.hash(id, name, age);
    }
}
