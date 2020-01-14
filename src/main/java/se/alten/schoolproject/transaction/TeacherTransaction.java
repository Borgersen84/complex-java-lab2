package se.alten.schoolproject.transaction;

import se.alten.schoolproject.entity.Teacher;
import se.alten.schoolproject.exception.DuplicateResourceException;
import se.alten.schoolproject.exception.ResourceNotFoundException;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.persistence.*;
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
    public Teacher findTeacherByEmail(String email) throws ResourceNotFoundException {
        String queryString = "SELECT t FROM Teacher t WHERE t.email = :email";
        Query query = entityManager.createQuery(queryString);
        query.setParameter("email", email);
        Teacher teacher;
        try {
            teacher = (Teacher) query.getSingleResult();
        } catch (NoResultException e) {
            throw new ResourceNotFoundException("{\"This teacher is not registered!\"}");
        }
        return teacher;
    }
}
