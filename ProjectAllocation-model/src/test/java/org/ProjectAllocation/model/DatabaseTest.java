package org.ProjectAllocation.model;

import org.hibernate.Transaction;
import org.hibernate.cfg.*;
import org.hibernate.*;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class DatabaseTest extends TestCase {
	public static void main(String[] arg) {

		SessionFactory sf =
            new Configuration().configure().buildSessionFactory();
        Session session = sf.openSession();
        Transaction tx = session.beginTransaction();
		session.createSQLQuery("DELETE FROM Student").executeUpdate();
		session.createSQLQuery("DELETE FROM Professor").executeUpdate();
		Student s = new Student("s1", "tzx");
		Professor p = new Professor("p1","haha");
		s.getPreferList().add(p);
		session.save(s);
		session.save(p);
		tx.commit();
        session.close();
		System.out.println("finished");
	}
}
