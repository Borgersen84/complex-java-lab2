package se.alten.schoolproject.dao;

import se.alten.schoolproject.exception.DuplicateResourceException;
import se.alten.schoolproject.exception.EmptyFieldException;
import se.alten.schoolproject.exception.ResourceNotFoundException;
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

    List listAllSubjects() throws ResourceNotFoundException;

    SubjectModel addSubject(String subjectModel) throws Exception;

    SubjectModel addTeacherToSubject(String subjectTitle, String teacherEmail) throws ResourceNotFoundException, EmptyFieldException, DuplicateResourceException;

    SubjectModel addStudentToSubject(String subjectTitle, String studentEmail) throws ResourceNotFoundException, EmptyFieldException, DuplicateResourceException;

    void removeStudentFromSubject(String subjectTitle, String studentEmail) throws ResourceNotFoundException, EmptyFieldException;

    void removeTeacherFromSubject(String subjectTitle, String teacherEmail) throws ResourceNotFoundException, EmptyFieldException;

    void removeSubject(String subjectTitle) throws ResourceNotFoundException, EmptyFieldException;

    List listAllTeachers() throws ResourceNotFoundException;

    TeacherModel addTeacher(String teacherModel) throws EmptyFieldException, DuplicateResourceException;

    TeacherModel findTeacherByEmail(String email) throws ResourceNotFoundException, EmptyFieldException;

    SubjectModel findSubjectByName(String subject) throws ResourceNotFoundException, EmptyFieldException;
}
