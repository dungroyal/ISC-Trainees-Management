package com.ISC.project.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;

public class EmbemdedIntakeStudentId implements Serializable{
	@Column(name = "intake_id",nullable = false)
	private Long intakeId;
	
	@Column(name = "student_id",nullable = false)
	private Long studentId;

	public EmbemdedIntakeStudentId(Long intakeId, Long studentId) {
		super();
		this.intakeId = intakeId;
		this.studentId = studentId;
	}
	
	public EmbemdedIntakeStudentId() {
		super();
	}

	public Long getintakeId() {
		return intakeId;
	}

	public void setintakeId(Long intakeId) {
		this.intakeId = intakeId;
	}

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	//hash code
	 @Override
	    public int hashCode() {
	        return Objects.hash(intakeId, studentId);
	    }
	
	//Override equals
	@Override
  public boolean equals(Object o) {
      if (this == o) return true;

      if (o == null || getClass() != o.getClass())
          return false;

      EmbemdedIntakeStudentId that = (EmbemdedIntakeStudentId) o;
      return Objects.equals(this.intakeId, that.intakeId) &&
             Objects.equals(this.studentId, that.studentId);
  }
}
