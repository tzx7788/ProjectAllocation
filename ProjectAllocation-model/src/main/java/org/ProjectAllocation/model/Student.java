package org.ProjectAllocation.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import javax.persistence.*;

@Entity
public class Student extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8362624965122639740L;
	private String sid;
	private String name;
	private String password;
	private List<Professor> preferList;
	private Collection<Professor> likedBy;

	public Student() {
	}

	public Student(String sid, String name) {
		super();
		this.sid = sid;
		this.name = name;
		this.preferList = new ArrayList<Professor>();
		this.likedBy = new HashSet<Professor>();
	}

	@Column(name = "NAME", length = 50)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "SID", unique = true)
	@Id
	public String getSid() {
		return sid;
	}

	@Column(name = "PASSWORD", length = 50)
	public String getPassword() {
		return password;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@ManyToMany(targetEntity = Professor.class, cascade = {
			CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(
			name = "SPreference",
			joinColumns = @JoinColumn(name = "SID"),
			inverseJoinColumns = @JoinColumn(name = "PID"))
	public List<Professor> getPreferList() {
		return preferList;
	}

	public void setPreferList(List<Professor> preferList) {
		this.preferList = preferList;
	}

	@ManyToMany(
	        cascade = {CascadeType.PERSIST, CascadeType.MERGE},
	        mappedBy = "preferList",
	        targetEntity = Professor.class
	    )
	public Collection<Professor> getLikedBy() {
		return likedBy;
	}

	public void setLikedBy(Collection<Professor> likedBy) {
		this.likedBy = likedBy;
	}

}
