package se.alten.schoolproject.rest;

import lombok.NoArgsConstructor;
import se.alten.schoolproject.dao.SchoolAccessLocal;
import se.alten.schoolproject.exception.DuplicateResourceException;
import se.alten.schoolproject.exception.EmptyFieldException;
import se.alten.schoolproject.exception.ResourceNotFoundException;
import se.alten.schoolproject.model.SubjectModel;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Stateless
@NoArgsConstructor
@Path("/subject")
public class SubjectController {

    @Inject
    private SchoolAccessLocal sal;

    @GET
    @Produces({"application/JSON"})
    public Response listSubjects() {
        try {
            List subject = sal.listAllSubjects();
            return Response.ok(subject).build();
        } catch (ResourceNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch ( Exception e ) {
            return Response.status(Response.Status.BAD_REQUEST).entity("{\"Something went wrong\"}").build();
        }
    }

    @GET
    @Path("/find")
    @Produces({"application/JSON"})
    public Response findSubjectByName(@QueryParam("title") String title) {
        try {
            SubjectModel subjectModel = sal.findSubjectByName(title);
            return Response.ok(subjectModel).build();
        } catch (ResourceNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (EmptyFieldException e) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("{\"Something went wrong!\"}").build();
        }
    }

    @POST
    @Path("/add")
    @Produces({"application/JSON"})
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addSubject(String subject) {
        try {
            SubjectModel subjectModel = sal.addSubject(subject);
            return Response.ok(subjectModel).build();
        } catch(DuplicateResourceException e){
            return Response.status(Response.Status.CONFLICT).entity(e.getMessage()).build();
        } catch (EmptyFieldException e){
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity(e.getMessage()).build();
        } catch (Exception e ) {
            return Response.status(Response.Status.BAD_REQUEST).entity("{\"Something went wrong!\"}").build();
        }
    }

    @DELETE
    @Path("/delete")
    @Produces({"application/JSON"})
    public Response deleteSubject(@QueryParam("title") String subjectTitle) {
        try {
            sal.removeSubject(subjectTitle);
            return Response.ok().build();
        } catch (EmptyFieldException e) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity(e.getMessage()).build();
        } catch (ResourceNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PATCH
    @Path("/student")
    @Produces({"application/JSON"})
    public Response addStudentToSubject(@QueryParam("title") String subjectTitle, @QueryParam("email") String studentEmail) {
        try {
            SubjectModel subjectModel = sal.addStudentToSubject(subjectTitle, studentEmail);
            return Response.ok(subjectModel).build();
        } catch (EmptyFieldException e) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity(e.getMessage()).build();
        } catch (ResourceNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (DuplicateResourceException e) {
            return Response.status(Response.Status.CONFLICT).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("{\"Something went wrong!\"}").build();
        }
    }

    @PATCH
    @Path("student/remove")
    @Produces({"application/JSON"})
    public Response removeStudentFromSubject(@QueryParam("title") String subjectTitle, @QueryParam("email") String studentEmail) {
        try {
            sal.removeStudentFromSubject(subjectTitle, studentEmail);
            return Response.ok().entity("{\"Student removed from " + subjectTitle + "\"}").build();
        } catch (EmptyFieldException e) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity(e.getMessage()).build();
        } catch (ResourceNotFoundException e){
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("{\"Something went wrong!\"}").build();
        }
    }

    @PATCH
    @Path("/teacher")
    @Produces({"application/JSON"})
    public Response addTeacherToSubject(@QueryParam("title") String subjectTitle, @QueryParam("email") String teacherEmail) {
        try {
            SubjectModel subjectModel = sal.addTeacherToSubject(subjectTitle, teacherEmail);
            return Response.ok(subjectModel).build();
        } catch (EmptyFieldException e) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity(e.getMessage()).build();
        } catch (ResourceNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (DuplicateResourceException e) {
            return Response.status(Response.Status.CONFLICT).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("{\"Something went wrong!\"}").build();
        }
    }

    @PATCH
    @Path("/teacher/remove")
    @Produces({"application/JSON"})
    public Response removeTeacherFromSubject(@QueryParam("title") String subjectTitle, @QueryParam("email") String teacherEmail) {
        try {
            sal.removeTeacherFromSubject(subjectTitle, teacherEmail);
            return Response.ok().entity("{\"Teacher removed from " + subjectTitle + "\"}").build();
        } catch (EmptyFieldException e) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity(e.getMessage()).build();
        } catch (ResourceNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("{\"Something went wrong!\"}").build();
        }
    }
}
