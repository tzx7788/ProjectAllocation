package ZhixiongTang.ProjectAllocation.api.impl;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Service;

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

	private void authorization(String authentification) throws AuthException {
		if (authentification == null)
			throw new AuthException(
					"Authentification Error:no 'authentification' key found in header");
		if (!authentification.equals("developer"))
			throw new AuthException(
					"Authentification Error: invalid 'authentification' value");
	}
}
