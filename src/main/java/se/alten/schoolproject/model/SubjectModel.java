package se.alten.schoolproject.model;

import lombok.*;
import se.alten.schoolproject.entity.Student;
import se.alten.schoolproject.entity.Subject;
import se.alten.schoolproject.entity.Teacher;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SubjectModel implements Serializable {

    private Long id;
    private String title;
    private Set<Teacher> teachers = new HashSet<>();
    private Set<Student> students = new HashSet<>();

    public SubjectModel toModel(Subject subjectToAdd) {
        SubjectModel subjectModel = new SubjectModel();
        subjectModel.setId(subjectToAdd.getId());
        subjectModel.setTitle(subjectToAdd.getTitle());
        subjectModel.setStudents(subjectToAdd.getStudents());
        subjectModel.setTeachers(subjectToAdd.getTeachers());
        return subjectModel;
    }
}
