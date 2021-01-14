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
@Table(name = "lecturers")
public class Lecturer {
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
	
	
	@Column(nullable = false, length = 50,unique = true)
	private String codeLec;
	
	@Column(nullable = false, length = 50)
	private String firstName;
	
	@Column(nullable = false, length = 50)
	private String lastName;
	
	@Column(length = 100)
	private String addressLec;
	
	@Column(nullable = false, length = 50)
	private String phoneLec;
	
	@Column(nullable = false, length = 50, unique = true)
	private String emailLec;
	
	@Column(nullable = false, length = 50)
	private String degree;
	
	@Column(nullable = false,columnDefinition = "TEXT")
	private String image;
	
	@Column(length = 50)
	private StatusAc statusLec;
	
	@Column(length = 2000)
	private String noteLec;

	public String getCodeLec() {
		return codeLec;
	}

	public void setCodeLec(String codeLec) {
		this.codeLec = codeLec;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddressLec() {
		return addressLec;
	}

	public void setAddressLec(String addressLec) {
		this.addressLec = addressLec;
	}

	public String getPhoneLec() {
		return phoneLec;
	}

	public void setPhoneLec(String phoneLec) {
		this.phoneLec = phoneLec;
	}

	public String getEmailLec() {
		return emailLec;
	}

	public void setEmailLec(String emailLec) {
		this.emailLec = emailLec;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public StatusAc getStatusLec() {
		return statusLec;
	}

	public void setStatusLec(StatusAc statusLec) {
		this.statusLec = statusLec;
	}

	public String getNoteLec() {
		return noteLec;
	}

	public void setNoteLec(String noteLec) {
		this.noteLec = noteLec;
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

	

	public Lecturer(Long id, String createdBy, String updatedBy, LocalDateTime createdDate, LocalDateTime updatedDate, String codeLec,
			String firstName, String lastName, String addressLec, String phoneLec, String emailLec, String degree,
			String image, StatusAc statusLec, String noteLec) {
		super();
		this.id = id;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
		this.createdDate = createdDate;
		this.updatedDate = updatedDate;
		this.codeLec = codeLec;
		this.firstName = firstName;
		this.lastName = lastName;
		this.addressLec = addressLec;
		this.phoneLec = phoneLec;
		this.emailLec = emailLec;
		this.degree = degree;
		this.image = image;
		this.statusLec = statusLec;
		this.noteLec = noteLec;
	}

	public Lecturer() {
		super();
	}

	public Lecturer(Long id) {
		super();
		this.id = id;
	}

	public Lecturer(String createdBy, String updatedBy,String codeLec, String firstName, String lastName, String addressLec, String phoneLec, String emailLec,
			String degree, String image, StatusAc statusLec, String noteLec) {
		super();
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
		this.codeLec = codeLec;
		this.firstName = firstName;
		this.lastName = lastName;
		this.addressLec = addressLec;
		this.phoneLec = phoneLec;
		this.emailLec = emailLec;
		this.degree = degree;
		this.image = image;
		this.statusLec = statusLec;
		this.noteLec = noteLec;
	}
}
