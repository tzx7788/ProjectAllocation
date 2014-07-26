package org.ProjectAllocation.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

@Entity
@Table(name = "Professor", catalog = "Cnvjt63_PA")
public class Professor extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1141185595926572529L;
	private String pid;
	private String name;
	private List<Student> preferList;
	private List<Student> likedByList;

	@Column(name = "NAME")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "PID")
	public String getPid() {
		return pid;
	}

	@ManyToMany(targetEntity = Student.class, cascade = { CascadeType.PERSIST,
			CascadeType.MERGE })
	@JoinTable(
			name = "PPreference",
			joinColumns = @JoinColumn(name = "PID"),
			inverseJoinColumns = @JoinColumn(name = "SID"))
	public List<Student> getPreferList() {
		return preferList;
	}

	@ManyToMany(
			cascade = { CascadeType.PERSIST, CascadeType.MERGE },
			mappedBy = "Student",
			targetEntity = Student.class)
	public List<Student> getLikedByList() {
		return likedByList;
	}

}
