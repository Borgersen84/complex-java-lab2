package se.alten.schoolproject.transaction;

import se.alten.schoolproject.entity.Teacher;
import se.alten.schoolproject.exception.DuplicateResourceException;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.ws.rs.NotFoundException;
import java.util.List;

@Stateless
@Default
public class TeacherTransaction implements TeacherTransactionAccess  {

    @PersistenceContext(unitName="school")
    private EntityManager entityManager;

    @Override
    public List listAllTeachers() {
        Query query = entityManager.createQuery("SELECT t FROM Teacher t");
        return query.getResultList();
    }

    @Override
    public Teacher addTeacher(Teacher teacherToAdd) throws DuplicateResourceException {
        try {
            entityManager.persist(teacherToAdd);
            entityManager.flush();
            return teacherToAdd;
        } catch (PersistenceException e) {
            throw new DuplicateResourceException("{\"Email already registered!\"}");
        }
    }

    @Override
    public Teacher findTeacherByEmail(String email) {
        String queryString = "SELECT t FROM Teacher t WHERE t.email = :email";
        Query query = entityManager.createQuery(queryString);
        query.setParameter("email", email);
        Teacher teacher = (Teacher) query.getSingleResult();

        return teacher;
    }
}
