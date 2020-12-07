package com.ISC.project.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity
@Table(name = "MajorSubject")
public class MajorSubject implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private EmbemdedMajorSubjectId id = new EmbemdedMajorSubjectId();
	
	//map to major
	@ManyToOne(optional = false)
	@MapsId("majorId")
	private Major major;
	
	//map to subject
	@ManyToOne(optional = false)
	@MapsId("subjectId")
	private Subject subject;

	public MajorSubject() {
		super();
	}

	public MajorSubject(Major major, EmbemdedMajorSubjectId id, Subject subject) {
		super();
		this.major = major;
		this.id = id;
		this.subject = subject;
	}

	public EmbemdedMajorSubjectId getId() {
		return id;
	}

	public void setId(EmbemdedMajorSubjectId id) {
		this.id = id;
	}

	public Major getMajor() {
		return major;
	}

	public void setMajor(Major major) {
		this.major = major;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public MajorSubject(EmbemdedMajorSubjectId id) {
		super();
		this.id = id;
	}
	
	//hash code
	 @Override
	    public int hashCode() {
	        return Objects.hash(major, subject);
	    }
	
	//Override equals
	@Override
   public boolean equals(Object o) {
       if (this == o) return true;

       if (o == null || getClass() != o.getClass())
           return false;

       MajorSubject that = (MajorSubject) o;
       return Objects.equals(this.major, that.major) &&
              Objects.equals(this.subject, that.subject);
   }
}
