package ZhixiongTang.ProjectAllocation.api.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.ProjectAllocation.model.Professor;
import org.ProjectAllocation.model.ProfessorPreferenceItem;
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

import ZhixiongTang.ProjectAllocation.api.ProfessorService;
import ZhixiongTang.ProjectAllocation.api.exception.AuthException;
import ZhixiongTang.ProjectAllocation.api.exception.DatabaseException;
import ZhixiongTang.ProjectAllocation.api.exception.ProfessorException;

@Service("professorService#default")
public class DefaultProfessorService implements ProfessorService {

	public Response getInformationFromPID(String pid) {
		try {
			JSONObject object = this.findProfessor(pid);
			State state = new State(object);
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
		try {
			Professor p = this
					.update(pid, headers.getRequestHeaders(), session);
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
		} catch (AuthException e) {
			Error error = new Error(e.getMessage());
			State state = new State(error);
			ResponseBuilder builder = Response.ok(state);
			builder.entity(state.toString());
			return builder.build();
		}
	}

	public Response logoutProfessor(String pid, String professorSession) {
		try {
			this.logout(pid, professorSession);
			State state = new State();
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

	public Response deletePreferStudent(String pid, String sid,
			String professorSession) {
		try {
			List<Student> list = this.delete(pid, sid, professorSession);
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
		} catch (AuthException e) {
			Error error = new Error(e.getMessage());
			State state = new State(error);
			ResponseBuilder builder = Response.ok(state);
			builder.entity(state.toString());
			return builder.build();
		}
	}

	public Response addPreferStudent(String pid, String sid,
			String professorSession) {
		try {
			List<Student> list = this.add(pid, sid, professorSession);
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
		} catch (AuthException e) {
			Error error = new Error(e.getMessage());
			State state = new State(error);
			ResponseBuilder builder = Response.ok(state);
			builder.entity(state.toString());
			return builder.build();
		}
	}

	public Response swapPreferStudent(String pid, String sid1, String sid2,
			String professorSession) {
		try {
			List<Student> list = this.swap(pid, sid1, sid2, professorSession);
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
		} catch (AuthException e) {
			Error error = new Error(e.getMessage());
			State state = new State(error);
			ResponseBuilder builder = Response.ok(state);
			builder.entity(state.toString());
			return builder.build();
		}
	}

	public List<Student> delete(String pid, String sid, String professorSession)
			throws ProfessorException, DatabaseException, AuthException {
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
			this.authorization(list.get(0), professorSession);
			List<ProfessorPreferenceItem> preferList = list.get(0)
					.getPreferList();
			for (ProfessorPreferenceItem item : preferList) {
				if (item.getStudent().getSid().equals(sid)) {
					preferList.remove(item);
					session.delete(item);
					item.getStudent().getLikedBy().remove(item);
					List<Student> result = list.get(0).preferStudentsList();
					return result;
				}
			}
			throw new ProfessorException("No student found!");
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage());
		} finally {
			if (tx != null)
				tx.commit();
			if (session != null)
				session.close();
		}
	}

	public Professor update(String pid, MultivaluedMap<String, String> data,
			String professorSession) throws ProfessorException,
			DatabaseException, AuthException {
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
				throw new ProfessorException("No student found!");
			Professor p = list.get(0);
			this.authorization(p, professorSession);
			if (data.containsKey("data_name")) {
				p.setName(data.get("data_name").get(0));
			}
			if (data.containsKey("data_password")) {
				p.setPassword(data.get("data_password").get(0));
			}
			if (data.containsKey("data_limit")) {
				p.setLimit(Integer.parseInt(data.get("data_limit").get(0)));
			}
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

	public void authorization(Professor p, String session) throws AuthException {
		if (p.getSession() == null)
			throw new AuthException("Invalid session");
		if (p.getSession().length() < 3)
			throw new AuthException("Invalid session");
		if (!p.getSession().equals(session))
			throw new AuthException("Invalid session");
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

	public void logout(String pid, String professorSession)
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
			if (!p.getSession().equals(professorSession))
				throw new ProfessorException("invalid session!");
			p.setSession("");
			session.save(p);
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage());
		} finally {
			if (tx != null)
				tx.commit();
			if (session != null)
				session.close();
		}

	}

	public JSONObject findProfessor(String pid) throws ProfessorException,
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
			list.get(0).preferStudentsList();
			JSONObject result = list.get(0).toJSONObjectWithSession();
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
			this.authorization(list.get(0), professorSession);
			Professor professor = list.get(0);
			hql = "from Student where SID=:sid";
			query = session.createQuery(hql);
			query.setString("sid", sid);
			if (query.list().size() == 0)
				throw new ProfessorException("No student found!");
			Student student = (Student) query.list().get(0);
			if (professor.preferStudentsList().contains(student))
				throw new ProfessorException("student has already been added");
			int weight = 0;
			if (professor.getPreferList().size() > 0)
				weight = professor.getPreferList()
						.get(professor.getPreferList().size() - 1).getWeight() + 1;
			ProfessorPreferenceItem item = new ProfessorPreferenceItem(
					professor, student, weight);
			professor.getPreferList().add(item);
			session.save(professor);
			return professor.preferStudentsList();
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage());
		} finally {
			if (tx != null)
				tx.commit();
			if (session != null)
				session.close();
		}
	}

	public List<Student> swap(String pid, String sid1, String sid2,
			String professorSession) throws ProfessorException,
			DatabaseException, AuthException {
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
			this.authorization(list.get(0), professorSession);
			Professor professor = list.get(0);
			hql = "from Student where SID=:sid1";
			query = session.createQuery(hql);
			query.setString("sid1", sid1);
			if (query.list().size() == 0)
				throw new ProfessorException("No student:" + sid1 + " found!");
			Student student1 = (Student) query.list().get(0);
			if (!professor.preferStudentsList().contains(student1))
				throw new ProfessorException("student:" + sid1
						+ " has not been added");
			hql = "from Student where SID=:sid2";
			query = session.createQuery(hql);
			query.setString("sid2", sid2);
			if (query.list().size() == 0)
				throw new ProfessorException("No student:" + sid2 + " found!");
			Student student2 = (Student) query.list().get(0);
			if (!professor.preferStudentsList().contains(student2))
				throw new ProfessorException("student:" + sid2
						+ " has not been added");
			professor.swap(student1, student2);
			List<Student> result = professor.preferStudentsList();
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

	public Response getResultFromPID(String pid) {
		try {
			Set<Student> set = this.findResults(pid);
			JSONArray result = new JSONArray();
			for (Student student : set) {
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

	public Set<Student> findResults(String pid)
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
			Set<Student> result = new HashSet<Student>();
			for ( Student s : list.get(0).getResult() )
				result.add(s);
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

}
