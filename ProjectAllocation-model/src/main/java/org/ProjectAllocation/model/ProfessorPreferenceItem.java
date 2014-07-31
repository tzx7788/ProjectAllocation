package org.ProjectAllocation.model;


import javax.persistence.*;

import org.json.JSONObject;


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
	public static final String COL_PKEY = "PKEY";

	private Professor professor;
	private Student student;
	private Integer weight;
	private Integer key;
	
	public ProfessorPreferenceItem() {
	}

	public ProfessorPreferenceItem(Professor professor, Student student, Integer weight) {
		super();
		this.professor = professor;
		this.student = student;
		this.weight = weight;
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
	
	public JSONObject toJSONObject() {
		JSONObject result = new JSONObject();
		result.put("sid", this.getStudent().getSid());
		result.put("pid", this.getStudent().getSid());
		result.put("weight", this.getWeight().toString());
		return result;
	}
	
}
