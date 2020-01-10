package se.alten.schoolproject.transaction;

import org.hibernate.Session;
import se.alten.schoolproject.entity.Student;
import se.alten.schoolproject.entity.Subject;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.persistence.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Stateless
@Default
public class SubjectTransaction implements SubjectTransactionAccess{

    @PersistenceContext(unitName="school")
    private EntityManager entityManager;

    @Override
    public List listAllSubjects() {
        Query query = entityManager.createQuery("SELECT s FROM Subject s");
        return query.getResultList();
    }

    @Override
    public Subject addSubject(Subject subject) throws Exception {
        try {
            entityManager.persist(subject);
            entityManager.flush();
            return subject;
        } catch ( PersistenceException pe ) {
            throw new Exception(pe.getCause());
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
    public Subject addStudentToSubject(String email) {
        return null;
    }


}
