package com.ISC.project.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;

public class EmbemdedIntakeSubjectId implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "intake_id",nullable = false)
	private Long intakeId;
	
	@Column(name = "subject_id",nullable = false)
	private Long subjectId;

	public EmbemdedIntakeSubjectId(Long intakeId, Long subjectId) {
		super();
		this.intakeId = intakeId;
		this.subjectId = subjectId;
	}

	public EmbemdedIntakeSubjectId() {
		super();
	}

	public Long getintakeId() {
		return intakeId;
	}

	public void setintakeId(Long intakeId) {
		this.intakeId = intakeId;
	}

	public Long getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}
	//hash code
		 @Override
		    public int hashCode() {
		        return Objects.hash(intakeId, subjectId);
		    }
		
		//Override equals
		@Override
	  public boolean equals(Object o) {
	      if (this == o) return true;

	      if (o == null || getClass() != o.getClass())
	          return false;

	      EmbemdedIntakeSubjectId that = (EmbemdedIntakeSubjectId) o;
	      return Objects.equals(this.intakeId, that.intakeId) &&
	             Objects.equals(this.subjectId, that.subjectId);
	  }
}
