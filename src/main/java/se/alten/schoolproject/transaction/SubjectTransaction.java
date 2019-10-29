package se.alten.schoolproject.transaction;

import org.hibernate.Session;
import se.alten.schoolproject.entity.Student;
import se.alten.schoolproject.entity.Subject;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
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
    public Subject addSubject(Subject subject) {
        try {
            entityManager.persist(subject);
            entityManager.flush();
            return subject;
        } catch ( PersistenceException pe ) {
            subject.setTitle("duplicate");
            return subject;
        }
    }

    @Override
    public Subject getSubjectByName(String subject) {
        System.out.println(subject);
        Query query = entityManager.createNativeQuery("SELECT * FROM Subject WHERE title = :subject", Subject.class);

        Subject t = (Subject)query.setParameter("subject", subject).getSingleResult();
        System.out.println(t.toString());

        return t;
     }
}
