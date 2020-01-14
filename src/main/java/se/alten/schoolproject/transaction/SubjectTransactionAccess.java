package se.alten.schoolproject.transaction;

import se.alten.schoolproject.entity.Student;
import se.alten.schoolproject.entity.Subject;
import se.alten.schoolproject.exception.DuplicateResourceException;
import se.alten.schoolproject.exception.ResourceNotFoundException;

import javax.ejb.Local;
import java.util.List;

@Local
public interface SubjectTransactionAccess {
    List listAllSubjects();
    Subject addSubject(Subject subject) throws Exception;
    void removeSubject(String subjectTitle);
    Subject getSubjectByName(String subject);
    Subject assignSubjectToStudent(String subjectTitle, String studentEmail) throws ResourceNotFoundException, DuplicateResourceException;
    Subject assignSubjectToTeacher(String subjectTitle, String teacherEmail);
    void removeStudentFromSubject(String subjectTitle, String studentEmail) throws ResourceNotFoundException;
    void removeTeacherFromSubject(String subjectTitle, String teacherEmail) throws ResourceNotFoundException;

}
