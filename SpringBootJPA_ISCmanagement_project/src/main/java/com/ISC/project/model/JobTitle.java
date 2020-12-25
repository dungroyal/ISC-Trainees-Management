package com.ISC.project.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;


@Entity
@Table(name = "jobTitles")
public class JobTitle {
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
	
	@Column(name = "name",nullable = false, length = 100, unique = true)
	private String nameJob;
	
	@Column(nullable = false)
	private StatusAc jobStatus;

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

	public String getNameJob() {
		return nameJob;
	}

	public void setNameJob(String nameJob) {
		this.nameJob = nameJob;
	}

	public StatusAc getJobStatus() {
		return jobStatus;
	}

	public void setJobStatus(StatusAc jobStatus) {
		this.jobStatus = jobStatus;
	}

	public JobTitle(Long id, String createdBy, String updatedBy, LocalDateTime createdDate, LocalDateTime updatedDate,
			String nameJob, StatusAc jobStatus) {
		super();
		this.id = id;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
		this.createdDate = createdDate;
		this.updatedDate = updatedDate;
		this.nameJob = nameJob;
		this.jobStatus = jobStatus;
	}

	public JobTitle(String createdBy, String updatedBy, LocalDateTime createdDate, LocalDateTime updatedDate,
			String nameJob, StatusAc jobStatus) {
		super();
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
		this.createdDate = createdDate;
		this.updatedDate = updatedDate;
		this.nameJob = nameJob;
		this.jobStatus = jobStatus;
	}

	public JobTitle(Long id, String createdBy, String updatedBy, String nameJob, StatusAc jobStatus) {
		super();
		this.id = id;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
		this.nameJob = nameJob;
		this.jobStatus = jobStatus;
	}
	
	
}
