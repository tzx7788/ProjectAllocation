package org.ProjectAllocation.model;

import javax.persistence.*;

@Entity
@Table(name = StudentPreferenceItem.TABLENAME)
public class StudentPreferenceItem extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -593070395833309657L;

	public static final String TABLENAME = "SPreference";
	public static final String COL_PID = "PID";
	public static final String COL_SID = "SID";
	public static final String COL_WEIGHT = "WEIGHT";
	public static final String COL_PKEY = "PKEY";

	private Professor professor;
	private Student student;
	private Integer weight;
	private Integer key;

	public StudentPreferenceItem() {
	}
	
	public StudentPreferenceItem(Student student, Professor professor, Integer weight) {
		super();
		this.professor = professor;
		this.student = student;
		this.weight = weight;
	}

	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
	@JoinColumn(name = COL_PID)
	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name=COL_SID,nullable=true)
	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	@Column(name = COL_WEIGHT)
	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	@Column(name = COL_PKEY)
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getKey() {
		return key;
	}

	public void setKey(Integer key) {
		this.key = key;
	}
	
	
}
