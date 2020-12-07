package com.ISC.project.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "lecturers")
public class Lecturer extends BaseEntity{
	
	@Column(nullable = false, length = 50)
	private String CodeLec;
	
	@Column(nullable = false, length = 50)
	private String firstName;
	
	@Column(nullable = false, length = 50)
	private String lastName;
	
	@Column(nullable = false, length = 50)
	private String addressLec;
	
	@Column(nullable = false, length = 50)
	private String phoneLec;
	
	@Column(nullable = false, length = 50)
	private String emailLec;
	
	@Column(nullable = false, length = 50)
	private String degree;
	
	@Column(length = 100)
	private String image;
	
	@Column(length = 50)
	private String statusLec;
	
	@Column(length = 150)
	private String noteLec;

	public String getCodeLec() {
		return CodeLec;
	}

	public void setCodeLec(String codeLec) {
		CodeLec = codeLec;
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

	public String getStatusLec() {
		return statusLec;
	}

	public void setStatusLec(String statusLec) {
		this.statusLec = statusLec;
	}

	public String getNoteLec() {
		return noteLec;
	}

	public void setNoteLec(String noteLec) {
		this.noteLec = noteLec;
	}

	
	

}
