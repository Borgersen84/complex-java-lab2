package se.alten.schoolproject.dao;

import se.alten.schoolproject.entity.Student;
import se.alten.schoolproject.entity.Subject;
import se.alten.schoolproject.entity.Teacher;
import se.alten.schoolproject.exception.DuplicateResourceException;
import se.alten.schoolproject.exception.EmptyFieldException;
import se.alten.schoolproject.exception.ResourceNotFoundException;
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
        return studentModel.toModel(studentTransactionAccess.listAllStudents());
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
    public List listAllSubjects() throws ResourceNotFoundException {
        // return subjectModel.toModel(subjectTransactionAccess.listAllSubjects());
        List<SubjectModel> subjectModelList = subjectModel.toModel(subjectTransactionAccess.listAllSubjects());
        if(subjectModelList.size() < 1) {
            throw new ResourceNotFoundException("{\"The list is empty!\"}");
        }

        return subjectModelList;
    }

    @Override
    public SubjectModel addSubject(String newSubject) throws Exception {
        Subject subjectToAdd = subject.toEntity(newSubject);
        //boolean checkForEmptyVariables = Stream.of(subjectToAdd.getTitle()).anyMatch(String::isBlank);
        if(!subjectToAdd.getTitle().isBlank()) {
            subjectTransactionAccess.addSubject(subjectToAdd);
            return subjectModel.toModel(subjectToAdd);
        } else {
            throw new EmptyFieldException("{\"No empty fields allowed!\"}");
        }
    }

    @Override
    public List listAllTeachers() throws ResourceNotFoundException {
        List<TeacherModel> teacherModelList = teacherModel.toModel(teacherTransactionAccess.listAllTeachers());
        if (teacherModelList.size() < 1) {
            throw new ResourceNotFoundException("{\"This list is empty!\"}");
        }

        return teacherModelList;
        //return teacherModel.toModel(teacherTransactionAccess.listAllTeachers());
    }

    @Override
    public TeacherModel addTeacher(String newTeacher) throws DuplicateResourceException, EmptyFieldException {
        Teacher teacherToAdd = teacher.toEntity(newTeacher);
        boolean checkForEmptyVariables = Stream.of(teacherToAdd.getForename(), teacherToAdd.getLastname(), teacherToAdd.getEmail()).anyMatch(String::isBlank);
        if (!checkForEmptyVariables) {
            return teacherModel.toModel(teacherTransactionAccess.addTeacher(teacherToAdd));
        } else {
            throw new EmptyFieldException("{\"No empty fields allowed!\"}");
        }
        /*teacherTransactionAccess.addTeacher(teacherToAdd);
        return teacherModel.toModel(teacherToAdd);*/
    }

    @Override
    public TeacherModel findTeacherByEmail(String email) throws ResourceNotFoundException, EmptyFieldException {
        if (!email.isBlank()) {
            return teacherModel.toModel(teacherTransactionAccess.findTeacherByEmail(email));
        } else {
            throw new EmptyFieldException("{\"No empty fields allowed\"}");
        }

    }

    @Override
    public SubjectModel findSubjectByName(String subject) throws EmptyFieldException, ResourceNotFoundException {
        if(!subject.isBlank()){
            try {
                return subjectModel.toModel(subjectTransactionAccess.getSubjectByName(subject));
            } catch (Exception e) {
                throw new ResourceNotFoundException("{\"Subject " + subject + " not found\"}");
            }
        } else {
            throw new EmptyFieldException("{\"No empty fields allowed\"}");
        }

    }

    @Override
    public SubjectModel addTeacherToSubject(String subjectTitle, String teacherEmail) throws ResourceNotFoundException, EmptyFieldException, DuplicateResourceException {
        if (!teacherEmail.isBlank() && !subjectTitle.isBlank()) {
            return subjectModel.toModel(subjectTransactionAccess.assignSubjectToTeacher(subjectTitle, teacherEmail));
        }
        else {
            throw new EmptyFieldException("{\"No empty fields allowed\"}");
        }
    }

    @Override
    public SubjectModel addStudentToSubject(String subjectTitle, String studentEmail) throws ResourceNotFoundException, EmptyFieldException, DuplicateResourceException {
        if(!studentEmail.isBlank() && !subjectTitle.isBlank()) {
            return subjectModel.toModel(subjectTransactionAccess.assignSubjectToStudent(subjectTitle, studentEmail));
        }
        else {
            throw new EmptyFieldException("{\"No empty fields allowed\"}");
        }
    }

    @Override
    public void removeStudentFromSubject(String subjectTitle, String studentEmail) throws ResourceNotFoundException, EmptyFieldException {
        /*Subject subject = subjectTransactionAccess.getSubjectByName(subjectTitle);
        if(!subject.getTitle().equals(subjectTitle)) {
            throw new ResourceNotFoundException("{\"This course does not exist\"}");
        }
        else {
            subjectTransactionAccess.removeStudentFromSubject(subjectTitle, studentEmail);
        }*/if (!subjectTitle.isBlank() && !studentEmail.isBlank()) {
            subjectTransactionAccess.removeStudentFromSubject(subjectTitle, studentEmail);
        } else {
            throw new EmptyFieldException("{\"No empty fields allowed!\"}");
        }


    }

    @Override
    public void removeTeacherFromSubject(String subjectTitle, String teacherEmail) throws ResourceNotFoundException, EmptyFieldException {
        if (!subjectTitle.isBlank() && !teacherEmail.isBlank()) {
            subjectTransactionAccess.removeTeacherFromSubject(subjectTitle, teacherEmail);
        } else {
            throw new EmptyFieldException("{\"No empty fields allowed!\"}");
        }

    }

    @Override
    public void removeSubject(String subjectTitle) throws ResourceNotFoundException, EmptyFieldException{
        if (!subjectTitle.isBlank()) {
            subjectTransactionAccess.removeSubject(subjectTitle);
        }
        else {
            throw new EmptyFieldException("{\"No empty fields allowed!\"}");
        }
    }

}
