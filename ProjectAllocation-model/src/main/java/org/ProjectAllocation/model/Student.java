package org.ProjectAllocation.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.*;

import net.sf.json.JSONObject;


@Entity
public class Student extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8362624965122639740L;

	public static final String TABLENAME = "Student";
	public static final String COL_SID = "SID";
	public static final String COL_NAME = "NAME";
	public static final String COL_PASSWORD = "PASSWORD";

	private String sid;
	private String name;
	private String password;
	private List<StudentPreferenceItem> preferList;
	private Set<ProfessorPreferenceItem> likedBy;

	public Student() {
	}

	public Student(String sid, String name) {
		super();
		this.sid = sid;
		this.name = name;
		this.preferList = new ArrayList<StudentPreferenceItem>();
		this.likedBy = new HashSet<ProfessorPreferenceItem>();
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

	@Column(name = COL_PASSWORD, length = 50)
	public String getPassword() {
		return password;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@OneToMany(mappedBy="student",cascade=CascadeType.ALL)
	@OrderBy("weight")
	public List<StudentPreferenceItem> getPreferList() {
		return preferList;
	}

	public void setPreferList(List<StudentPreferenceItem> preferList) {
		this.preferList = preferList;
	}

	@OneToMany(mappedBy="student",cascade=CascadeType.ALL)
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

	public String toJSONString() {
		Map<String, String> result = new HashMap<String, String>();
		result.put("sid", this.getSid());
		result.put("name", this.getName());
		return JSONObject.fromObject(result).toString();
	}

}
