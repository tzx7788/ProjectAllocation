package ZhixiongTang.ProjectAllocation.api;

import javax.ws.rs.DELETE;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("DatabaseService")
public interface DatabaseService {
	@Path("clear")
	@DELETE
	@Produces({ MediaType.APPLICATION_JSON })
	Response clearDatabase(
			@HeaderParam("authentification") String authentification);

	@Path("loadTestData/{version}")
	@POST
	@Produces({ MediaType.APPLICATION_JSON })
	Response loadTestData(
			@HeaderParam("authentification") String authentification,
			@PathParam("version") Integer version);
}
