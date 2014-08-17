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
import ZhixiongTang.ProjectAllocation.api.exception.ProfessorException;

@Path("ProfessorService")
public interface ProfessorService {
	@Path("information/{pid}")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	Response getInformationFromPID(@PathParam("pid") String pid);

	@Path("preferenceList/{pid}")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	Response getPreferenceListFromPID(@PathParam("pid") String pid);

	@Path("login")
	@POST
	@Produces({ MediaType.APPLICATION_JSON })
	Response loginProfessor(@HeaderParam("pid") String pid,
			@HeaderParam("password") String password);

	@Path("update/{pid}")
	@POST
	@Produces({ MediaType.APPLICATION_JSON })
	Response updateProfessor(@PathParam("pid") String pid,
			@Context HttpHeaders headers, @HeaderParam("session") String session);

	@Path("logout")
	@POST
	@Produces({ MediaType.APPLICATION_JSON })
	Response logoutProfessor(@HeaderParam("pid") String pid,
			@HeaderParam("session") String professorSession);

	@Path("delete")
	@POST
	@Produces({ MediaType.APPLICATION_JSON })
	Response deletePreferStudent(@HeaderParam("pid") String pid,
			@HeaderParam("sid") String sid,
			@HeaderParam("session") String professorSession);

	@Path("add")
	@POST
	@Produces({ MediaType.APPLICATION_JSON })
	Response addPreferStudent(@HeaderParam("pid") String pid,
			@HeaderParam("sid") String sid,
			@HeaderParam("session") String professorSession);

	@Path("swap")
	@POST
	@Produces({ MediaType.APPLICATION_JSON })
	Response swapPreferStudent(@HeaderParam("pid") String pid,
			@HeaderParam("sid1") String sid1, @HeaderParam("sid2") String sid2,
			@HeaderParam("session") String professorSession);

	List<Student> delete(String pid, String sid, String professorSession)
			throws ProfessorException, DatabaseException, AuthException;

	Student update(String pid, MultivaluedMap<String, String> data,
			String professorSession) throws ProfessorException, DatabaseException,
			AuthException;

	void authorization(Professor p, String session) throws AuthException;

	Student login(String pid, String password) throws ProfessorException,
			DatabaseException;

	void logout(String pid, String studentSession) throws ProfessorException,
			DatabaseException;

	Professor findProfessor(String pid) throws ProfessorException, DatabaseException;

	List<Student> findPreferStudentsList(String pid)
			throws ProfessorException, DatabaseException;

	List<Student> add(String pid, String sid, String professorSession)
			throws ProfessorException, DatabaseException, AuthException;

	List<Student> swap(String pid, String sid1, String sid2,
			String studentSession) throws ProfessorException, DatabaseException,
			AuthException;
}
