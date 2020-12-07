package com.ISC.project.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "courses")
public class Course extends BaseEntity {
	@Column(nullable = false, length = 50)
	private String codeCourse;
	
	@Column(nullable = false, length = 50)
	private String nameCourse;
	
	@Column(nullable = false)
	private Date startDay;
	
	@Column(nullable = false)
	private Date endDay;
	
	@Column(length = 150)
	private String statusCourse;

	public String getCodeCourse() {
		return codeCourse;
	}

	public void setCodeCourse(String codeCourse) {
		this.codeCourse = codeCourse;
	}

	public String getNameCourse() {
		return nameCourse;
	}

	public void setNameCourse(String nameCourse) {
		this.nameCourse = nameCourse;
	}

	public Date getStartDay() {
		return startDay;
	}

	public void setStartDay(Date startDay) {
		this.startDay = startDay;
	}

	public Date getEndDay() {
		return endDay;
	}

	public void setEndDay(Date endDay) {
		this.endDay = endDay;
	}

	public String getStatusCourse() {
		return statusCourse;
	}

	public void setStatusCourse(String statusCourse) {
		this.statusCourse = statusCourse;
	}
	
	
}
