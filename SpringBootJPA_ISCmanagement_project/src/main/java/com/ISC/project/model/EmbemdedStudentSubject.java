package com.ISC.project.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class EmbemdedStudentSubject implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name = "student_id",nullable = false)
	private Long studentId;
	
	@Column(name = "subject_id",nullable = false)
	private Long subjectId;

	public EmbemdedStudentSubject(Long studentId, Long subjectId) {
		super();
		this.studentId = studentId;
		this.subjectId = subjectId;
	}

	public EmbemdedStudentSubject() {
		super();
	}

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public Long getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	//hash code
	 @Override
	    public int hashCode() {
	        return Objects.hash(studentId, subjectId);
	    }
	
	//Override equals
	@Override
   public boolean equals(Object o) {
       if (this == o) return true;

       if (o == null || getClass() != o.getClass())
           return false;

       EmbemdedStudentSubject that = (EmbemdedStudentSubject) o;
       return Objects.equals(this.studentId, that.studentId) &&
              Objects.equals(this.subjectId, that.subjectId);
   }
}
