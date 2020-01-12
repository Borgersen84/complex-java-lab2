package se.alten.schoolproject.rest;

import lombok.NoArgsConstructor;
import net.bytebuddy.asm.Advice;
import se.alten.schoolproject.dao.SchoolAccessLocal;
import se.alten.schoolproject.dao.SchoolDataAccess;
import se.alten.schoolproject.model.TeacherModel;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Stateless
@NoArgsConstructor
@Path("/teacher")
public class TeacherController {

    @Inject
    private SchoolAccessLocal sal;

    @GET
    @Produces({"application/JSON"})
    public Response listTeachers() {
            List teachers = sal.listAllTeachers();
            return Response.ok(teachers).build();
    }

    @POST
    @Path("/add")
    @Produces({"application/JSON"})
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addTeacher(String teacherModel) {
        try {
            TeacherModel teacher = sal.addTeacher(teacherModel);
            return Response.ok(teacher).build();
        } catch (Exception e) {
            return Response.status(Response.Status.CONFLICT).entity(e.getMessage()).build();

        }
    }

    @GET
    @Path("/find")
    @Produces({"application/JSON"})
    public Response findTeacherByEmail(@QueryParam("email") String email) {
        try {
            TeacherModel teacher = sal.findTeacherByEmail(email);
            return Response.ok(teacher).build();
        } catch (Exception e) {
            return Response.status(Response.Status.CONFLICT).entity(e.getMessage()).build();
        }

    }

    public Response deleteTeacher() {
        return null;
    }
}
