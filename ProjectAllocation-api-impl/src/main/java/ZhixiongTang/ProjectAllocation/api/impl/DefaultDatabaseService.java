package ZhixiongTang.ProjectAllocation.api.impl;


import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Service;

import ZhixiongTang.ProjectAllocation.api.DatabaseService;

@Service("databaseService#default")
public class DefaultDatabaseService implements DatabaseService {

	public Response clearDatabase(String authentification) {
		if ( authentification == null ) authentification = "";
		if ( !authentification.equals("developer") ) {
			Error error = new Error("Authentification Error");
			State state = new State(error);
			ResponseBuilder builder = Response.ok(state);
			builder.entity(state.toString());
			return builder.build();
		}
		SessionFactory sf = new Configuration().configure()
				.buildSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		session.createSQLQuery("DELETE FROM Student").executeUpdate();
		session.createSQLQuery("DELETE FROM Professor").executeUpdate();
		session.createSQLQuery("DELETE FROM SPreference").executeUpdate();
		session.createSQLQuery("DELETE FROM PPreference").executeUpdate();
		tx.commit();
		session.close();
		State state = new State();
		ResponseBuilder builder = Response.ok(state);
		builder.entity(state.toString());
		return builder.build();
	}

}
