package com.ISC.project.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;


@Embeddable
public class EmbemdedMajorSubjectId implements Serializable{
	@Column(name = "major_id",nullable = false)
	private Long majorId;
	
	@Column(name = "subject_id",nullable = false)
	private Long subjectId;
	
	public EmbemdedMajorSubjectId() {
		super();
	}

	
	public EmbemdedMajorSubjectId(Long majorId, Long subjectId) {
		super();
		this.majorId = majorId;
		this.subjectId = subjectId;
	}

	

	public Long getMajorId() {
		return majorId;
	}


	public void setMajorId(Long majorId) {
		this.majorId = majorId;
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
		        return Objects.hash(majorId, subjectId);
		    }
		
		//Override equals
		@Override
	    public boolean equals(Object o) {
	        if (this == o) return true;
	 
	        if (o == null || getClass() != o.getClass())
	            return false;
	 
	        EmbemdedMajorSubjectId that = (EmbemdedMajorSubjectId) o;
	        return Objects.equals(this.majorId, that.majorId) &&
	               Objects.equals(this.subjectId, that.subjectId);
	    }

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
}
