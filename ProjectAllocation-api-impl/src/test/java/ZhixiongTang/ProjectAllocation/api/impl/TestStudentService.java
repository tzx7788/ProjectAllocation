package ZhixiongTang.ProjectAllocation.api.impl;

import static org.junit.Assert.*;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.ProjectAllocation.model.Professor;
import org.ProjectAllocation.model.Student;
import org.apache.catalina.Context;
import org.apache.catalina.deploy.ApplicationParameter;
import org.apache.catalina.startup.Tomcat;
import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.web.context.ContextLoaderListener;

import ZhixiongTang.ProjectAllocation.api.StudentService;

@RunWith(JUnit4.class)
public class TestStudentService {
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
		StudentService service = JAXRSClientFactory.create("http://localhost:"
				+ port + "/" + getRestServicesPath() + "/services/",
				StudentService.class);
		SessionFactory sf = new Configuration().configure()
				.buildSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		String hql = "from Student where SID=:sid";
		Query query = session.createQuery(hql);
		query.setString("sid", "s1");
		@SuppressWarnings("unchecked")
		List<Student> list = query.list();
		Student s = list.get(0);
		tx.commit();
		session.close();
		JSONObject jsonObject = JSONObject.fromObject(service
				.getInformationFromSID("s1"));
		System.out.print(service
				.getInformationFromSID("s1"));
		System.out.print(service
				.getPreferenceListFromSID("s1"));
		jsonObject = JSONObject.fromObject(jsonObject.get("data"));
		assertEquals(s.getSid().toString(), jsonObject.get("sid"));
		assertEquals(s.getName().toString(), jsonObject.get("name"));
	}

	@Test
	public void testGetPreferenceList() {
		StudentService service = JAXRSClientFactory.create("http://localhost:"
				+ port + "/" + getRestServicesPath() + "/services/",
				StudentService.class);
		SessionFactory sf = new Configuration().configure()
				.buildSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		String hql = "from Student where SID=:sid";
		Query query = session.createQuery(hql);
		query.setString("sid", "s1");
		Student s = (Student) query.list().get(0);
		List<Professor> list = s.preferProfessorsList();
		tx.commit();
		session.close();
		JSONObject jsonObject = JSONObject.fromObject(service
				.getPreferenceListFromSID("s1"));
		JSONArray array = jsonObject.getJSONArray("data");
		assertEquals(list.size(), array.size());
		for (int index = 0; index < list.size(); index++) {
			System.out.println(array.getJSONObject(index).get("pid"));
			assertEquals(list.get(index).getPid().toString(), array.getJSONObject(index).get("pid"));
			assertEquals(list.get(index).getName().toString(), array.getJSONObject(index).get("name"));
		}
	}
}
