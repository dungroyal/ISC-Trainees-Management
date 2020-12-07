package com.ISC.project.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "schools")
public class School {
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
	
	@Column(nullable = false, length = 100)
	private String nameShc;
	
	@Column(nullable = false)
	private String addressShc;
	
	@Column(nullable = false, length = 150)
	private String contactPerson;
	
	@Column(length = 50)
	private String websiteShc;
	
	@Column(length = 1000)
	private String noteShc;

	//mapping to student
	@JsonBackReference
	@OneToMany(
	        cascade = CascadeType.ALL
	    )
	    private List<Student> student = new ArrayList<>();
	
	public String getNameShc() {
		return nameShc;
	}

	public void setNameShc(String nameShc) {
		this.nameShc = nameShc;
	}

	public String getAddressShc() {
		return addressShc;
	}

	public void setAddressShc(String addressShc) {
		this.addressShc = addressShc;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public String getWebsiteShc() {
		return websiteShc;
	}

	public void setWebsiteShc(String websiteShc) {
		this.websiteShc = websiteShc;
	}

	public String getNoteShc() {
		return noteShc;
	}

	public void setNoteShc(String noteShc) {
		this.noteShc = noteShc;
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

	public List<Student> getStudent() {
		return student;
	}

	public void setStudent(List<Student> student) {
		this.student = student;
	}

	public LocalDateTime getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(LocalDateTime updatedDate) {
		this.updatedDate = updatedDate;
	}

	public School(Long id, String createdBy, String updatedBy, LocalDateTime createdDate, LocalDateTime updatedDate, String nameShc,
			String addressShc, String contactPerson, String websiteShc, String noteShc) {
		super();
		this.id = id;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
		this.createdDate = createdDate;
		this.updatedDate = updatedDate;
		this.nameShc = nameShc;
		this.addressShc = addressShc;
		this.contactPerson = contactPerson;
		this.websiteShc = websiteShc;
		this.noteShc = noteShc;
	}

	public School() {
		super();
	}

	public School(Long id) {
		super();
		this.id = id;
	}
	
	
}
