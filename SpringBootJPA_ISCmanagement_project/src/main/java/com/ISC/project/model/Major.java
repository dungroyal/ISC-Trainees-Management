package com.ISC.project.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
@Table(name = "majors")
public class Major  {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "createdBy")
	private String createdBy;
	
	@Column(name = "updatedBy")
	private String updatedBy;
	
	@CreatedDate
	@Column(name = "createdDate")
	private LocalDateTime createdDate;
	
	@LastModifiedDate
	@Column(name = "updatedDate")
	private LocalDateTime updatedDate;
	
	@Column(nullable = false, length = 50)
	private String codeMajor;
	
	@Column(nullable = false, length = 50)
	private String nameMajor;
	
	@Column(nullable = false, length = 2000)
	private String descriptionMajor;

	
	//mapping to course
	@OneToMany
	@JoinColumn(name = "course_id")
	    private List<Course> courses = new ArrayList<>();
	
	public String getCodeMajor() {
		return codeMajor;
	}

	public void setCodeMajor(String codeMajor) {
		this.codeMajor = codeMajor;
	}

	public String getNameMajor() {
		return nameMajor;
	}

	public void setNameMajor(String nameMajor) {
		this.nameMajor = nameMajor;
	}

	public String getDescriptionMajor() {
		return descriptionMajor;
	}

	public void setDescriptionMajor(String descriptionMajor) {
		this.descriptionMajor = descriptionMajor;
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

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public LocalDateTime getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(LocalDateTime updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Major(Long id, String createdBy, String updatedBy, LocalDateTime createdDate, LocalDateTime updatedDate, String codeMajor,
			String nameMajor, String descriptionMajor) {
		super();
		this.id = id;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
		this.createdDate = createdDate;
		this.updatedDate = updatedDate;
		this.codeMajor = codeMajor;
		this.nameMajor = nameMajor;
		this.descriptionMajor = descriptionMajor;
	}

	public Major(Long id) {
		super();
		this.id = id;
	}

	public Major() {
		super();
	}

		
	
 
}
