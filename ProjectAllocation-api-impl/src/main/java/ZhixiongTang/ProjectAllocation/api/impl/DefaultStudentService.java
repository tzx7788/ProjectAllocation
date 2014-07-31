package ZhixiongTang.ProjectAllocation.api.impl;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;

import org.ProjectAllocation.model.Professor;
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
		if (list.size() == 0) {
			Error error = new Error("No student found!");
			State state = new State(error.toJSONString());
			return state.toJSONString();
		} else {
			Student s = list.get(0);
			State state = new State(s.toJSONString());
			return state.toJSONString();
		}
	}

	public String getPreferenceListFromSID(String sid) {
		SessionFactory sf = new Configuration().configure()
				.buildSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		String hql = "from Student where SID=:sid";
		Query query = session.createQuery(hql);
		query.setString("sid", sid);
		if (query.list().size() == 0) {
			tx.commit();
			session.close();
			Error error = new Error("No student found!");
			State state = new State(error.toJSONString());
			return state.toJSONString();
		} else {
			Student s = (Student) query.list().get(0);
			List<Professor> list = s.preferProfessorsList();
			tx.commit();
			session.close();
			List<String> result = new ArrayList<String>();
			for (Professor professor : list) {
				result.add(professor.toJSONString());
			}
			State state = new State(JSONArray.fromObject(result).toString());
			return state.toJSONString();
		}
	}
}
