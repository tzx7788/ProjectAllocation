package ZhixiongTang.ProjectAllocation.api.impl;

import java.util.List;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

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
import org.springframework.stereotype.Service;

import ZhixiongTang.ProjectAllocation.api.ProfessorService;
import ZhixiongTang.ProjectAllocation.api.exception.AuthException;
import ZhixiongTang.ProjectAllocation.api.exception.DatabaseException;
import ZhixiongTang.ProjectAllocation.api.exception.ProfessorException;
import ZhixiongTang.ProjectAllocation.api.exception.StudentException;

@Service("professorService#default")
public class DefaultProfessorService implements ProfessorService {

	public Response getInformationFromPID(String pid) {
		try {
			Professor p = this.findProfessor(pid);
			State state = new State(p);
			ResponseBuilder builder = Response.ok(state);
			builder.entity(state.toString());
			return builder.build();
		} catch (ProfessorException e) {
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

	public Response getPreferenceListFromPID(String pid) {
		try {
			List<Student> list = this.findPreferStudentsList(pid);
			JSONArray result = new JSONArray();
			for (Student student : list) {
				result.put(student.toJSONObject());
			}
			State state = new State(result);
			ResponseBuilder builder = Response.ok(state);
			builder.entity(state.toString());
			return builder.build();
		} catch (ProfessorException e) {
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

	public Response loginProfessor(String pid, String password) {
		try {
			Professor p = login(pid, password);
			State state = new State(p);
			state.put("session", p.getSession());
			ResponseBuilder builder = Response.ok(state);
			builder.entity(state.toString());
			return builder.build();
		} catch (ProfessorException e) {
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

	public Response updateProfessor(String pid, HttpHeaders headers,
			String session) {
		// TODO Auto-generated method stub
		return null;
	}

	public Response logoutProfessor(String pid, String professorSession) {
		// TODO Auto-generated method stub
		return null;
	}

	public Response deletePreferStudent(String pid, String sid,
			String professorSession) {
		// TODO Auto-generated method stub
		return null;
	}

	public Response addPreferStudent(String pid, String sid,
			String professorSession) {
		// TODO Auto-generated method stub
		return null;
	}

	public Response swapPreferStudent(String pid, String sid1, String sid2,
			String professorSession) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Student> delete(String pid, String sid, String professorSession)
			throws ProfessorException, DatabaseException, AuthException {
		// TODO Auto-generated method stub
		return null;
	}

	public Student update(String pid, MultivaluedMap<String, String> data,
			String professorSession) throws ProfessorException,
			DatabaseException, AuthException {
		// TODO Auto-generated method stub
		return null;
	}

	public void authorization(Professor p, String session) throws AuthException {
		// TODO Auto-generated method stub
		
	}

	public Professor login(String pid, String password)
			throws ProfessorException, DatabaseException {
		if (pid == null)
			throw new ProfessorException("No professor ID input");
		SessionFactory sf = null;
		Session session = null;
		Transaction tx = null;
		try {
			sf = new Configuration().configure().buildSessionFactory();
			session = sf.openSession();
			tx = session.beginTransaction();
			String hql = "from Professor where PID=:pid";
			Query query = session.createQuery(hql);
			query.setString("pid", pid);
			@SuppressWarnings("unchecked")
			List<Professor> list = query.list();
			if (list.size() == 0)
				throw new ProfessorException("No professor found!");
			Professor p = list.get(0);
			if (!p.getPassword().equals(password))
				throw new ProfessorException("Wrong password!");
			p.setSession(SessionGenerator.generateSession());
			session.save(p);
			return p;
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage());
		} finally {
			if (tx != null)
				tx.commit();
			if (session != null)
				session.close();
		}
	}

	public void logout(String pid, String studentSession)
			throws ProfessorException, DatabaseException {
		// TODO Auto-generated method stub
		
	}

	public Professor findProfessor(String pid) throws ProfessorException,
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
			query.setString("pid", pid);
			@SuppressWarnings("unchecked")
			List<Professor> list = query.list();
			if (list.size() == 0)
				throw new ProfessorException("No professor found!");
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

	public List<Student> findPreferStudentsList(String pid)
			throws ProfessorException, DatabaseException {
		SessionFactory sf = null;
		Session session = null;
		Transaction tx = null;
		try {
			sf = new Configuration().configure().buildSessionFactory();
			session = sf.openSession();
			tx = session.beginTransaction();
			String hql = "from Professor where PID=:pid";
			Query query = session.createQuery(hql);
			query.setString("pid", pid);
			@SuppressWarnings("unchecked")
			List<Professor> list = query.list();
			if (list.size() == 0)
				throw new ProfessorException("No professor found!");
			List<Student> result = list.get(0).preferStudentsList();
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

	public List<Student> add(String pid, String sid, String professorSession)
			throws ProfessorException, DatabaseException, AuthException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Student> swap(String pid, String sid1, String sid2,
			String studentSession) throws ProfessorException,
			DatabaseException, AuthException {
		// TODO Auto-generated method stub
		return null;
	}

}
