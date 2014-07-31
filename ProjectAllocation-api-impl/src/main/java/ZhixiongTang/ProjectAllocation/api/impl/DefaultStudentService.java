package ZhixiongTang.ProjectAllocation.api.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.ProjectAllocation.model.Student;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Service;

@Service("studentService#default")
public class DefaultStudentService implements
		ZhixiongTang.ProjectAllocation.api.StudentService {

	public String getInformationFromSID(String sid) {
		SessionFactory sf = new Configuration().configure()
				.buildSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		String hql = "from Student where SID=:sid";
		Query query = session.createQuery(hql);
		query.setString("sid", sid);
		@SuppressWarnings("unchecked")
		List<Student> list = query.list();
		tx.commit();
		session.close();
		if (list.size() == 0) return "haha";
		Student s = list.get(0);
		Map<String, String> result = new HashMap<String, String>();
		result.put("sid", s.getSid());
		result.put("name", s.getName());
		return JSONObject.fromObject( result ).toString();
	}

}
