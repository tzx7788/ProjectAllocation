package ZhixiongTang.ProjectAllocation.api;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.ProjectAllocation.model.Professor;
import org.ProjectAllocation.model.Student;

import ZhixiongTang.ProjectAllocation.api.exception.AuthException;
import ZhixiongTang.ProjectAllocation.api.exception.DatabaseException;
import ZhixiongTang.ProjectAllocation.api.exception.StudentException;

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

	List<Professor> delete(String sid, String pid, String studentSession)
			throws StudentException, DatabaseException, AuthException;

	Student update(String sid, MultivaluedMap<String, String> data,
			String studentSession) throws StudentException, DatabaseException,
			AuthException;

	void authorization(Student s, String session) throws AuthException;

	Student login(String sid, String password) throws StudentException,
			DatabaseException;

	void logout(String sid, String studentSession) throws StudentException,
			DatabaseException;

	Student findStudent(String sid) throws StudentException, DatabaseException;

	List<Professor> findPreferProfessorsList(String sid)
			throws StudentException, DatabaseException;

	List<Professor> add(String sid, String pid, String studentSession)
			throws StudentException, DatabaseException, AuthException;

	List<Professor> swap(String sid, String pid1, String pid2,
			String studentSession) throws StudentException, DatabaseException,
			AuthException;
}
