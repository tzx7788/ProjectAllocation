package ZhixiongTang.ProjectAllocation.api;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("StudentService")
public interface StudentService {
	@Path("information/{sid}")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	Response getInformationFromSID(@PathParam("sid") String sid);

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
			@HeaderParam("data_name") String name,
			@HeaderParam("data_password") String password,
			@HeaderParam("session") String session);
}
