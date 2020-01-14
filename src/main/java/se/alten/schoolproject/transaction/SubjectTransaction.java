package se.alten.schoolproject.transaction;

import org.hibernate.Session;
import se.alten.schoolproject.entity.Student;
import se.alten.schoolproject.entity.Subject;
import se.alten.schoolproject.entity.Teacher;
import se.alten.schoolproject.exception.DuplicateResourceException;
import se.alten.schoolproject.exception.ResourceNotFoundException;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.persistence.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Stateless
@Default
public class SubjectTransaction implements SubjectTransactionAccess{

    @PersistenceContext(unitName="school")
    private EntityManager entityManager;

    @Inject
    private StudentTransactionAccess studentTransactionAccess;

    @Override
    public List listAllSubjects() {
        Query query = entityManager.createQuery("SELECT s FROM Subject s");
        return query.getResultList();
    }

    @Override
    public Subject addSubject(Subject subject) throws DuplicateResourceException {
        try {
            entityManager.persist(subject);
            entityManager.flush();
            return subject;
        } catch ( PersistenceException pe ) {
            throw new DuplicateResourceException("{\"Subject " + subject.getTitle() + " already exist!\"}");
        }
    }

    @Override
    public Subject getSubjectByName(String subjectTitle) {
        String queryStr = "SELECT s FROM Subject s WHERE s.title = :title";
        Query query = entityManager.createQuery(queryStr);
        query.setParameter("title", subjectTitle);
        Subject subjectName = (Subject) query.getSingleResult();

        return subjectName;
     }

    @Override
    public Subject assignSubjectToStudent(String subjectTitle, String studentEmail) throws ResourceNotFoundException, DuplicateResourceException {
        Subject subject;
        try {
            subject = getSubjectByName(subjectTitle);
        } catch (NoResultException e) {
            throw new ResourceNotFoundException("{\"This subject does not exist\"}");
        }

        String studentQueryStr = "SELECT s FROM Student s WHERE s.email = :email";
        Query studentQuery = entityManager.createQuery(studentQueryStr);
        studentQuery.setParameter("email", studentEmail);
        Student student;
        try {
            student = (Student) studentQuery.getSingleResult();
        } catch (NoResultException e) {
            throw new ResourceNotFoundException("{\"This student does not exist\"}");
        }
        if (subject.getStudents().contains(student)) {
            throw new DuplicateResourceException("{\"Student already assigned to this subject!\"}");
        }

        Set<Student> students = subject.getStudents();
        Set<Subject> subjects = student.getSubject();
        students.add(student);
        subjects.add(subject);
        subject.setStudents(students);
        student.setSubject(subjects);
        entityManager.flush();

        return subject;
    }

    @Override
    public Subject assignSubjectToTeacher(String subjectTitle, String teacherEmail) throws ResourceNotFoundException, DuplicateResourceException {
        Subject subject;
        try {
            subject = getSubjectByName(subjectTitle);
        } catch (NoResultException e) {
            throw new ResourceNotFoundException("{\"This subject does not exist!\"}");
        }
        String teacherQueryStr = "SELECT t FROM Teacher t WHERE t.email = :email";
        Query teacherQuery = entityManager.createQuery(teacherQueryStr);
        teacherQuery.setParameter("email", teacherEmail);
        Teacher teacher;
        try {
            teacher = (Teacher) teacherQuery.getSingleResult();
        } catch (NoResultException e) {
            throw new ResourceNotFoundException("{\"This teacher does not exist!\"}");
        }
        if (subject.getTeachers().contains(teacher)) {
            throw new DuplicateResourceException("{\"This teacher is already assigned to " + subjectTitle + "\"}");
        }

        Set<Teacher> teachers = subject.getTeachers();
        Set<Subject> subjects = teacher.getSubjects();
        teachers.add(teacher);
        subjects.add(subject);
        subject.setTeachers(teachers);
        teacher.setSubjects(subjects);
        entityManager.flush();

        return subject;
    }

    @Override
    public void removeSubject(String subjectTitle) throws ResourceNotFoundException{
        String deleteSubjectQuery = "DELETE FROM Subject s WHERE s.title = :title";
        Subject subject;
        try {
            subject = getSubjectByName(subjectTitle);
            Query query = entityManager.createQuery(deleteSubjectQuery);
            query.setParameter("title", subjectTitle).executeUpdate();
        } catch (NoResultException e) {
            throw new ResourceNotFoundException("{\"This subject does not exist!\"}");
        }

        entityManager.flush();
    }

    @Override
    public void removeStudentFromSubject(String subjectTitle, String studentEmail) throws ResourceNotFoundException {
        String studentQueryStr = "SELECT s FROM Student s WHERE s.email = :email";
        Subject subjectForStudent;
        try {
            subjectForStudent = getSubjectByName(subjectTitle);
        } catch (NoResultException e) {
            throw  new ResourceNotFoundException("This subject does not exist");
        }
        Query studentQuery = entityManager.createQuery(studentQueryStr);
        Student student = null;
        try {
            student = (Student) studentQuery.setParameter("email", studentEmail).getSingleResult();
            student.getSubject().removeIf(subject -> subject.getTitle().equals(subjectTitle));
            entityManager.merge(student);
        } catch (Exception e) {}
        if (student == null) {
            throw new ResourceNotFoundException("{\"This student does not exist\"}");
        }
        else if (!subjectForStudent.getStudents().contains(student)) {
            throw new ResourceNotFoundException("{\"This student does not have this subject\"}");
        }
        entityManager.flush();
    }

    @Override
    public void removeTeacherFromSubject(String subjectTitle, String teacherEmail) throws ResourceNotFoundException {
        String teacherQueryStr = "SELECT t FROM Teacher t WHERE t.email = :email";
        Subject subjectForTeacher;
        try {
            subjectForTeacher = getSubjectByName(subjectTitle);
        } catch (NoResultException e) {
            throw new ResourceNotFoundException("{\"This subject does not exist\"}");
        }

        Teacher teacher = null;
        Query teacherQuery = entityManager.createQuery(teacherQueryStr);
        try {
            teacher = (Teacher) teacherQuery.setParameter("email", teacherEmail).getSingleResult();
            teacher.getSubjects().removeIf(subject -> subject.getTitle().equals(subjectTitle));
            entityManager.merge(teacher);
        } catch (Exception e) {}
        if (teacher == null) {
            throw new ResourceNotFoundException("{\"This teacher does not exist\"}");
        }
        else if (!subjectForTeacher.getTeachers().contains(teacher)) {
            throw new ResourceNotFoundException("{\"This teacher does not have this subject\"}");
        }
        entityManager.flush();
    }
}
