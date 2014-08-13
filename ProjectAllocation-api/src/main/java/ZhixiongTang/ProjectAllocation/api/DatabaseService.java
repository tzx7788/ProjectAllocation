 package ZhixiongTang.ProjectAllocation.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("DatabaseService")
public interface DatabaseService {
    @Path( "loadTestData" )
    @POST
    @Consumes( { MediaType.APPLICATION_JSON } )
    public Response getInformationFromSID();
}
