package se.alten.schoolproject.model;

import lombok.*;
import se.alten.schoolproject.entity.Student;
import se.alten.schoolproject.entity.Subject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StudentModel implements Serializable {

    private String forename;
    private String lastname;
    private String email;
    private Set<String> subjects = new HashSet<>();

    public StudentModel toModel(Student student) {
        StudentModel studentModel = new StudentModel();

        studentModel.setForename(student.getForename());
        studentModel.setLastname(student.getLastname());
        studentModel.setEmail(student.getEmail());
        studentModel.setSubjects(subjectsToStringValues(student.getSubject()));

        return studentModel;
    }

    public List<StudentModel> toModel(List<Student> students) {
        List<StudentModel> studentList = new ArrayList<>();
        for(Student s:students){
            studentList.add(toModel(s));
        }

        return studentList;
    }

    private Set<String> subjectsToStringValues(Set<Subject> subjects) {
        Set<String> tempSet = new HashSet<>();
        for(Subject s:subjects) {
            tempSet.add(s.getTitle());
        }

        return tempSet;
    }
}
