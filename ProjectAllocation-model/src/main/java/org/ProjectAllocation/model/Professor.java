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
public class Professor extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1141185595926572529L;

	public static final String TABLENAME = "Professor";
	public static final String COL_PID = "PID";
	public static final String COL_NAME = "NAME";
	public static final String COL_PASSWORD = "PASSWORD";

	private String pid;
	private String name;
	private String password;
	private List<ProfessorPreferenceItem> preferList;
	private Set<StudentPreferenceItem> likedBy;

	public Professor() {
	}

	public Professor(String pid, String name) {
		super();
		this.pid = pid;
		this.name = name;
		this.preferList = new ArrayList<ProfessorPreferenceItem>();
		this.likedBy = new HashSet<StudentPreferenceItem>();
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

	@Column(name = COL_PASSWORD)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public List<Student>preferStudentsList() {
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
	
	public String toJSONString() {
		Map<String, String> result = new HashMap<String, String>();
		result.put("pid", this.getPid());
		result.put("name", this.getName());
		return JSONObject.fromObject(result).toString();
	}


}
