package org.ProjectAllocation.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import org.json.JSONArray;
import org.json.JSONObject;

@Entity
public class Student extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8362624965122639740L;

	public static final String TABLENAME = "Student";
	public static final String COL_SID = "SID";
	public static final String COL_NAME = "NAME";
	public static final String COL_SESSION = "SESSION";
	public static final String COL_PASSWORD = "PASSWORD";

	private String sid;
	private String name;
	private String password;
	private String session;
	private List<StudentPreferenceItem> preferList;
	private Set<ProfessorPreferenceItem> likedBy;
	private Set<Professor> result;

	public Student() {
	}

	public Student(String sid, String name) {
		super();
		this.sid = sid;
		this.name = name;
		this.preferList = new ArrayList<StudentPreferenceItem>();
		this.likedBy = new HashSet<ProfessorPreferenceItem>();
		this.result = new HashSet<Professor>();
	}

	@Column(name = COL_NAME, length = 50)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = COL_SID, unique = true)
	@Id
	public String getSid() {
		return sid;
	}

	@Column(name = COL_PASSWORD, length = 50, nullable = false)
	public String getPassword() {
		if (password == null)
			password = "";
		return password;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = COL_SESSION, nullable = true)
	public String getSession() {
		return session;
	}

	public void setSession(String session) {
		this.session = session;
	}

	@OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
	@OrderBy("weight")
	public List<StudentPreferenceItem> getPreferList() {
		return preferList;
	}

	public void setPreferList(List<StudentPreferenceItem> preferList) {
		this.preferList = preferList;
	}

	@OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
	public Set<ProfessorPreferenceItem> getLikedBy() {
		return likedBy;
	}

	public void setLikedBy(Set<ProfessorPreferenceItem> likedBy) {
		this.likedBy = likedBy;
	}

	public List<Professor> preferProfessorsList() {
		ArrayList<Professor> result = new ArrayList<Professor>();
		for (int index = 0; index < this.getPreferList().size(); index++)
			result.add(this.getPreferList().get(index).getProfessor());
		return result;
	}

	public Set<Professor> likedByProfessorsSet() {
		HashSet<Professor> result = new HashSet<Professor>();
		for (ProfessorPreferenceItem item : this.getLikedBy()) {
			Professor professor = item.getProfessor();
			result.add(professor);
		}
		return result;
	}

	@ManyToMany(targetEntity = Professor.class, cascade = { CascadeType.PERSIST,
			CascadeType.MERGE })
	@JoinTable(
			name = "Result",
			joinColumns = @JoinColumn(name = COL_SID),
			inverseJoinColumns = @JoinColumn(name = Professor.COL_PID))
	public Set<Professor> getResult() {
		if ( result == null )
			result = new HashSet<Professor>();
		return result;
	}

	public void setResult(Set<Professor> result) {
		this.result = result;
	}
	
	public void saveOutput() {
		
	}

	public JSONObject toJSONObject() {
		JSONObject result = new JSONObject();
		result.put("sid", this.getSid());
		result.put("name", this.getName());;
		return result;
	}
	
	public JSONObject toJSONObjectWithSession() {
		JSONObject result = new JSONObject();
		result.put("sid", this.getSid());
		result.put("name", this.getName());
		List<Professor> list = this.preferProfessorsList();
		JSONArray resultArray = new JSONArray();
		JSONArray suggestionArray = new JSONArray();
		for ( Professor professor : this.getResult() )
			if ( list.contains(professor) )
				resultArray.put(professor.toJSONObject());
			else
				suggestionArray.put(professor.toJSONObject());
		result.put("result", resultArray);
		result.put("suggestion", suggestionArray);
		return result;
	}

	public void swap(Professor p1, Professor p2) {
		List<Professor> list = this.preferProfessorsList();
		if (!list.contains(p1))
			return;
		if (!list.contains(p2))
			return;
		StudentPreferenceItem item1 = this.getPreferList()
				.get(list.indexOf(p1));
		StudentPreferenceItem item2 = this.getPreferList()
				.get(list.indexOf(p2));
		this.getPreferList().set(list.indexOf(p1), item2);
		this.getPreferList().set(list.indexOf(p2), item1);
		int weight = item1.getWeight();
		item1.setWeight(item2.getWeight());
		item2.setWeight(weight);
	}

}
