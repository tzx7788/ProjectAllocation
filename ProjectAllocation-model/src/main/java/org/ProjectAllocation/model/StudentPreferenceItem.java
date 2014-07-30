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

	private Professor professor;
	private Student student;
	private Integer weight;

	public StudentPreferenceItem() {
	}
	
	public StudentPreferenceItem(Student student, Professor professor) {
		super();
		this.professor = professor;
		this.student = student;
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
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}
}
