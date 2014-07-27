package org.ProjectAllocation.model;

import java.util.List;

import org.hibernate.Transaction;
import org.hibernate.cfg.*;
import org.hibernate.*;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class DatabaseTest extends TestCase {

	public DatabaseTest(String name) {
		super(name);
	}

	public static Test suite() {
		return new TestSuite(DatabaseTest.class);
	}

	@Override
	protected void setUp() throws Exception {
		SessionFactory sf = new Configuration().configure()
				.buildSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		session.createSQLQuery("DELETE FROM Student").executeUpdate();
		session.createSQLQuery("DELETE FROM Professor").executeUpdate();
		session.createSQLQuery("DELETE FROM SPreference").executeUpdate();
		session.createSQLQuery("DELETE FROM PPreference").executeUpdate();
		Student s1 = new Student("s1", "tzx1");
		Student s2 = new Student("s2", "tzx2");
		Student s3 = new Student("s3", "tzx3");
		Professor p1 = new Professor("p1", "haha1");
		Professor p2 = new Professor("p2", "haha1");
		Professor p3 = new Professor("p3", "haha1");
		s1.getPreferList().add(p1);
		s1.getPreferList().add(p2);
		s2.getPreferList().add(p1);
		s3.getPreferList().add(p3);
		p1.getPreferList().add(s1);
		p1.getPreferList().add(s3);
		p2.getPreferList().add(s3);
		p3.getPreferList().add(s2);
		p3.getPreferList().add(s3);
		session.save(s1);
		session.save(s2);
		session.save(s3);
		session.save(p1);
		session.save(p2);
		session.save(p3);
		tx.commit();
		session.close();
	}

	@Override
	protected void tearDown() throws Exception {
//		SessionFactory sf = new Configuration().configure()
//				.buildSessionFactory();
//		Session session = sf.openSession();
//		Transaction tx = session.beginTransaction();
//		session.createSQLQuery("DELETE FROM Student").executeUpdate();
//		session.createSQLQuery("DELETE FROM Professor").executeUpdate();
//		session.createSQLQuery("DELETE FROM SPreference").executeUpdate();
//		session.createSQLQuery("DELETE FROM PPreference").executeUpdate();
//		tx.commit();
//		session.close();
	}

	public static void testSelect() {
		SessionFactory sf = new Configuration().configure()
				.buildSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		Student s1 = (Student) session.get(Student.class, "s1");
		Student s2 = (Student) session.get(Student.class, "s2");
		Student s3 = (Student) session.get(Student.class, "s3");
		Professor p1 = (Professor) session.get(Professor.class, "p1");
		Professor p2 = (Professor) session.get(Professor.class, "p2");
		Professor p3 = (Professor) session.get(Professor.class, "p3");
		assertTrue(s1.getLikedBy().contains(p1));
		assertTrue(s2.getLikedBy().contains(p3));
		assertTrue(s3.getLikedBy().contains(p1));
		assertTrue(s3.getLikedBy().contains(p2));
		assertTrue(s3.getLikedBy().contains(p3));
		assertTrue(p1.getLikedBy().contains(s1));
		assertTrue(p1.getLikedBy().contains(s2));
		assertTrue(p2.getLikedBy().contains(s1));
		assertTrue(p3.getLikedBy().contains(s3));
		assertFalse(s1.getLikedBy().contains(p2));
		assertFalse(s1.getLikedBy().contains(p3));
		assertFalse(s2.getLikedBy().contains(p1));
		assertFalse(s2.getLikedBy().contains(p2));
		assertFalse(p1.getLikedBy().contains(s3));
		assertFalse(p2.getLikedBy().contains(s2));
		assertFalse(p2.getLikedBy().contains(s3));
		assertFalse(p3.getLikedBy().contains(s1));
		assertFalse(p3.getLikedBy().contains(s2));
		tx.commit();
		session.close();
	}

	public void testHQL() {
		SessionFactory sf = new Configuration().configure()
				.buildSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		String hql = "from Student where name=:name";
		Query query = session.createQuery(hql);
		query.setString("name", "tzx1");
		@SuppressWarnings("unchecked")
		List<Student> list = query.list();
		assertEquals(list.size(), 1);
		assertEquals(list.get(0).getSid(), "s1");
		tx.commit();
		session.close();
	}

	public void testUpdate() {
		SessionFactory sf = new Configuration().configure()
				.buildSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		Student s1 = (Student) session.get(Student.class, "s1");
		Professor p1 = (Professor) session.get(Professor.class, "p1");
		Professor p3 = (Professor) session.get(Professor.class, "p3");
		s1.getPreferList().add(p3);
		assertFalse(p3.getLikedBy().contains(s1));
		p3.getLikedBy().add(s1);
		assertTrue(p3.getLikedBy().contains(s1));
		s1.getPreferList().remove(p1);
		assertTrue(p1.getLikedBy().contains(s1));
		s1.setName("tzxtzx");
		session.save(s1);
		tx.commit();
		session.close();
		sf = new Configuration().configure().buildSessionFactory();
		session = sf.openSession();
		tx = session.beginTransaction();
		s1 = (Student) session.get(Student.class, "s1");
		p1 = (Professor) session.get(Professor.class, "p1");
		p3 = (Professor) session.get(Professor.class, "p3");
		System.out.println("size:" + p3.getLikedBy().size());
		assertTrue(s1.getPreferList().contains(p3));
		assertTrue(p3.getLikedBy().contains(s1));
		assertFalse(p1.getLikedBy().contains(s1));
		assertEquals(s1.getName(), "tzxtzx");
		tx.commit();
		session.close();
	}

	public void testInsert() {
		SessionFactory sf = new Configuration().configure()
				.buildSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		Student s = (Student) session.get(Student.class, "s4");
		assertEquals(s, null);
		Professor p = (Professor) session.get(Professor.class, "p4");
		assertEquals(s, null);
		s = new Student("s4", "tzx4");
		p = new Professor("p4", "haha4");
		session.save(s);
		session.save(p);
		tx.commit();
		session.close();
		sf = new Configuration().configure().buildSessionFactory();
		session = sf.openSession();
		tx = session.beginTransaction();
		s = (Student) session.get(Student.class, "s4");
		assertNotNull(s);
		p = (Professor) session.get(Professor.class, "p4");
		assertNotNull(p);
		tx.commit();
		session.close();
	}

	public void testDelete() {
		SessionFactory sf = new Configuration().configure()
				.buildSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		Student s = (Student) session.get(Student.class, "s1");
		assertNotNull(s);
		Professor p = (Professor) session.get(Professor.class, "p1");
		assertNotNull(p);
		s.getPreferList().remove(p);
		session.delete(s);
		session.delete(p);
		tx.commit();
		session.close();
		sf = new Configuration().configure().buildSessionFactory();
		session = sf.openSession();
		tx = session.beginTransaction();
		s = (Student) session.get(Student.class, "s1");
		assertNull(s);
		p = (Professor) session.get(Professor.class, "p1");
		assertNull(p);
		s = (Student) session.get(Student.class, "s2");
		assertEquals(s.getPreferList().size(),0);
		tx.commit();
		session.close();
	}

	public static void main(String[] arg) {

		SessionFactory sf = new Configuration().configure()
				.buildSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		session.createSQLQuery("DELETE FROM Student").executeUpdate();
		session.createSQLQuery("DELETE FROM Professor").executeUpdate();
		session.createSQLQuery("DELETE FROM SPreference").executeUpdate();
		session.createSQLQuery("DELETE FROM PPreference").executeUpdate();
		Student s1 = new Student("s1", "tzx1");
		Student s2 = new Student("s2", "tzx2");
		Student s3 = new Student("s3", "tzx3");
		Professor p1 = new Professor("p1", "haha1");
		Professor p2 = new Professor("p2", "haha1");
		Professor p3 = new Professor("p3", "haha1");
		s1.getPreferList().add(p1);
		s1.getPreferList().add(p2);
		s2.getPreferList().add(p1);
		s3.getPreferList().add(p3);
		p1.getPreferList().add(s1);
		p1.getPreferList().add(s3);
		p2.getPreferList().add(s3);
		p3.getPreferList().add(s2);
		p3.getPreferList().add(s3);
		session.save(s1);
		session.save(s2);
		session.save(s3);
		session.save(p1);
		session.save(p2);
		session.save(p3);
		Student s = (Student) session.get(Student.class, "s1");
		System.out.println("ll:" + s.getName());
		tx.commit();
		session.close();
		System.out.println("finished");
	}
}
