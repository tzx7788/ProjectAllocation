package ZhixiongTang.ProjectAllocation.api.impl;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.ProjectAllocation.model.Admin;
import org.ProjectAllocation.model.Student;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import ZhixiongTang.ProjectAllocation.api.AdminService;
import ZhixiongTang.ProjectAllocation.api.exception.AdminException;
import ZhixiongTang.ProjectAllocation.api.exception.AuthException;
import ZhixiongTang.ProjectAllocation.api.exception.DatabaseException;

@Service("adminService#default")
public class DefaultAdminService implements AdminService {

	public Response getInformationFromAid(String aid) {
		try {
			Admin a = this.findAdmin(aid);
			State state = new State(a);
			ResponseBuilder builder = Response.ok(state);
			builder.entity(state.toString());
			return builder.build();
		} catch (AdminException e) {
			Error error = new Error(e.getMessage());
			State state = new State(error);
			ResponseBuilder builder = Response.ok(state);
			builder.entity(state.toString());
			return builder.build();
		} catch (DatabaseException e) {
			Error error = new Error(e.getMessage());
			State state = new State(error);
			ResponseBuilder builder = Response.ok(state);
			builder.entity(state.toString());
			return builder.build();
		}
	}

	public Admin findAdmin(String aid) throws AdminException, DatabaseException {
		return null;
	}

	public Response loginAdmin(String aid, String password) {
		try {
			Admin a = this.login(aid, password);
			State state = new State(a);
			state.put("session", a.getSession());
			ResponseBuilder builder = Response.ok(state);
			builder.entity(state.toString());
			return builder.build();
		} catch (AdminException e) {
			Error error = new Error(e.getMessage());
			State state = new State(error);
			ResponseBuilder builder = Response.ok(state);
			builder.entity(state.toString());
			return builder.build();
		} catch (DatabaseException e) {
			Error error = new Error(e.getMessage());
			State state = new State(error);
			ResponseBuilder builder = Response.ok(state);
			builder.entity(state.toString());
			return builder.build();
		}
	}

	public Admin login(String aid, String password) throws AdminException,
			DatabaseException {
		return null;
	}

	public Response logoutStudent(String aid, String adminSession) {
		try {
			this.logout(aid, adminSession);
			State state = new State();
			ResponseBuilder builder = Response.ok(state);
			builder.entity(state.toString());
			return builder.build();
		} catch (AdminException e) {
			Error error = new Error(e.getMessage());
			State state = new State(error);
			ResponseBuilder builder = Response.ok(state);
			builder.entity(state.toString());
			return builder.build();
		} catch (DatabaseException e) {
			Error error = new Error(e.getMessage());
			State state = new State(error);
			ResponseBuilder builder = Response.ok(state);
			builder.entity(state.toString());
			return builder.build();
		}
	}

	public void logout(String aid, String adminSession) throws AdminException,
			DatabaseException {
	}

	public Response matching(String aid, String studentSession,
			Boolean isFinished) {
		try {
			JSONObject result = this.matchingStart(aid, studentSession, isFinished);
			State state = new State(result);
			ResponseBuilder builder = Response.ok(state);
			builder.entity(state.toString());
			return builder.build();
		} catch (AdminException e) {
			Error error = new Error(e.getMessage());
			State state = new State(error);
			ResponseBuilder builder = Response.ok(state);
			builder.entity(state.toString());
			return builder.build();
		} catch (DatabaseException e) {
			Error error = new Error(e.getMessage());
			State state = new State(error);
			ResponseBuilder builder = Response.ok(state);
			builder.entity(state.toString());
			return builder.build();
		} catch (AuthException e) {
			Error error = new Error(e.getMessage());
			State state = new State(error);
			ResponseBuilder builder = Response.ok(state);
			builder.entity(state.toString());
			return builder.build();
		}
	}

	public JSONObject matchingStart(String aid, String studentSession,
			Boolean isFinished) throws AdminException, DatabaseException,
			AuthException {
		JSONObject result = new JSONObject();
		return result;
	}

	public Response getAllStudents(String aid, String adminSession) {
		// TODO Auto-generated method stub
		return null;
	}

	public Response addStudent(String aid, String adminSession,
			HttpHeaders headers) {
		// TODO Auto-generated method stub
		return null;
	}

	public Response deleteStudent(String aid, String sid, String adminSession) {
		// TODO Auto-generated method stub
		return null;
	}

	public Response getAllProfessors(String aid, String adminSession) {
		// TODO Auto-generated method stub
		return null;
	}

	public Response addProfessor(String aid, String adminSession,
			HttpHeaders headers) {
		// TODO Auto-generated method stub
		return null;
	}

	public Response deleteProfessor(String aid, String pid, String adminSession) {
		// TODO Auto-generated method stub
		return null;
	}

	public void authorization(Admin a, String session) throws AuthException {
		if (a.getSession() == null)
			throw new AuthException("Invalid session");
		if (a.getSession().length() < 3)
			throw new AuthException("Invalid session");
		if (!a.getSession().equals(session))
			throw new AuthException("Invalid session");
	}
}
