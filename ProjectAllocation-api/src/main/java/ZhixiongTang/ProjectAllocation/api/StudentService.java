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

@Path("StudentService")
public interface StudentService {
	@Path("information/{sid}")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	Response getInformationFromSID(@PathParam("sid") String sid);

	@Path("information/{sid}")
	@POST
	@Produces({ MediaType.APPLICATION_JSON })
	Response getPostInformationFromSID(@PathParam("sid") String sid,
			@HeaderParam("session") String studentSession);

	@Path("preferenceList/{sid}")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	Response getPreferenceListFromSID(@PathParam("sid") String sid);

	@Path("login")
	@POST
	@Produces({ MediaType.APPLICATION_JSON })
	Response loginStudent(@HeaderParam("sid") String sid,
			@HeaderParam("password") String password);

	@Path("update/{sid}")
	@POST
	@Produces({ MediaType.APPLICATION_JSON })
	Response updateStudents(@PathParam("sid") String sid,
			@Context HttpHeaders headers, @HeaderParam("session") String session);

	@Path("logout")
	@POST
	@Produces({ MediaType.APPLICATION_JSON })
	Response logoutStudent(@HeaderParam("sid") String sid,
			@HeaderParam("session") String studentSession);

	@Path("delete")
	@POST
	@Produces({ MediaType.APPLICATION_JSON })
	Response deletePreferProfessor(@HeaderParam("sid") String sid,
			@HeaderParam("pid") String pid,
			@HeaderParam("session") String studentSession);

	@Path("add")
	@POST
	@Produces({ MediaType.APPLICATION_JSON })
	Response addPreferProfessor(@HeaderParam("sid") String sid,
			@HeaderParam("pid") String pid,
			@HeaderParam("session") String studentSession);

	@Path("swap")
	@POST
	@Produces({ MediaType.APPLICATION_JSON })
	Response swapPreferProfessor(@HeaderParam("sid") String sid,
			@HeaderParam("pid1") String pid1, @HeaderParam("pid2") String pid2,
			@HeaderParam("session") String studentSession);

	@Path("result/{sid}")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	Response getResultFromSID(@PathParam("sid") String sid);
}
