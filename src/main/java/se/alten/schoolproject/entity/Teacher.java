package se.alten.schoolproject.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Teacher implements Serializable {

    @Id
    private Long id;

    @Column
    private String forename;

    @Column
    private String lastname;

    private Set<Student> students = new HashSet<>();

    private Set<Subject> subjects = new HashSet<>();
}
