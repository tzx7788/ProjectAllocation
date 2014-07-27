package org.ProjectAllocation.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import javax.persistence.*;

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
	public static final String PREFERENCETABLE = "PPreference";
	
	private String pid;
	private String name;
	private String password;
	private List<Student> preferList;
	private Collection<Student> likedBy;

	public Professor() {
	}

	public Professor(String pid, String name) {
		super();
		this.pid = pid;
		this.name = name;
		this.preferList = new ArrayList<Student>();
		this.likedBy = new HashSet<Student>();
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

	public void setPreferList(List<Student> preferList) {
		this.preferList = preferList;
	}

	public void setLikedBy(Collection<Student> likedBy) {
		this.likedBy = likedBy;
	}

	@ManyToMany(targetEntity = Student.class, cascade = { CascadeType.PERSIST,
			CascadeType.MERGE })
	@JoinTable(
			name = PREFERENCETABLE,
			joinColumns = @JoinColumn(name = COL_PID),
			inverseJoinColumns = @JoinColumn(name = Student.COL_SID))
	public List<Student> getPreferList() {
		return preferList;
	}

	@ManyToMany(targetEntity = Student.class, cascade = { CascadeType.PERSIST,
			CascadeType.MERGE })
	@JoinTable(
			name = Student.PREFERENCETABLE,
			joinColumns = @JoinColumn(name = COL_PID),
			inverseJoinColumns = @JoinColumn(name = Student.COL_SID))
	public Collection<Student> getLikedBy() {
		return likedBy;
	}

}
