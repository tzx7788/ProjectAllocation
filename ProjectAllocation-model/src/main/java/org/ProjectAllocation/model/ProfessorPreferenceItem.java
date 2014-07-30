package org.ProjectAllocation.model;

import javax.persistence.*;

@Entity
@Table(name = ProfessorPreferenceItem.TABLENAME)
public class ProfessorPreferenceItem extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2673054909058879092L;

	public static final String TABLENAME = "PPreference";
	public static final String COL_PID = "PID";
	public static final String COL_SID = "SID";
	public static final String COL_WEIGHT = "WEIGHT";

	private Professor professor;
	private Student student;
	private Integer weight;

	public ProfessorPreferenceItem() {
	}

	public ProfessorPreferenceItem(Professor professor, Student student) {
		super();
		this.professor = professor;
		this.student = student;
	}

	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name=COL_PID,nullable=true)
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
