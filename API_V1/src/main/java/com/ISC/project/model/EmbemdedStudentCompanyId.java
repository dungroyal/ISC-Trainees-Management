package com.ISC.project.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class EmbemdedStudentCompanyId implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(name = "student_id",nullable = false)
	private Long studentId;
	
	@Column(name = "company_id",nullable = false)
	private Long companyId;

	public EmbemdedStudentCompanyId(Long studentId, Long companyId) {
		super();
		this.studentId = studentId;
		this.companyId = companyId;
	}

	public EmbemdedStudentCompanyId() {
		super();
	}

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	//hash code
		 @Override
		    public int hashCode() {
		        return Objects.hash(studentId, companyId);
		    }
		
		//Override equals
		@Override
	   public boolean equals(Object o) {
	       if (this == o) return true;

	       if (o == null || getClass() != o.getClass())
	           return false;

	       EmbemdedStudentCompanyId that = (EmbemdedStudentCompanyId) o;
	       return Objects.equals(this.studentId, that.studentId) &&
	              Objects.equals(this.companyId, that.companyId);
	   }
}
