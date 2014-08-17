package ZhixiongTang.ProjectAllocation.api.impl;

import java.util.List;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.ProjectAllocation.model.Professor;
import org.ProjectAllocation.model.SessionGenerator;
import org.ProjectAllocation.model.Student;
import org.ProjectAllocation.model.StudentPreferenceItem;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.json.JSONArray;
import org.springframework.stereotype.Service;

import ZhixiongTang.ProjectAllocation.api.AuthException;
import ZhixiongTang.ProjectAllocation.api.DatabaseException;
import ZhixiongTang.ProjectAllocation.api.StudentException;

@Service("studentService#default")
public class DefaultStudentService implements
		ZhixiongTang.ProjectAllocation.api.StudentService {

	public Response getInformationFromSID(String sid) {
		try {
			Student s = this.findStudent(sid);
			State state = new State(s);
			ResponseBuilder builder = Response.ok(state);
			builder.entity(state.toString());
			return builder.build();
		} catch (StudentException e) {
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

	public Response getPreferenceListFromSID(String sid) {
		try {
			List<Professor> list = this.findPreferProfessorsList(sid);
			JSONArray result = new JSONArray();
			for (Professor professor : list) {
				result.put(professor.toJSONObject());
			}
			State state = new State(result);
			ResponseBuilder builder = Response.ok(state);
			builder.entity(state.toString());
			return builder.build();
		} catch (StudentException e) {
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

	public Response loginStudent(String sid, String password) {
		try {
			Student s = login(sid, password);
			State state = new State(s);
			state.put("session", s.getSession());
			ResponseBuilder builder = Response.ok(state);
			builder.entity(state.toString());
			return builder.build();
		} catch (StudentException e) {
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

	public Response logoutStudent(String sid, String studentSession) {
		try {
			logout(sid, studentSession);
			State state = new State();
			ResponseBuilder builder = Response.ok(state);
			builder.entity(state.toString());
			return builder.build();
		} catch (StudentException e) {
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

	public Response updateStudents(String sid, HttpHeaders headers,
			String studentSession) {
		try {
			Student s = this.update(sid, headers.getRequestHeaders(), studentSession);
			State state = new State(s);
			ResponseBuilder builder = Response.ok(state);
			builder.entity(state.toString());
			return builder.build();
		} catch (StudentException e) {
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

	public Response deletePreferProfessor(String sid, String pid,
			String studentSession) {
		try {
			this.delete(sid, pid, studentSession);
			State state = new State();
			ResponseBuilder builder = Response.ok(state);
			builder.entity(state.toString());
			return builder.build();
		} catch (StudentException e) {
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

	public void delete(String sid, String pid, String studentSession)
			throws StudentException, DatabaseException, AuthException {
		SessionFactory sf = null;
		Session session = null;
		Transaction tx = null;
		try {
			sf = new Configuration().configure().buildSessionFactory();
			session = sf.openSession();
			tx = session.beginTransaction();
			String hql = "from Student where SID=:sid";
			Query query = session.createQuery(hql);
			query.setString("sid", sid);
			@SuppressWarnings("unchecked")
			List<Student> list = query.list();
			if (list.size() == 0)
				throw new StudentException("No student found!");
			this.authorization(list.get(0), studentSession);
			List<StudentPreferenceItem> preferList = list.get(0)
					.getPreferList();
			for (StudentPreferenceItem item : preferList) {
				if (item.getProfessor().getPid().equals(pid)) {
					preferList.remove(item);
					session.delete(item);
					item.getProfessor().getLikedBy().remove(item);
					return;
				}
			}
			throw new StudentException("No professor found!");
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage());
		} finally {
			if (tx != null)
				tx.commit();
			if (session != null)
				session.close();
		}
	}

	public Student update(String sid, MultivaluedMap<String,String> data,
			String studentSession) throws StudentException, DatabaseException, AuthException {
		SessionFactory sf = null;
		Session session = null;
		Transaction tx = null;
		try {
			sf = new Configuration().configure().buildSessionFactory();
			session = sf.openSession();
			tx = session.beginTransaction();
			String hql = "from Student where SID=:sid";
			Query query = session.createQuery(hql);
			query.setString("sid", sid);
			@SuppressWarnings("unchecked")
			List<Student> list = query.list();
			if (list.size() == 0)
				throw new StudentException("No student found!");
			Student s = list.get(0);
			this.authorization(s, studentSession);
			if ( data.containsKey("data_name")) {
				s.setName(data.get("data_name").get(0));
			}
			if ( data.containsKey("data_password")) {
				s.setPassword(data.get("data_password").get(0));
			}
			session.save(s);

			return s;
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage());
		} finally {
			if (tx != null)
				tx.commit();
			if (session != null)
				session.close();
		}
	}

	public void authorization(Student s, String session)
			throws AuthException {
		if (s.getSession() == null)
			throw new AuthException("Invalid session");
		if (s.getSession().length() < 3)
			throw new AuthException("Invalid session");
		if (!s.getSession().equals(session))
			throw new AuthException("Invalid session");
	}

	public Student login(String sid, String password) throws StudentException,
			DatabaseException {
		if (sid == null)
			throw new StudentException("No student ID input");
		SessionFactory sf = null;
		Session session = null;
		Transaction tx = null;
		try {
			sf = new Configuration().configure().buildSessionFactory();
			session = sf.openSession();
			tx = session.beginTransaction();
			String hql = "from Student where SID=:sid";
			Query query = session.createQuery(hql);
			query.setString("sid", sid);
			@SuppressWarnings("unchecked")
			List<Student> list = query.list();
			if (list.size() == 0)
				throw new StudentException("No student found!");
			Student s = list.get(0);
			if (!s.getPassword().equals(password))
				throw new StudentException("Wrong password!");
			s.setSession(SessionGenerator.generateSession());
			session.save(s);
			return s;
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage());
		} finally {
			if (tx != null)
				tx.commit();
			if (session != null)
				session.close();
		}
	}

	public void logout(String sid, String studentSession)
			throws StudentException, DatabaseException {
		if (sid == null)
			throw new StudentException("No student ID input");
		SessionFactory sf = null;
		Session session = null;
		Transaction tx = null;
		try {
			sf = new Configuration().configure().buildSessionFactory();
			session = sf.openSession();
			tx = session.beginTransaction();
			String hql = "from Student where SID=:sid";
			Query query = session.createQuery(hql);
			query.setString("sid", sid);
			@SuppressWarnings("unchecked")
			List<Student> list = query.list();
			if (list.size() == 0)
				throw new StudentException("No student found!");
			Student s = list.get(0);
			if (!s.getSession().equals(studentSession))
				throw new StudentException("invalid session!");
			s.setSession("");
			session.save(s);
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage());
		} finally {
			if (tx != null)
				tx.commit();
			if (session != null)
				session.close();
		}
	}

	public Student findStudent(String sid) throws StudentException,
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
			query.setString("sid", sid);
			@SuppressWarnings("unchecked")
			List<Student> list = query.list();
			if (list.size() == 0)
				throw new StudentException("No student found!");
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

	public List<Professor> findPreferProfessorsList(String sid)
			throws StudentException, DatabaseException {
		SessionFactory sf = null;
		Session session = null;
		Transaction tx = null;
		try {
			sf = new Configuration().configure().buildSessionFactory();
			session = sf.openSession();
			tx = session.beginTransaction();
			String hql = "from Student where SID=:sid";
			Query query = session.createQuery(hql);
			query.setString("sid", sid);
			@SuppressWarnings("unchecked")
			List<Student> list = query.list();
			if (list.size() == 0)
				throw new StudentException("No student found!");
			List<Professor> result = list.get(0).preferProfessorsList();
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

	public Response addPreferProfessor(String sid, String pid,
			String studentSession) {
		// TODO Auto-generated method stub
		return null;
	}

	public Response swapPreferProfessor(String sid, String pid1, String pid2,
			String studentSession) {
		// TODO Auto-generated method stub
		return null;
	}

}
