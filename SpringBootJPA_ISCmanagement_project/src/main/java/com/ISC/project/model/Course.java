package com.ISC.project.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
@Table(name = "courses")
public class Course {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "createdBy")
	private String createdBy;
	
	@Column(name = "updatedBy")
	private String updatedBy;
	
	@CreatedDate
	@Column(name = "createdDate")
	private Date createdDate;
	
	@LastModifiedDate
	@Column(name = "updatedDate")
	private Date updatedDate;
	
	@Column(nullable = false, length = 50, unique = true)
	private String codeCourse;
	
	@Column(nullable = false, length = 200)
	private String nameCourse;
	
	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date startDay;
	
	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date endDay;
	
	@Column(length = 150)
	private StatusCourse statusCourse;

	//mapping to major
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "major_id")
    private Major major;
	
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

	

	public StatusCourse getStatusCourse() {
		return statusCourse;
	}

	public void setStatusCourse(StatusCourse statusCourse) {
		this.statusCourse = statusCourse;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	

	public Course(Long id, String createdBy, String updatedBy, Date createdDate, Date updatedDate, String codeCourse,
			String nameCourse, Date startDay, Date endDay, StatusCourse statusCourse) {
		super();
		this.id = id;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
		this.createdDate = createdDate;
		this.updatedDate = updatedDate;
		this.codeCourse = codeCourse;
		this.nameCourse = nameCourse;
		this.startDay = startDay;
		this.endDay = endDay;
		this.statusCourse = statusCourse;
	}

	public Course() {
		super();
	}

	public Course(Long id) {
		super();
		this.id = id;
	}
	
	
}
