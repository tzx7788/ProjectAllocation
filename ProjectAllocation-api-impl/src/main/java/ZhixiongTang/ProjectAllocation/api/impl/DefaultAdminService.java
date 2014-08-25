package ZhixiongTang.ProjectAllocation.api.impl;

import java.util.ArrayList;
import java.util.HashSet;
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

	public void logout(String aid) throws AdminException, DatabaseException {
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

	public Response matchingBegin(String aid, String adminSession) {
		try {
			this.authorization(aid, adminSession);
			JSONObject result = this.matchingStart();
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

	public Response matchingEnd(String aid, String adminSession) {
		try {
			this.authorization(aid, adminSession);
			JSONObject result = this.matchingFinish();
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

	public Response matching(String aid, String adminSession, Boolean isFinished) {
		try {
			this.authorization(aid, adminSession);
			JSONObject result = this.matchingOneStep(isFinished);
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

	private static Algorithm algorithm;

	public JSONObject matchingStart() throws AdminException, DatabaseException {
		SessionFactory sf = null;
		Session session = null;
		Transaction tx = null;
		try {
			sf = new Configuration().configure().buildSessionFactory();
			session = sf.openSession();
			tx = session.beginTransaction();
			JSONObject result = new JSONObject();
			String hql = "from Student";
			Query query = session.createQuery(hql);
			@SuppressWarnings("unchecked")
			List<Student> students = new ArrayList<Student>(query.list());
			hql = "from Professor";
			query = session.createQuery(hql);
			@SuppressWarnings("unchecked")
			List<Professor> professors = new ArrayList<Professor>(query.list());
			algorithm = new Algorithm(students, professors);
			algorithm.initialization();
			algorithm.firstStep();
			algorithm.save();
			for (Student student : students)
				session.save(student);
			for (Professor professor : professors)
				session.save(professor);
			result.put("result", algorithm.toJSONArray());
			result.put("isFinished", false);
			return result;
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage());
		} finally {
			if (tx != null)
				tx.commit();
			if (session != null)
				session.close();
		}
	}

	public JSONObject matchingFinish() throws AdminException, DatabaseException {
		SessionFactory sf = null;
		Session session = null;
		Transaction tx = null;
		try {
			sf = new Configuration().configure().buildSessionFactory();
			session = sf.openSession();
			tx = session.beginTransaction();
			JSONObject result = new JSONObject();
			String hql = "from Student";
			Query query = session.createQuery(hql);
			@SuppressWarnings("unchecked")
			List<Student> students = new ArrayList<Student>(query.list());
			hql = "from Professor";
			query = session.createQuery(hql);
			@SuppressWarnings("unchecked")
			List<Professor> professors = new ArrayList<Professor>(query.list());
			algorithm = new Algorithm(students, professors);
			algorithm.initialization();
			algorithm.firstStep();
			algorithm.finish();
			algorithm.save();
			for (Student student : students)
				session.save(student);
			for (Professor professor : professors)
				session.save(professor);
			result.put("result", algorithm.toJSONArray());
			result.put("isFinished", true);
			algorithm = null;
			return result;
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage());
		} finally {
			if (tx != null)
				tx.commit();
			if (session != null)
				session.close();
		}
	}

	public JSONObject matchingOneStep(Boolean isFinished)
			throws AdminException, DatabaseException {
		SessionFactory sf = null;
		Session session = null;
		Transaction tx = null;
		try {
			sf = new Configuration().configure().buildSessionFactory();
			session = sf.openSession();
			tx = session.beginTransaction();
			JSONObject result = new JSONObject();
			String hql = "from Student";
			Query query = session.createQuery(hql);
			@SuppressWarnings("unchecked")
			List<Student> students = new ArrayList<Student>(query.list());
			hql = "from Professor";
			query = session.createQuery(hql);
			@SuppressWarnings("unchecked")
			List<Professor> professors = new ArrayList<Professor>(query.list());
			if (algorithm == null) {
				algorithm = new Algorithm(students, professors);
				algorithm.initialization();
				algorithm.firstStep();
			}
			algorithm.load(students, professors);
			algorithm.initialization();
			if (!isFinished)
				isFinished = algorithm.oneStep();
			algorithm.save();
			for (Student student : algorithm.getStudents())
				session.save(student);
			for (Professor professor : algorithm.getProfessors())
				session.save(professor);
			result.put("result", algorithm.toJSONArray());
			result.put("isFinished", isFinished);
			if (isFinished)
				algorithm = null;
			return result;
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage());
		} finally {
			if (tx != null)
				tx.commit();
			if (session != null)
				session.close();
		}
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

	public Set<Student> getAllStudents() throws DatabaseException {
		SessionFactory sf = null;
		Session session = null;
		Transaction tx = null;
		try {
			sf = new Configuration().configure().buildSessionFactory();
			session = sf.openSession();
			tx = session.beginTransaction();
			String hql = "from Student";
			Query query = session.createQuery(hql);
			@SuppressWarnings("unchecked")
			List<Student> list = query.list();
			HashSet<Student> result = new HashSet<Student>();
			for (Student student : list)
				result.add(student);
			return result;
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage());
		} finally {
			if (tx != null)
				tx.commit();
			if (session != null)
				session.close();
		}
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

	public Student addStudent(MultivaluedMap<String, String> data)
			throws AdminException, DatabaseException {
		SessionFactory sf = null;
		Session session = null;
		Transaction tx = null;
		try {
			sf = new Configuration().configure().buildSessionFactory();
			session = sf.openSession();
			tx = session.beginTransaction();
			String sid = null;
			String name = null;
			String password = null;
			if (data.containsKey("sid"))
				sid = data.getFirst("sid");
			else
				throw new AdminException("No ID input");
			if (data.containsKey("name"))
				name = data.getFirst("name");
			else
				throw new AdminException("No name input");
			if (data.containsKey("password"))
				name = data.getFirst("password");
			else
				throw new AdminException("No password input");
			String hql = "from Student where SID=:sid";
			Query query = session.createQuery(hql);
			query.setString("sid", sid);
			@SuppressWarnings("unchecked")
			List<Student> list = query.list();
			Student student = null;
			if (list.size() == 0)
				student = new Student(sid, name);
			else
				student = list.get(0);
			student.setName(name);
			student.setPassword(password);
			session.save(student);
			return student;
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage());
		} finally {
			if (tx != null)
				tx.commit();
			if (session != null)
				session.close();
		}
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

	public void deleteStudent(String sid) throws AdminException,
			DatabaseException {
		SessionFactory sf = null;
		Session session = null;
		Transaction tx = null;
		try {
			sf = new Configuration().configure().buildSessionFactory();
			session = sf.openSession();
			tx = session.beginTransaction();
			String hql = "from Student where SID=:sid";
			Query query = session.createQuery(hql);
			if (sid == null)
				throw new AdminException("No student ID input");
			query.setString("sid", sid);
			@SuppressWarnings("unchecked")
			List<Student> list = query.list();
			if (list.size() == 0)
				throw new AdminException("No student found!");
			session.delete(list.get(0));
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage());
		} finally {
			if (tx != null)
				tx.commit();
			if (session != null)
				session.close();
		}
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

	public Set<Professor> getAllProfessors() throws DatabaseException {
		SessionFactory sf = null;
		Session session = null;
		Transaction tx = null;
		try {
			sf = new Configuration().configure().buildSessionFactory();
			session = sf.openSession();
			tx = session.beginTransaction();
			String hql = "from Professor";
			Query query = session.createQuery(hql);
			@SuppressWarnings("unchecked")
			List<Professor> list = query.list();
			HashSet<Professor> result = new HashSet<Professor>();
			for (Professor professor : list)
				result.add(professor);
			return result;
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage());
		} finally {
			if (tx != null)
				tx.commit();
			if (session != null)
				session.close();
		}
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

	public Professor addProfessor(MultivaluedMap<String, String> data)
			throws AdminException, DatabaseException {
		SessionFactory sf = null;
		Session session = null;
		Transaction tx = null;
		try {
			sf = new Configuration().configure().buildSessionFactory();
			session = sf.openSession();
			tx = session.beginTransaction();
			String pid = null;
			String name = null;
			String password = null;
			Integer limit = 1;
			if (data.containsKey("pid"))
				pid = data.getFirst("pid");
			else
				throw new AdminException("No ID input");
			if (data.containsKey("name"))
				name = data.getFirst("name");
			else
				throw new AdminException("No name input");
			if (data.containsKey("password"))
				name = data.getFirst("password");
			else
				throw new AdminException("No password input");
			if (data.containsKey("limit"))
				limit = Integer.parseInt(data.getFirst("limit"));
			String hql = "from Professor where PID=:pid";
			Query query = session.createQuery(hql);
			query.setString("pid", pid);
			@SuppressWarnings("unchecked")
			List<Professor> list = query.list();
			Professor professor = null;
			if (list.size() == 0)
				professor = new Professor(pid, name);
			else
				professor = list.get(0);
			professor.setName(name);
			professor.setPassword(password);
			professor.setLimit(limit);
			session.save(professor);
			return professor;
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage());
		} finally {
			if (tx != null)
				tx.commit();
			if (session != null)
				session.close();
		}
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

	public void deleteProfessor(String pid) throws AdminException,
			DatabaseException {
		SessionFactory sf = null;
		Session session = null;
		Transaction tx = null;
		try {
			sf = new Configuration().configure().buildSessionFactory();
			session = sf.openSession();
			tx = session.beginTransaction();
			String hql = "from Professor where PID=:pid";
			Query query = session.createQuery(hql);
			if (pid == null)
				throw new AdminException("No Professor ID input");
			query.setString("pid", pid);
			@SuppressWarnings("unchecked")
			List<Professor> list = query.list();
			if (list.size() == 0)
				throw new AdminException("No Professor found!");
			session.delete(list.get(0));
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage());
		} finally {
			if (tx != null)
				tx.commit();
			if (session != null)
				session.close();
		}
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

	public Response getInformationFromAid(String aid, String adminSession) {
		try {
			this.authorization(aid, adminSession);
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
		} catch (AuthException e) {
			Error error = new Error(e.getMessage());
			State state = new State(error);
			ResponseBuilder builder = Response.ok(state);
			builder.entity(state.toString());
			return builder.build();
		}
	}

	public Response getAllStudentsWithGET() {
		try {
			JSONArray array = new JSONArray();
			Set<Student> students = this.getAllStudents();
			for (Student student : students)
				array.put(student.toJSONObject());
			State state = new State(array);
			ResponseBuilder builder = Response.ok(state);
			builder.entity(state.toString());
			return builder.build();
		}  catch (DatabaseException e) {
			Error error = new Error(e.getMessage());
			State state = new State(error);
			ResponseBuilder builder = Response.ok(state);
			builder.entity(state.toString());
			return builder.build();
		} 
	}

	public Response getAllProfessorsWithGET() {
		try {
			JSONArray array = new JSONArray();
			Set<Professor> professors = this.getAllProfessors();
			for (Professor professor : professors)
				array.put(professor.toJSONObject());
			State state = new State(array);
			ResponseBuilder builder = Response.ok(state);
			builder.entity(state.toString());
			return builder.build();
		}  catch (DatabaseException e) {
			Error error = new Error(e.getMessage());
			State state = new State(error);
			ResponseBuilder builder = Response.ok(state);
			builder.entity(state.toString());
			return builder.build();
		} 
	}

}
