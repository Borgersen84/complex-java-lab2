package se.alten.schoolproject.transaction;

import se.alten.schoolproject.entity.Teacher;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.NotFoundException;
import java.util.List;

@Stateless
@Default
public class TeacherTransaction implements TeacherTransactionAccess  {

    @PersistenceContext(unitName="school")
    private EntityManager entityManager;

    @Override
    public List listAllTeachers() {
        return null;
    }

    @Override
    public Teacher addTeacher(Teacher teacherToAdd) throws Exception {
        try {
            entityManager.persist(teacherToAdd);
            entityManager.flush();
            return teacherToAdd;
        } catch (Exception e) {
            throw new Exception("This didnt work");
        }
    }


}
