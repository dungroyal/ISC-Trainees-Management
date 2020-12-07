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

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

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
	private Date createdDate;
	
	@LastModifiedDate
	@Column(name = "updatedDate")
	private Date updatedDate;
	
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
		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "student_id")
	    private Student student;
	
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

	public School(Long id, String createdBy, String updatedBy, Date createdDate, Date updatedDate, String nameShc,
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
