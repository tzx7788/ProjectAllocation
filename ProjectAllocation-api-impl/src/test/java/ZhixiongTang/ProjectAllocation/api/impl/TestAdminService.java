package ZhixiongTang.ProjectAllocation.api.impl;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.core.Response;

import org.apache.catalina.Context;
import org.apache.catalina.deploy.ApplicationParameter;
import org.apache.catalina.startup.Tomcat;
import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.web.context.ContextLoaderListener;

import ZhixiongTang.ProjectAllocation.api.AdminService;
import ZhixiongTang.ProjectAllocation.api.DatabaseService;

@RunWith(JUnit4.class)
public class TestAdminService {

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
		
		DatabaseService service = JAXRSClientFactory.create("http://localhost:"
				+ port + "/" + getRestServicesPath() + "/services/",
				DatabaseService.class);
		service.loadTestData("developer", 1);
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
		AdminService service = JAXRSClientFactory.create("http://localhost:"
				+ port + "/" + getRestServicesPath() + "/services/",
				AdminService.class);
		Response response = service.getInformationFromAid("a1");
		System.out.println(response.getMetadata());
		InputStream inputStream = (InputStream) response.getEntity();
		String theString = null;
		try {
			theString = IOUtils.toString(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(theString);
		JSONObject jsonObject = new JSONObject(theString);
		jsonObject = jsonObject.getJSONObject("data");
		assertEquals("admin", jsonObject.get("name"));
	}

	@Test
	public void testLogin() {
		AdminService service = JAXRSClientFactory.create("http://localhost:"
				+ port + "/" + getRestServicesPath() + "/services/",
				AdminService.class);
		Response response = service.loginAdmin("a1", "");
		System.out.println(response.getMetadata());
		InputStream inputStream = (InputStream) response.getEntity();
		String theString = null;
		try {
			theString = IOUtils.toString(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(theString);
		JSONObject jsonObject = new JSONObject(theString);
		jsonObject = jsonObject.getJSONObject("data");
		assertEquals("admin", jsonObject.get("name"));
	}
	
	@Test
	public void testLogout() {
		AdminService service = JAXRSClientFactory.create("http://localhost:"
				+ port + "/" + getRestServicesPath() + "/services/",
				AdminService.class);
		Response response = service.logoutAdmin("a1", "3e051af3f56067d8526cc1237134fcc8");
		System.out.println(response.getMetadata());
		InputStream inputStream = (InputStream) response.getEntity();
		String theString = null;
		try {
			theString = IOUtils.toString(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(theString);
		JSONObject jsonObject = new JSONObject(theString);
		assertEquals("success", jsonObject.get("status"));
	}
}
