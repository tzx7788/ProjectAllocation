package ZhixiongTang.ProjectAllocation.api.impl;

import java.util.List;

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

	private Student login(String sid, String password) throws StudentException,
			DatabaseException {
		if ( sid == null ) throw new StudentException("No student ID input");
		SessionFactory sf = new Configuration().configure()
				.buildSessionFactory();
		Session session = sf.openSession();
		try {
			Transaction tx = session.beginTransaction();
			String hql = "from Student where SID=:sid";
			Query query = session.createQuery(hql);
			query.setString("sid", sid);
			@SuppressWarnings("unchecked")
			List<Student> list = query.list();
			if (list.size() == 0)
				throw new StudentException("No student found!");
			Student s = list.get(0);
			System.out.print(password+":"+s.getPassword());
			if (!s.getPassword().equals(password))
				throw new StudentException("Wrong password!");
			s.setSession(SessionGenerator.generateSession());
			session.save(s);
			tx.commit();
			session.close();
			return s;
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	private Student findStudent(String sid) throws StudentException,
			DatabaseException {
		SessionFactory sf = new Configuration().configure()
				.buildSessionFactory();
		Session session = sf.openSession();
		try {
			Transaction tx = session.beginTransaction();
			String hql = "from Student where SID=:sid";
			Query query = session.createQuery(hql);
			query.setString("sid", sid);
			@SuppressWarnings("unchecked")
			List<Student> list = query.list();
			tx.commit();
			session.close();
			if (list.size() == 0)
				throw new StudentException("No student found!");
			return list.get(0);
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	private List<Professor> findPreferProfessorsList(String sid)
			throws StudentException, DatabaseException {
		SessionFactory sf = new Configuration().configure()
				.buildSessionFactory();
		Session session = sf.openSession();
		try {
			Transaction tx = session.beginTransaction();
			String hql = "from Student where SID=:sid";
			Query query = session.createQuery(hql);
			query.setString("sid", sid);
			@SuppressWarnings("unchecked")
			List<Student> list = query.list();
			if (list.size() == 0)
				throw new StudentException("No student found!");
			List<Professor> result = list.get(0).preferProfessorsList();
			tx.commit();
			session.close();
			return result;
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage());
		}
	}
}
