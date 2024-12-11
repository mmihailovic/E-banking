package rs.edu.raf.model.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Employee extends User {
    private String position;
    private String department;
    @ManyToOne
    private Company company;
}
