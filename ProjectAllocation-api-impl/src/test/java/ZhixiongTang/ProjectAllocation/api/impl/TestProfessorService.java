package ZhixiongTang.ProjectAllocation.api.impl;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.ws.rs.core.Response;

import org.ProjectAllocation.model.Professor;
import org.ProjectAllocation.model.ProfessorPreferenceItem;
import org.ProjectAllocation.model.Student;
import org.ProjectAllocation.model.StudentPreferenceItem;
import org.apache.catalina.Context;
import org.apache.catalina.deploy.ApplicationParameter;
import org.apache.catalina.startup.Tomcat;
import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.web.context.ContextLoaderListener;

import ZhixiongTang.ProjectAllocation.api.ProfessorService;

@RunWith(JUnit4.class)
public class TestProfessorService {
	int port;

	private Tomcat tomcat;

	@Before
	public void startTomcat() throws Exception {
		tomcat = new Tomcat();
		tomcat.setBaseDir(System.getProperty("java.io.tmpdir"));
		tomcat.setPort(0);

		Context context = tomcat.addContext("",
				System.getProperty("java.io.tmpdir"));

		ApplicationParameter applicationParameter = new ApplicationParameter();
		applicationParameter.setName("contextConfigLocation");
		applicationParameter.setValue(getSpringConfigLocation());
		context.addApplicationParameter(applicationParameter);

		context.addApplicationListener(ContextLoaderListener.class.getName());

		Tomcat.addServlet(context, "cxf", new CXFServlet());
		context.addServletMapping("/" + getRestServicesPath() + "/*", "cxf");

		tomcat.start();

		port = tomcat.getConnector().getLocalPort();

		System.out.println("Tomcat started on port:" + port);
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
		s1.setSession("3e051af3f56067d8526cc1237134fcc8");
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

	@After
	public void stopTomcat() throws Exception {
		tomcat.stop();
	}

	protected String getRestServicesPath() {
		return "foo";
	}

	protected String getSpringConfigLocation() {
		return "classpath*:META-INF/spring-context.xml";
	}

	@Test
	public void testGetInformation() {
		ProfessorService service = JAXRSClientFactory.create("http://localhost:"
				+ port + "/" + getRestServicesPath() + "/services/",
				ProfessorService.class);
		SessionFactory sf = new Configuration().configure()
				.buildSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		String hql = "from Professor where PID=:pid";
		Query query = session.createQuery(hql);
		query.setString("pid", "p1");
		@SuppressWarnings("unchecked")
		List<Professor> list = query.list();
		Professor p = list.get(0);
		tx.commit();
		session.close();
		Response response = service.getInformationFromPID("p1");
		System.out.println(response.getMetadata());
		InputStream inputStream = (InputStream) response.getEntity();
		String theString = null;
		try {
			theString = IOUtils.toString(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		JSONObject jsonObject = new JSONObject(theString);
		jsonObject = jsonObject.getJSONObject("data");
		assertEquals(p.getPid().toString(), jsonObject.get("pid"));
		assertEquals(p.getName().toString(), jsonObject.get("name"));
	}

	@Test
	public void testGetPreferenceList() {
	}

	@Test
	public void testLogin() {
	}

	@Test
	public void testLogout() {

	}

	@Test
	public void testDelete() {
	}

	@Test
	public void testAdd() {
	}

	@Test
	public void testSwap() {

	}

}
