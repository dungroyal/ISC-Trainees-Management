package com.ISC.project.model;

import java.util.Objects;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity
@Table(name = "CourseStudent")
public class CourseStudent {
	@EmbeddedId
	private EmbemdedCourseStudentId id = new EmbemdedCourseStudentId();
	
	//map to course
			@ManyToOne(optional = false)
			@MapsId("courseId")
			private Course course;
			
			//map to student
			@ManyToOne(optional = false)
			@MapsId("studentId")
			private Student student;

			public CourseStudent(EmbemdedCourseStudentId id, Course course, Student student) {
				super();
				this.id = id;
				this.course = course;
				this.student = student;
			}

			public CourseStudent() {
				super();
			}

			public EmbemdedCourseStudentId getId() {
				return id;
			}

			public void setId(EmbemdedCourseStudentId id) {
				this.id = id;
			}

			public Course getCourse() {
				return course;
			}

			public void setCourse(Course course) {
				this.course = course;
			}

			public Student getStudent() {
				return student;
			}

			public void setStudent(Student student) {
				this.student = student;
			}
			
			//hash code
			 @Override
			    public int hashCode() {
			        return Objects.hash(course, student);
			    }
			
			//Override equals
			@Override
		   public boolean equals(Object o) {
		       if (this == o) return true;

		       if (o == null || getClass() != o.getClass())
		           return false;

		       CourseStudent that = (CourseStudent) o;
		       return Objects.equals(this.course, that.course) &&
		              Objects.equals(this.student, that.student);
		   }
}
