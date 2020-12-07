package com.ISC.project.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "students")
public class Student extends BaseEntity{
	
	@Column(nullable = false, length = 50)
	private String codeStu;
	
	@Column(nullable = false, length = 50)
	private String firstName;
	
	@Column(nullable = false, length = 50)
	private String lastName;
	
	@Column(nullable = false, length = 100)
	private String addressStu;
	
	@Column(length = 50)
	private String emailStu;
	
	@Column(nullable = false)
	private TypeStudent typeStu;
	
	@Column(nullable = false, length = 50)
	private Double gpa;
	
	@Column(nullable = false, length = 50)
	private String workingStatus;
	
	@Column(nullable = false)
	private String image;
	
	@Column(length = 150)
	private String noteStu;

	public String getCodeStu() {
		return codeStu;
	}

	public void setCodeStu(String codeStu) {
		this.codeStu = codeStu;
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

	public String getAddressStu() {
		return addressStu;
	}

	public void setAddressStu(String addressStu) {
		this.addressStu = addressStu;
	}

	public String getEmailStu() {
		return emailStu;
	}

	public void setEmailStu(String emailStu) {
		this.emailStu = emailStu;
	}

	
	public TypeStudent getTypeStu() {
		return typeStu;
	}

	public void setTypeStu(TypeStudent typeStu) {
		this.typeStu = typeStu;
	}

	public Double getGpa() {
		return gpa;
	}

	public void setGpa(Double gpa) {
		this.gpa = gpa;
	}

	public String getWorkingStatus() {
		return workingStatus;
	}

	public void setWorkingStatus(String workingStatus) {
		this.workingStatus = workingStatus;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getNoteStu() {
		return noteStu;
	}

	public void setNoteStu(String noteStu) {
		this.noteStu = noteStu;
	}
	
	

}
