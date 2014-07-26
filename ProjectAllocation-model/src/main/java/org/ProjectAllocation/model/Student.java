package org.ProjectAllocation.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Student", catalog = "Cnvjt63_PA")
public class Student extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8362624965122639740L;
	private String sid;
	private String name;
	private List<Professor> preferList;
	private List<Professor> likedByList;

	@Column(name = "NAME", length = 50)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "SID", unique = true)
	public String getSid() {
		return sid;
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

	@ManyToMany(
			cascade = { CascadeType.PERSIST, CascadeType.MERGE },
			mappedBy = "Professor",
			targetEntity = Professor.class)
	public List<Professor> getLikedByList() {
		return likedByList;
	}

}
