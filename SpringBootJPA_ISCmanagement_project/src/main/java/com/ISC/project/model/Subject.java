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

import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "subjects")
public class Subject{
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
	
	@Column(nullable = false, unique = true, length = 50)
	@Schema(description = "Code Subject")
	private String codeSub;
	
	@Column(nullable = false, length = 50)
	private String nameSub;
	
	@Column(nullable = false, length = 50)
	private Double creditSub;
	
	@Column(nullable = false, length = 50)
	private Double passCore;
	
	@Column(nullable = false)
	private StatusAc statusSub;
	
	@Column(length = 1000)
	private String noteSub;

	public String getCodeSub() {
		return codeSub;
	}

	public void setCodeSub(String codeSub) {
		this.codeSub = codeSub;
	}

	public String getNameSub() {
		return nameSub;
	}

	public void setNameSub(String nameSub) {
		this.nameSub = nameSub;
	}

	public Double getCreditSub() {
		return creditSub;
	}

	public void setCreditSub(Double creditSub) {
		this.creditSub = creditSub;
	}

	public Double getPassCore() {
		return passCore;
	}

	public void setPassCore(Double passCore) {
		this.passCore = passCore;
	}

	public StatusAc getStatusSub() {
		return statusSub;
	}

	public void setStatusSub(StatusAc statusSub) {
		this.statusSub = statusSub;
	}

	public String getNoteSub() {
		return noteSub;
	}

	public void setNoteSub(String noteSub) {
		this.noteSub = noteSub;
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

	public Subject(Long id, String createdBy, String updatedBy, LocalDateTime createdDate, LocalDateTime updatedDate, String codeSub,
			String nameSub, Double creditSub, Double passCore, StatusAc statusSub, String noteSub) {
		super();
		this.id = id;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
		this.createdDate = createdDate;
		this.updatedDate = updatedDate;
		this.codeSub = codeSub;
		this.nameSub = nameSub;
		this.creditSub = creditSub;
		this.passCore = passCore;
		this.statusSub = statusSub;
		this.noteSub = noteSub;
	}

	public Subject() {
		super();
	}

	public Subject(Long id) {
		super();
		this.id = id;
	}
	
	
	
}
