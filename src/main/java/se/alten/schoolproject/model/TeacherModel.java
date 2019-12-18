package se.alten.schoolproject.model;

import lombok.*;
import se.alten.schoolproject.entity.Teacher;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TeacherModel {

    private String forename;
    private String lastname;

    public TeacherModel toModel(Teacher teacherToAdd) {
        TeacherModel teacherModel = new TeacherModel();
        teacherModel.setForename(teacherToAdd.getForename());
        teacherModel.setLastname(teacherToAdd.getLastname());

        return teacherModel;
    }
}
