package se.alten.schoolproject.dao;

import se.alten.schoolproject.entity.Student;
import se.alten.schoolproject.entity.Subject;
import se.alten.schoolproject.entity.Teacher;
import se.alten.schoolproject.exception.EmptyFieldException;
import se.alten.schoolproject.exception.StudentNotFoundException;
import se.alten.schoolproject.model.StudentModel;
import se.alten.schoolproject.model.SubjectModel;
import se.alten.schoolproject.model.TeacherModel;
import se.alten.schoolproject.transaction.StudentTransactionAccess;
import se.alten.schoolproject.transaction.SubjectTransactionAccess;
import se.alten.schoolproject.transaction.TeacherTransactionAccess;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Stream;

@Stateless
public class SchoolDataAccess implements SchoolAccessLocal, SchoolAccessRemote {

    private Student student = new Student();
    private StudentModel studentModel = new StudentModel();
    private Subject subject = new Subject();
    private SubjectModel subjectModel = new SubjectModel();
    private TeacherModel teacherModel = new TeacherModel();
    private Teacher teacher = new Teacher();

    @Inject
    StudentTransactionAccess studentTransactionAccess;

    @Inject
    SubjectTransactionAccess subjectTransactionAccess;

    @Inject
    TeacherTransactionAccess teacherTransactionAccess;

    @Override
    public List listAllStudents() throws StudentNotFoundException {
        return studentTransactionAccess.listAllStudents();
    }

    @Override
    public StudentModel findStudentByName(String forename, String lastname) throws StudentNotFoundException, EmptyFieldException {
        if( !forename.isBlank() && !lastname.isBlank() ) {
            try {
                return studentModel.toModel(studentTransactionAccess.findStudentByName(forename, lastname));
            } catch (Exception e) {
                throw new StudentNotFoundException("{\"Student not found!\"}");
            }
        } else {
            throw new EmptyFieldException("{\"No empty fields allowed!\"}");
        }

    }

    @Override
    public StudentModel addStudent(String newStudent) throws Exception {
        Student studentToAdd = student.toEntity(newStudent);
        boolean checkForEmptyVariables = Stream.of(studentToAdd.getForename(), studentToAdd.getLastname(), studentToAdd.getEmail()).anyMatch(String::isBlank);
        if (!checkForEmptyVariables) {
            studentTransactionAccess.addStudent(studentToAdd);
            return studentModel.toModel(studentToAdd);
        } else {
            throw new EmptyFieldException("{\"No empty fields allowed!\"}");
        }
    }

    @Override
    public StudentModel removeStudent(String studentEmail) throws StudentNotFoundException, EmptyFieldException {
        if (!studentEmail.isBlank()) {
            try {
                return studentModel.toModel(studentTransactionAccess.removeStudent(studentEmail));
            } catch (Exception e) {
                throw new StudentNotFoundException("{\"This User Does Not Exist!\"}");
            }
        } else {
            throw new EmptyFieldException("{\"No empty fields allowed!\"}");
        }

    }

    @Override
    public StudentModel updateStudent(String forename, String lastname, String email) throws StudentNotFoundException, EmptyFieldException {
        return studentModel.toModel(studentTransactionAccess.updateStudent(forename, lastname, email));
    }

    @Override
    public StudentModel updateStudentPartial(String studentModel) throws StudentNotFoundException, EmptyFieldException {
        Student studentToUpdate = student.toEntity(studentModel);
        return this.studentModel.toModel(studentTransactionAccess.updateStudentPartial(studentToUpdate));
    }

    @Override
    public List listAllSubjects() {
        return subjectTransactionAccess.listAllSubjects();
    }

    @Override
    public SubjectModel addSubject(String newSubject) throws Exception {
        Subject subjectToAdd = subject.toEntity(newSubject);
        subjectTransactionAccess.addSubject(subjectToAdd);
        return subjectModel.toModel(subjectToAdd);
    }

    @Override
    public List listAllTeachers() {
        return teacherTransactionAccess.listAllTeachers();
    }

    @Override
    public TeacherModel addTeacher(String newTeacher) throws Exception {
        Teacher teacherToAdd = teacher.toEntity(newTeacher);
        teacherTransactionAccess.addTeacher(teacherToAdd);
        return teacherModel.toModel(teacherToAdd);
    }

    @Override
    public SubjectModel findSubjectByName(String subject) {
        return subjectModel.toModel(subjectTransactionAccess.getSubjectByName(subject));
    }


}
