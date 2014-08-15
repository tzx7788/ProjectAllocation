 package ZhixiongTang.ProjectAllocation.api;

import javax.ws.rs.DELETE;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("DatabaseService")
public interface DatabaseService {
    @Path( "clear" )
    @DELETE
    @Produces( { MediaType.APPLICATION_JSON } )
    Response clearDatabase( @HeaderParam("authentification") String authentification );
    
}
