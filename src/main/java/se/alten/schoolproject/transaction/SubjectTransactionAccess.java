package se.alten.schoolproject.transaction;

import se.alten.schoolproject.entity.Student;
import se.alten.schoolproject.entity.Subject;

import javax.ejb.Local;
import java.util.List;

@Local
public interface SubjectTransactionAccess {
    List listAllSubjects();
    Subject addSubject(Subject subject) throws Exception;
    void removeSubject(String subjectTitle);
    Subject getSubjectByName(String subject);
    Subject assignSubjectToStudent(String subjectTitle, String studentEmail);
    Subject assignSubjectToTeacher(String subjectTitle, String teacherEmail);
    void removeStudentFromSubject(String subjectTitle, String studentEmail);
    void removeTeacherFromSubject(String subjectTitle, String teacherEmail);

}
