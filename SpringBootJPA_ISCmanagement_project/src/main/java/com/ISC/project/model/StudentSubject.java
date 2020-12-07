package com.ISC.project.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity
@Table(name = "StudentSubject")
public class StudentSubject implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private EmbemdedStudentSubject id = new EmbemdedStudentSubject();
	
	//map to major
		@ManyToOne(optional = false)
		@MapsId("studentId")
		private Student student;
		
	//map to subject
		@ManyToOne(optional = false)
		@MapsId("subjectId")
		private Subject subject;

		
		public StudentSubject(EmbemdedStudentSubject id, Student student, Subject subject) {
			super();
			this.id = id;
			this.student = student;
			this.subject = subject;
		}

		public StudentSubject() {
			super();
		}

		public StudentSubject(EmbemdedStudentSubject id) {
			super();
			this.id = id;
		}

		public EmbemdedStudentSubject getId() {
			return id;
		}

		public void setId(EmbemdedStudentSubject id) {
			this.id = id;
		}

		public Student getStudent() {
			return student;
		}

		public void setStudent(Student student) {
			this.student = student;
		}

		public Subject getSubject() {
			return subject;
		}

		public void setSubject(Subject subject) {
			this.subject = subject;
		}
		
		//hash code
		 @Override
		    public int hashCode() {
		        return Objects.hash(student, subject);
		    }
		
		//Override equals
		@Override
	   public boolean equals(Object o) {
	       if (this == o) return true;

	       if (o == null || getClass() != o.getClass())
	           return false;

	       StudentSubject that = (StudentSubject) o;
	       return Objects.equals(this.student, that.student) &&
	              Objects.equals(this.subject, that.subject);
	   }
}
