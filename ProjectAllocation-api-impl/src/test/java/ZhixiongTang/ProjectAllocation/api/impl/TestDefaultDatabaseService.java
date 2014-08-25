package ZhixiongTang.ProjectAllocation.api.impl;


import java.io.IOException;
import java.io.InputStream;
import javax.ws.rs.core.Response;

import org.ProjectAllocation.model.Professor;
import org.ProjectAllocation.model.Student;
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
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.web.context.ContextLoaderListener;

import ZhixiongTang.ProjectAllocation.api.DatabaseService;

import junit.framework.TestCase;

@RunWith(JUnit4.class)
public class TestDefaultDatabaseService extends TestCase {
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
	public void testClear() {
		DatabaseService service = JAXRSClientFactory.create("http://localhost:"
				+ port + "/" + getRestServicesPath() + "/services/",
				DatabaseService.class);
		Response response = service.clearDatabase("developer");
		System.out.println(response.getMetadata());
		InputStream inputStream = (InputStream) response.getEntity();
		String theString = null;
		try {
			theString = IOUtils.toString(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		JSONObject jsonObject = new JSONObject(theString);
		assertEquals(jsonObject.get("status"),"success");
		SessionFactory sf = new Configuration().configure()
				.buildSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		String hql = "from " + Student.TABLENAME;
		Query query = session.createQuery(hql);
		assertEquals(0, query.list().size());
		hql = "from " + Professor.TABLENAME;
		query = session.createQuery(hql);
		assertEquals(0, query.list().size());
		tx.commit();
		session.close();
	}
	
	@Test
	public void testLoad() {
		DatabaseService service = JAXRSClientFactory.create("http://localhost:"
				+ port + "/" + getRestServicesPath() + "/services/",
				DatabaseService.class);
		Response response = service.loadTestData("developer", 2);
		System.out.println(response.getMetadata());
		InputStream inputStream = (InputStream) response.getEntity();
		String theString = null;
		try {
			theString = IOUtils.toString(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		JSONObject jsonObject = new JSONObject(theString);
		assertEquals(jsonObject.get("status"),"success");
	}

}
