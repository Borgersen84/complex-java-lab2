package se.alten.schoolproject.model;

import lombok.*;
import se.alten.schoolproject.entity.Student;
import se.alten.schoolproject.entity.Subject;
import se.alten.schoolproject.entity.Teacher;

import java.io.Serializable;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SubjectModel implements Serializable {

    private Long id;
    private String title;
    private Set<String> teachers = new HashSet<>();
    private Set<String> students = new HashSet<>();

    public SubjectModel toModel(Subject subjectToAdd) {
        SubjectModel subjectModel = new SubjectModel();
        subjectModel.setId(subjectToAdd.getId());
        subjectModel.setTitle(subjectToAdd.getTitle());
        subjectModel.setStudents(studentsToStringValues(subjectToAdd.getStudents()));
        subjectModel.setTeachers(teachersToStringValues(subjectToAdd.getTeachers()));
        return subjectModel;
    }

    public List<SubjectModel> toModel(List<Subject> subjects) {
        List<SubjectModel> subjectList = new ArrayList<>();
        for(Subject s:subjects) {
            subjectList.add(toModel(s));
        }

        return subjectList;
    }

    private Set<String> studentsToStringValues(Set<Student> students) {
        Set<String> tempSet = new HashSet<>();
        for(Student s:students) {
            tempSet.add(s.getForename() + " " + s.getLastname());
        }

        return tempSet;
    }

    private Set<String> teachersToStringValues(Set<Teacher> teachers) {
        Set<String> tempSet = new HashSet<>();
        for(Teacher t:teachers) {
            tempSet.add(t.getForename() + " " + t.getLastname());
        }

        return tempSet;
    }

}

