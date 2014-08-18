package ZhixiongTang.ProjectAllocation.api;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("AdminService")
public interface AdminService {

	@Path("information/{aid}")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	Response getInformationFromAid(@PathParam("aid") String aid);

	@Path("login")
	@POST
	@Produces({ MediaType.APPLICATION_JSON })
	Response loginAdmin(@HeaderParam("aid") String aid,
			@HeaderParam("password") String password);

	@Path("logout")
	@POST
	@Produces({ MediaType.APPLICATION_JSON })
	Response logoutAdmin(@HeaderParam("aid") String aid,
			@HeaderParam("session") String adminSession);

	@Path("matching")
	@POST
	@Produces({ MediaType.APPLICATION_JSON })
	Response matching(@HeaderParam("aid") String aid,
			@HeaderParam("session") String adminSession,
			@HeaderParam("isFinished") Boolean isFinished);

	@Path("students")
	@POST
	@Produces({ MediaType.APPLICATION_JSON })
	Response getAllStudents(@HeaderParam("aid") String aid,
			@HeaderParam("session") String adminSession);

	@Path("students/add")
	@POST
	@Produces({ MediaType.APPLICATION_JSON })
	Response addStudent(@HeaderParam("aid") String aid,
			@HeaderParam("session") String adminSession,
			@Context HttpHeaders headers);

	@Path("student/delete")
	@POST
	@Produces({ MediaType.APPLICATION_JSON })
	Response deleteStudent(@HeaderParam("aid") String aid,
			@HeaderParam("sid") String sid,
			@HeaderParam("session") String adminSession);
	
	@Path("professors")
	@POST
	@Produces({ MediaType.APPLICATION_JSON })
	Response getAllProfessors(@HeaderParam("aid") String aid,
			@HeaderParam("session") String adminSession);

	@Path("professors/add")
	@POST
	@Produces({ MediaType.APPLICATION_JSON })
	Response addProfessor(@HeaderParam("aid") String aid,
			@HeaderParam("session") String adminSession,
			@Context HttpHeaders headers);

	@Path("professors/delete")
	@POST
	@Produces({ MediaType.APPLICATION_JSON })
	Response deleteProfessor(@HeaderParam("aid") String aid,
			@HeaderParam("pid") String pid,
			@HeaderParam("session") String adminSession);
}
