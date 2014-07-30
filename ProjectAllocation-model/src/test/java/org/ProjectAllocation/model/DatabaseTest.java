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
	}

	@Override
	protected void tearDown() throws Exception {
		// SessionFactory sf = new Configuration().configure()
		// .buildSessionFactory();
		// Session session = sf.openSession();
		// Transaction tx = session.beginTransaction();
		// session.createSQLQuery("DELETE FROM Student").executeUpdate();
		// session.createSQLQuery("DELETE FROM Professor").executeUpdate();
		// session.createSQLQuery("DELETE FROM SPreference").executeUpdate();
		// session.createSQLQuery("DELETE FROM PPreference").executeUpdate();
		// tx.commit();
		// session.close();
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
		assertTrue(s1.likedByProfessorsSet().contains(p1));
		assertTrue(s2.likedByProfessorsSet().contains(p3));
		assertTrue(s3.likedByProfessorsSet().contains(p1));
		assertTrue(s3.likedByProfessorsSet().contains(p2));
		assertTrue(s3.likedByProfessorsSet().contains(p3));
		assertTrue(p1.likedByStudentsSet().contains(s1));
		assertTrue(p1.likedByStudentsSet().contains(s2));
		assertTrue(p2.likedByStudentsSet().contains(s1));
		assertTrue(p3.likedByStudentsSet().contains(s3));
		assertFalse(s1.likedByProfessorsSet().contains(p2));
		assertFalse(s1.likedByProfessorsSet().contains(p3));
		assertFalse(s2.likedByProfessorsSet().contains(p1));
		assertFalse(s2.likedByProfessorsSet().contains(p2));
		assertFalse(p1.likedByStudentsSet().contains(s3));
		assertFalse(p2.likedByStudentsSet().contains(s2));
		assertFalse(p2.likedByStudentsSet().contains(s3));
		assertFalse(p3.likedByStudentsSet().contains(s1));
		assertFalse(p3.likedByStudentsSet().contains(s2));
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
		StudentPreferenceItem sr = new StudentPreferenceItem(s1, p3, 4);
		s1.getPreferList().add(sr);
		assertFalse(p3.likedByStudentsSet().contains(s1));
		System.out.println("size " + s1.getPreferList().size());
		for (int i = 0; i < s1.getPreferList().size(); i++)

		{
			StudentPreferenceItem item = s1.getPreferList().get(i);
			if (item.getProfessor() == p1) {
				System.out.println("haha "
						+ s1.getPreferList().get(i).getProfessor().getPid());
				s1.getPreferList().remove(item);
				item.getProfessor().getLikedBy().remove(item);
				item.setStudent(null);
				item.setProfessor(null);
				session.delete(item);
			}
		}
		System.out.println("size " + s1.getPreferList().size());
		// assertTrue(p1.likedByStudentsSet().contains(s1));
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
		assertTrue(s1.preferProfessorsList().contains(p3));
		assertTrue(p3.likedByStudentsSet().contains(s1));
		assertFalse(p1.likedByStudentsSet().contains(s1));
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
		session.delete(s);
		tx.commit();
		session.close();
		sf = new Configuration().configure().buildSessionFactory();
		session = sf.openSession();
		tx = session.beginTransaction();
		s = (Student) session.get(Student.class, "s1");
		assertNull(s);
		p = (Professor) session.get(Professor.class, "p1");
		assertEquals(p.getPreferList().size(), 1);
		tx.commit();
		session.close();
	}

	public void testSort() {
		SessionFactory sf = new Configuration().configure()
				.buildSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		Student s = (Student) session.get(Student.class, "s1");
		Integer weight = s.getPreferList().get(1).getWeight();
		s.getPreferList().get(1).setWeight(s.getPreferList().get(0).getWeight());
		s.getPreferList().get(0).setWeight(weight);
		StudentPreferenceItem item = s.getPreferList().get(0);
		s.getPreferList().remove(item);
		s.getPreferList().add(item);
		session.save(s);
		tx.commit();
		session.close();
	}
}
