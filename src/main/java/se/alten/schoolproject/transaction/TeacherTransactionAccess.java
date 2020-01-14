package se.alten.schoolproject.transaction;

import se.alten.schoolproject.entity.Teacher;
import se.alten.schoolproject.exception.DuplicateResourceException;

import java.util.List;

public interface TeacherTransactionAccess {

    List listAllTeachers();
    Teacher addTeacher(Teacher teacherToAdd) throws DuplicateResourceException;
    Teacher findTeacherByEmail(String email);
}
