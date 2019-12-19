package se.alten.schoolproject.dao;

import se.alten.schoolproject.exception.EmptyFieldException;
import se.alten.schoolproject.exception.StudentNotFoundException;
import se.alten.schoolproject.model.StudentModel;
import se.alten.schoolproject.model.SubjectModel;
import se.alten.schoolproject.model.TeacherModel;

import javax.ejb.Local;
import java.util.List;

@Local
public interface SchoolAccessLocal {

    List listAllStudents() throws StudentNotFoundException;

    StudentModel findStudentByName(String forename, String lastName) throws StudentNotFoundException, EmptyFieldException;

    StudentModel addStudent(String studentModel) throws Exception;

    StudentModel removeStudent(String student) throws StudentNotFoundException, EmptyFieldException;

    StudentModel updateStudent(String forename, String lastname, String email) throws StudentNotFoundException, EmptyFieldException;

    StudentModel updateStudentPartial(String studentModel) throws StudentNotFoundException, EmptyFieldException;

    List listAllSubjects();

    SubjectModel addSubject(String subjectModel) throws Exception;

    List listAllTeachers();

    TeacherModel addTeacher(String teacherModel) throws Exception;
}
