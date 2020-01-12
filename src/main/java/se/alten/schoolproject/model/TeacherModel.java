package se.alten.schoolproject.model;

import lombok.*;
import se.alten.schoolproject.entity.Student;
import se.alten.schoolproject.entity.Subject;
import se.alten.schoolproject.entity.Teacher;

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
public class TeacherModel implements Serializable {

    private String forename;
    private String lastname;
    private String email;
    private Set<String> subjects = new HashSet<>();

    public TeacherModel toModel(Teacher teacherToAdd) {
        TeacherModel teacherModel = new TeacherModel();
        teacherModel.setForename(teacherToAdd.getForename());
        teacherModel.setLastname(teacherToAdd.getLastname());
        teacherModel.setEmail(teacherToAdd.getEmail());
        teacherModel.setSubjects(subjectsToStringValues(teacherToAdd.getSubjects()));

        return teacherModel;
    }

    public List<TeacherModel> toModel(List<Teacher> teachers) {
        List<TeacherModel> teacherList = new ArrayList<>();
        for(Teacher t:teachers) {
            teacherList.add(toModel(t));
        }

        return teacherList;
    }

    private Set<String> subjectsToStringValues(Set<Subject> subjects) {
        Set<String> tempSet = new HashSet<>();
        for(Subject s:subjects) {
            tempSet.add(s.getTitle());
        }

        return tempSet;
    }

}
