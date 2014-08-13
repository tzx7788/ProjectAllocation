package ZhixiongTang.ProjectAllocation.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("StudentService")
public interface StudentService {
    @Path( "information/{sid}" )
    @GET
    @Produces( { MediaType.APPLICATION_JSON } )
    Response getInformationFromSID( @PathParam( "sid" ) String sid );
    
    @Path( "preferenceList/{sid}")
    @GET
    @Produces( { MediaType.APPLICATION_JSON } )
    String getPreferenceListFromSID( @PathParam( "sid" ) String sid );
}
