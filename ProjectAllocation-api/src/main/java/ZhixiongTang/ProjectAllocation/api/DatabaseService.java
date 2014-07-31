package ZhixiongTang.ProjectAllocation.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("DatabaseService")
public interface DatabaseService {
    @Path( "information/{sid}" )
    @GET
    @Produces( { MediaType.APPLICATION_JSON } )
    String getInformationFromSID( @PathParam( "sid" ) String sid );
}
