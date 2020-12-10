package com.ISC.project.model;


import java.time.LocalDateTime;
import java.util.ArrayList;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "university")
public class University {
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
	private String nameUni;

	@Column(nullable = false)
	private String addressUni;

	@Column(nullable = false, length = 150)
	private String contactPerson;

	@Column(length = 50)
	private String websiteUni;

	@Column(length = 1000)
	private String noteUni;

	//mapping to student
	@JsonBackReference
	@OneToMany(mappedBy = "university")
	private List<Student> student = new ArrayList<>();

	public String getNameUni() {
		return nameUni;
	}

	public void setNameUni(String nameUni) {
		this.nameUni = nameUni;
	}

	public String getAddressUni() {
		return addressUni;
	}

	public void setAddressUni(String addressUni) {
		this.addressUni = addressUni;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public String getWebsiteUni() {
		return websiteUni;
	}

	public void setWebsiteUni(String websiteUni) {
		this.websiteUni = websiteUni;
	}

	public String getNoteUni() {
		return noteUni;
	}

	public void setNoteUni(String noteUni) {
		this.noteUni = noteUni;
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

	public University(Long id, String createdBy, String updatedBy, LocalDateTime createdDate, LocalDateTime updatedDate, String nameUni,
			String addressUni, String contactPerson, String websiteUni, String noteUni) {
		super();
		this.id = id;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
		this.createdDate = createdDate;
		this.updatedDate = updatedDate;
		this.nameUni = nameUni;
		this.addressUni = addressUni;
		this.contactPerson = contactPerson;
		this.websiteUni = websiteUni;
		this.noteUni = noteUni;
	}

	public University() {
		super();
	}

	public University(Long id) {
		super();
		this.id = id;
	}

	public University(String createdBy, String updatedBy, String nameUni, String addressUni, String contactPerson,
			String websiteUni, String noteUni) {
		super();
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
		this.nameUni = nameUni;
		this.addressUni = addressUni;
		this.contactPerson = contactPerson;
		this.websiteUni = websiteUni;
		this.noteUni = noteUni;
	}

	
}
