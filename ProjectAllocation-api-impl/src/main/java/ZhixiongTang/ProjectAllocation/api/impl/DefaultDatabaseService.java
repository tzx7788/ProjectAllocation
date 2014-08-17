package ZhixiongTang.ProjectAllocation.api.impl;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.ProjectAllocation.model.Professor;
import org.ProjectAllocation.model.ProfessorPreferenceItem;
import org.ProjectAllocation.model.Student;
import org.ProjectAllocation.model.StudentPreferenceItem;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Service;

import ZhixiongTang.ProjectAllocation.api.AuthException;
import ZhixiongTang.ProjectAllocation.api.DatabaseException;
import ZhixiongTang.ProjectAllocation.api.DatabaseService;

@Service("databaseService#default")
public class DefaultDatabaseService implements DatabaseService {

	public Response clearDatabase(String authentification) {
		try {
			authorization(authentification);
		} catch (AuthException e) {
			e.printStackTrace();
			Error error = new Error(e.getMessage());
			State state = new State(error);
			ResponseBuilder builder = Response.ok(state);
			builder.entity(state.toString());
			return builder.build();
		}
		try {
			this.clear();
		} catch (DatabaseException e) {
			e.printStackTrace();
			Error error = new Error(e.getMessage());
			State state = new State(error);
			ResponseBuilder builder = Response.ok(state);
			builder.entity(state.toString());
			return builder.build();
		}
		State state = new State();
		ResponseBuilder builder = Response.ok(state);
		builder.entity(state.toString());
		return builder.build();
	}
	
	public Response loadTestData(String authentification, Integer version) {
		try {
			authorization(authentification);
		} catch (AuthException e) {
			e.printStackTrace();
			Error error = new Error(e.getMessage());
			State state = new State(error);
			ResponseBuilder builder = Response.ok(state);
			builder.entity(state.toString());
			return builder.build();
		}
		try {
			if ( version == 1 )
				this.loadTestData();
		} catch (DatabaseException e) {
			e.printStackTrace();
			Error error = new Error(e.getMessage());
			State state = new State(error);
			ResponseBuilder builder = Response.ok(state);
			builder.entity(state.toString());
			return builder.build();
		}
		State state = new State();
		ResponseBuilder builder = Response.ok(state);
		builder.entity(state.toString());
		return builder.build();
	}

	private void clear() throws DatabaseException {
		SessionFactory sf = new Configuration().configure()
				.buildSessionFactory();
		Session session = sf.openSession();
		try {
			Transaction tx = session.beginTransaction();
			session.createSQLQuery("DELETE FROM Student").executeUpdate();
			session.createSQLQuery("DELETE FROM Professor").executeUpdate();
			session.createSQLQuery("DELETE FROM SPreference").executeUpdate();
			session.createSQLQuery("DELETE FROM PPreference").executeUpdate();
			tx.commit();
			session.close();
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	private void loadTestData() throws DatabaseException {
		this.clear();
		SessionFactory sf = new Configuration().configure()
				.buildSessionFactory();
		Session session = sf.openSession();
		try {
			Transaction tx = session.beginTransaction();
			Student s1 = new Student("s1", "tzx1");
			Student s2 = new Student("s2", "tzx2");
			Student s3 = new Student("s3", "tzx3");
			Professor p1 = new Professor("p1", "haha1");
			Professor p2 = new Professor("p2", "haha1");
			Professor p3 = new Professor("p3", "haha1");
			s1.setSession("3e051af3f56067d8526cc1237134fcc8");
			session.save(s1);
			session.save(s2);
			session.save(s3);
			session.save(p1);
			session.save(p2);
			session.save(p3);
			StudentPreferenceItem sr1 = new StudentPreferenceItem(s1, p1, 0);
			StudentPreferenceItem sr2 = new StudentPreferenceItem(s1, p2, 1);
			StudentPreferenceItem sr3 = new StudentPreferenceItem(s2, p1, 2);
			StudentPreferenceItem sr4 = new StudentPreferenceItem(s3, p3, 3);
			s1.getPreferList().add(sr1);
			s1.getPreferList().add(sr2);
			s2.getPreferList().add(sr3);
			s3.getPreferList().add(sr4);
			ProfessorPreferenceItem pr1 = new ProfessorPreferenceItem(p1, s1, 0);
			ProfessorPreferenceItem pr2 = new ProfessorPreferenceItem(p1, s3, 1);
			ProfessorPreferenceItem pr3 = new ProfessorPreferenceItem(p2, s3, 2);
			ProfessorPreferenceItem pr4 = new ProfessorPreferenceItem(p3, s2, 3);
			ProfessorPreferenceItem pr5 = new ProfessorPreferenceItem(p3, s3, 4);
			p1.getPreferList().add(pr1);
			p1.getPreferList().add(pr2);
			p2.getPreferList().add(pr3);
			p3.getPreferList().add(pr4);
			p3.getPreferList().add(pr5);
			session.save(s1);
			session.save(s2);
			session.save(s3);
			session.save(p1);
			session.save(p2);
			session.save(p3);
			tx.commit();
			session.close();
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	private void authorization(String authentification) throws AuthException {
		if (authentification == null)
			throw new AuthException(
					"Authentification Error:no 'authentification' key found in header");
		if (!authentification.equals("developer"))
			throw new AuthException(
					"Authentification Error: invalid 'authentification' value");
	}
}
