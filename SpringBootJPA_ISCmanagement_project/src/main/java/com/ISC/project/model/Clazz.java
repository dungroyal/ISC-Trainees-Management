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
@Table(name = "clazzs")
public class Clazz {
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
	private String nameClazz;
	
	@Column(name = "numOfStu")
	private Long numOfStu;
	
	@Column(name = "pointGraduate", nullable = false, length = 50)
	private Double pointGra;
	
	@Column(nullable = false)
	private StatusAc clazzStatus;

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

	public String getNameClazz() {
		return nameClazz;
	}

	public void setNameClazz(String nameClazz) {
		this.nameClazz = nameClazz;
	}

	public Long getNumOfStu() {
		return numOfStu;
	}

	public void setNumOfStu(Long numOfStu) {
		this.numOfStu = numOfStu;
	}

	public Double getPointGra() {
		return pointGra;
	}

	public void setPointGra(Double pointGra) {
		this.pointGra = pointGra;
	}

	public StatusAc getClazzStatus() {
		return clazzStatus;
	}

	public void setClazzStatus(StatusAc clazzStatus) {
		this.clazzStatus = clazzStatus;
	}

	public Clazz(Long id, String createdBy, String updatedBy, LocalDateTime createdDate, LocalDateTime updatedDate,
			String nameClazz, Long numOfStu, Double pointGra, StatusAc clazzStatus) {
		super();
		this.id = id;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
		this.createdDate = createdDate;
		this.updatedDate = updatedDate;
		this.nameClazz = nameClazz;
		this.numOfStu = numOfStu;
		this.pointGra = pointGra;
		this.clazzStatus = clazzStatus;
	}

	public Clazz(String createdBy, String updatedBy, LocalDateTime createdDate, LocalDateTime updatedDate,
			String nameClazz, Long numOfStu, Double pointGra, StatusAc clazzStatus) {
		super();
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
		this.createdDate = createdDate;
		this.updatedDate = updatedDate;
		this.nameClazz = nameClazz;
		this.numOfStu = numOfStu;
		this.pointGra = pointGra;
		this.clazzStatus = clazzStatus;
	}

	public Clazz(String createdBy, String updatedBy, String nameClazz, Long numOfStu, Double pointGra,
			StatusAc clazzStatus) {
		super();
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
		this.nameClazz = nameClazz;
		this.numOfStu = numOfStu;
		this.pointGra = pointGra;
		this.clazzStatus = clazzStatus;
	}

	public Clazz() {
		super();
	}

	public Clazz(Long id) {
		super();
		this.id = id;
	}
	
	
}

