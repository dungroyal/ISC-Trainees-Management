package com.ISC.project.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;

public class EmbemdedCourseStudentId implements Serializable{
	@Column(name = "course_id",nullable = false)
	private Long courseId;
	
	@Column(name = "student_id",nullable = false)
	private Long studentId;

	public EmbemdedCourseStudentId(Long courseId, Long studentId) {
		super();
		this.courseId = courseId;
		this.studentId = studentId;
	}
	
	public EmbemdedCourseStudentId() {
		super();
	}

	public Long getCourseId() {
		return courseId;
	}

	public void setCourseId(Long courseId) {
		this.courseId = courseId;
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
	        return Objects.hash(courseId, studentId);
	    }
	
	//Override equals
	@Override
  public boolean equals(Object o) {
      if (this == o) return true;

      if (o == null || getClass() != o.getClass())
          return false;

      EmbemdedCourseStudentId that = (EmbemdedCourseStudentId) o;
      return Objects.equals(this.courseId, that.courseId) &&
             Objects.equals(this.studentId, that.studentId);
  }
}
