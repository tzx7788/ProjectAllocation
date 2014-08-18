package ZhixiongTang.ProjectAllocation.api.impl;

import java.util.List;
import java.util.Set;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.ProjectAllocation.model.Admin;
import org.ProjectAllocation.model.Professor;
import org.ProjectAllocation.model.SessionGenerator;
import org.ProjectAllocation.model.Student;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import ZhixiongTang.ProjectAllocation.api.AdminService;
import ZhixiongTang.ProjectAllocation.api.exception.AdminException;
import ZhixiongTang.ProjectAllocation.api.exception.AuthException;
import ZhixiongTang.ProjectAllocation.api.exception.DatabaseException;
import ZhixiongTang.ProjectAllocation.api.exception.StudentException;

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
		SessionFactory sf = null;
		Session session = null;
		Transaction tx = null;
		try {
			sf = new Configuration().configure().buildSessionFactory();
			session = sf.openSession();
			tx = session.beginTransaction();
			String hql = "from Admin where AID=:aid";
			Query query = session.createQuery(hql);
			query.setString("aid", aid);
			@SuppressWarnings("unchecked")
			List<Admin> list = query.list();
			if (list.size() == 0)
				throw new AdminException("No Admin found!");
			return list.get(0);
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage());
		} finally {
			if (tx != null)
				tx.commit();
			if (session != null)
				session.close();
		}
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
		if (aid == null)
			throw new AdminException("No Admin ID input");
		SessionFactory sf = null;
		Session session = null;
		Transaction tx = null;
		try {
			sf = new Configuration().configure().buildSessionFactory();
			session = sf.openSession();
			tx = session.beginTransaction();
			String hql = "from Admin where AID=:aid";
			Query query = session.createQuery(hql);
			query.setString("aid", aid);
			@SuppressWarnings("unchecked")
			List<Admin> list = query.list();
			if (list.size() == 0)
				throw new AdminException("No Admin found!");
			Admin a = list.get(0);
			if (!a.getPassword().equals(password))
				throw new AdminException("Wrong password!");
			a.setSession(SessionGenerator.generateSession());
			session.save(a);
			return a;
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage());
		} finally {
			if (tx != null)
				tx.commit();
			if (session != null)
				session.close();
		}

	}

	public Response logoutAdmin(String aid, String adminSession) {
		try {
			this.authorization(aid, adminSession);
			this.logout(aid);
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
		} catch (AuthException e) {
			Error error = new Error(e.getMessage());
			State state = new State(error);
			ResponseBuilder builder = Response.ok(state);
			builder.entity(state.toString());
			return builder.build();
		}
	}

	public void logout(String aid) throws AdminException,
			DatabaseException {
		SessionFactory sf = null;
		Session session = null;
		Transaction tx = null;
		try {
			sf = new Configuration().configure().buildSessionFactory();
			session = sf.openSession();
			tx = session.beginTransaction();
			String hql = "from Admin where AID=:aid";
			Query query = session.createQuery(hql);
			query.setString("aid", aid);
			@SuppressWarnings("unchecked")
			List<Admin> list = query.list();
			if (list.size() == 0)
				throw new AdminException("No Admin found!");
			Admin a = list.get(0);
			a.setSession("");
			session.save(a);
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage());
		} finally {
			if (tx != null)
				tx.commit();
			if (session != null)
				session.close();
		}
	}

	public Response matching(String aid, String adminSession, Boolean isFinished) {
		try {
			this.authorization(aid, adminSession);
			JSONObject result = this.matchingStart(isFinished);
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

	public JSONObject matchingStart(Boolean isFinished) throws AdminException,
			DatabaseException, AuthException {
		JSONObject result = new JSONObject();
		return result;
	}

	public Response getAllStudents(String aid, String adminSession) {
		try {
			this.authorization(aid, adminSession);
			JSONArray array = new JSONArray();
			Set<Student> students = this.getAllStudents();
			for (Student student : students)
				array.put(student.toJSONObject());
			State state = new State(array);
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

	public Set<Student> getAllStudents() {
		return null;
	}

	public Response addStudent(String aid, String adminSession,
			HttpHeaders headers) {
		try {
			this.authorization(aid, adminSession);
			State state = new State(this
					.addStudent(headers.getRequestHeaders()).toJSONObject());
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

	public Student addStudent(MultivaluedMap<String, String> data) {
		return null;
	}

	public Response deleteStudent(String aid, String sid, String adminSession) {
		try {
			this.authorization(aid, adminSession);
			this.deleteStudent(sid);
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
		} catch (AuthException e) {
			Error error = new Error(e.getMessage());
			State state = new State(error);
			ResponseBuilder builder = Response.ok(state);
			builder.entity(state.toString());
			return builder.build();
		}
	}

	public void deleteStudent(String sid) {
	}

	public Response getAllProfessors(String aid, String adminSession) {
		try {
			this.authorization(aid, adminSession);
			JSONArray array = new JSONArray();
			Set<Professor> professors = this.getAllProfessors();
			for (Professor professor : professors)
				array.put(professor.toJSONObject());
			State state = new State(array);
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

	public Set<Professor> getAllProfessors() {
		return null;
	}

	public Response addProfessor(String aid, String adminSession,
			HttpHeaders headers) {
		try {
			this.authorization(aid, adminSession);
			State state = new State(this.addProfessor(
					headers.getRequestHeaders()).toJSONObject());
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

	public Student addProfessor(MultivaluedMap<String, String> data) {
		return null;
	}

	public Response deleteProfessor(String aid, String pid, String adminSession) {
		try {
			this.authorization(aid, adminSession);
			this.deleteProfessor(pid);
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
		} catch (AuthException e) {
			Error error = new Error(e.getMessage());
			State state = new State(error);
			ResponseBuilder builder = Response.ok(state);
			builder.entity(state.toString());
			return builder.build();
		}
	}

	public void deleteProfessor(String pid) {

	}

	public Admin authorization(String aid, String adminSession)
			throws AuthException, AdminException, DatabaseException {
		Admin a = this.findAdmin(aid);
		if (a.getSession() == null)
			throw new AuthException("Invalid session");
		if (a.getSession().length() < 3)
			throw new AuthException("Invalid session");
		if (!a.getSession().equals(adminSession))
			throw new AuthException("Invalid session");
		return a;
	}
}
