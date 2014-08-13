 package ZhixiongTang.ProjectAllocation.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("DatabaseService")
public interface DatabaseService {
    @Path( "clear" )
    @GET
    @Produces( { MediaType.APPLICATION_JSON } )
    Response clearDatabase();
}
