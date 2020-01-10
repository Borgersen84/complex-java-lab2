package se.alten.schoolproject.model;

import lombok.*;
import se.alten.schoolproject.entity.Teacher;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TeacherModel implements Serializable {

    private String forename;
    private String lastname;
    private String email;

    public TeacherModel toModel(Teacher teacherToAdd) {
        TeacherModel teacherModel = new TeacherModel();
        teacherModel.setForename(teacherToAdd.getForename());
        teacherModel.setLastname(teacherToAdd.getLastname());
        teacherModel.setEmail(teacherToAdd.getEmail());

        return teacherModel;
    }
}
