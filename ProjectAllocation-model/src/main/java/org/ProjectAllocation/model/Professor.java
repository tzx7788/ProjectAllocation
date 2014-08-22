package org.ProjectAllocation.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import org.json.JSONArray;
import org.json.JSONObject;

@Entity
public class Professor extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1141185595926572529L;

	public static final String TABLENAME = "Professor";
	public static final String COL_PID = "PID";
	public static final String COL_NAME = "NAME";
	public static final String COL_PASSWORD = "PASSWORD";
	public static final String COL_SESSION = "SESSION";
	public static final String COL_LIMITATION = "LIMITATION";

	private String pid;
	private String name;
	private String password;
	private String session;
	private Integer limit;

	private List<ProfessorPreferenceItem> preferList;
	private Set<StudentPreferenceItem> likedBy;
	private Set<Student> result;

	public Professor() {
	}

	public Professor(String pid, String name, Integer limit) {
		super();
		this.pid = pid;
		this.name = name;
		this.preferList = new ArrayList<ProfessorPreferenceItem>();
		this.likedBy = new HashSet<StudentPreferenceItem>();
		this.result = new HashSet<Student>();
		this.limit = limit;
	}

	public Professor(String pid, String name) {
		super();
		this.pid = pid;
		this.name = name;
		this.preferList = new ArrayList<ProfessorPreferenceItem>();
		this.likedBy = new HashSet<StudentPreferenceItem>();
		this.result = new HashSet<Student>();
		this.limit = 1;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	@Column(name = COL_NAME)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = COL_PID)
	@Id
	public String getPid() {
		return pid;
	}

	@Column(name = COL_PASSWORD, nullable = false)
	public String getPassword() {
		if (password == null)
			password = "";
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = COL_LIMITATION)
	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	@Column(name = COL_SESSION, nullable = true)
	public String getSession() {
		return session;
	}

	public void setSession(String session) {
		this.session = session;
	}

	public void setPreferList(List<ProfessorPreferenceItem> preferList) {
		this.preferList = preferList;
	}

	public void setLikedBy(Set<StudentPreferenceItem> likedBy) {
		this.likedBy = likedBy;
	}

	@OneToMany(mappedBy = "professor", cascade = CascadeType.ALL)
	@OrderBy("weight")
	public List<ProfessorPreferenceItem> getPreferList() {
		return preferList;
	}

	@OneToMany(mappedBy = "professor", cascade = CascadeType.ALL)
	public Set<StudentPreferenceItem> getLikedBy() {
		return likedBy;
	}

	public List<Student> preferStudentsList() {
		ArrayList<Student> result = new ArrayList<Student>();
		for (int index = 0; index < this.getPreferList().size(); index++)
			result.add(this.getPreferList().get(index).getStudent());
		return result;
	}

	public Set<Student> likedByStudentsSet() {
		HashSet<Student> result = new HashSet<Student>();
		for (StudentPreferenceItem item : this.getLikedBy()) {
			Student student = item.getStudent();
			result.add(student);
		}
		return result;
	}

	@ManyToMany(targetEntity = Student.class, cascade = { CascadeType.PERSIST,
			CascadeType.MERGE })
	@JoinTable(
			name = "Result",
			joinColumns = @JoinColumn(name = COL_PID),
			inverseJoinColumns = @JoinColumn(name = Student.COL_SID))
	public Set<Student> getResult() {
		if (result == null)
			result = new HashSet<Student>();
		return result;
	}

	public void setResult(Set<Student> result) {
		this.result = result;
	}

	public JSONObject toJSONObject() {
		JSONObject result = new JSONObject();
		result.put("pid", this.getPid());
		result.put("name", this.getName());
		result.put("limit", this.getLimit());
		return result;
	}

	public JSONObject toJSONObjectWithSession() {
		JSONObject result = new JSONObject();
		result.put("pid", this.getPid());
		result.put("name", this.getName());
		result.put("limit", this.getLimit());
		List<Student> list = this.preferStudentsList();
		JSONArray resultArray = new JSONArray();
		JSONArray suggestionArray = new JSONArray();
		for (Student student : this.getResult())
			if (list.contains(student))
				resultArray.put(student.toJSONObject());
			else
				suggestionArray.put(student.toJSONObject());
		result.put("result", resultArray);
		result.put("suggestion", suggestionArray);
		return result;
	}

	public void swap(Student s1, Student s2) {
		List<Student> list = this.preferStudentsList();
		if (!list.contains(s1))
			return;
		if (!list.contains(s2))
			return;
		ProfessorPreferenceItem item1 = this.getPreferList().get(
				list.indexOf(s1));
		ProfessorPreferenceItem item2 = this.getPreferList().get(
				list.indexOf(s2));
		this.getPreferList().set(list.indexOf(s1), item2);
		this.getPreferList().set(list.indexOf(s2), item1);
		int weight = item1.getWeight();
		item1.setWeight(item2.getWeight());
		item2.setWeight(weight);
	}
}
