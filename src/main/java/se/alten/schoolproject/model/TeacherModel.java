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
public class TeacherModel implements Serializable {

    private String forename;
    private String lastname;
    private String email;
    private Set<Subject> subjects = new HashSet<>();
    private Set<Set<Student>> students = new HashSet<>();

    public TeacherModel toModel(Teacher teacherToAdd) {
        TeacherModel teacherModel = new TeacherModel();
        teacherModel.setForename(teacherToAdd.getForename());
        teacherModel.setLastname(teacherToAdd.getLastname());
        teacherModel.setEmail(teacherToAdd.getEmail());
        teacherModel.setSubjects(teacherToAdd.getSubjects());
        teacherModel.setStudents(getStudentsFromSubjects(subjects));

        return teacherModel;
    }

    private Set<Set<Student>> getStudentsFromSubjects(Set<Subject> subjects) {
        for(Subject s:subjects) {
            this.students.add(s.getStudents());
        }
        return this.students;
    }
}
