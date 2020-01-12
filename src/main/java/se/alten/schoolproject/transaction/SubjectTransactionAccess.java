package se.alten.schoolproject.transaction;

import se.alten.schoolproject.entity.Student;
import se.alten.schoolproject.entity.Subject;

import javax.ejb.Local;
import java.util.List;

@Local
public interface SubjectTransactionAccess {
    List listAllSubjects();
    Subject addSubject(Subject subject) throws Exception;
    Subject getSubjectByName(String subject);
    Subject assignSubjectToStudent(String subjectTitle, String studentEmail);
    Subject assignSubjectToTeacher(Subject subject, Student student);

}
