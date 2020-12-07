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
@Table(name = "companies")
public class Company {
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
	
	@Column(nullable = false)
	private String nameCom;
	
	@Column(nullable = false)
	private String addresCom;
	
	@Column(nullable = false,length = 100)
	private String contactPerson;
	
	@Column(length = 100)
	private String websiteCom;
	
	@Column(nullable = false,length = 100)
	private String statusCom;
	
	@Column(length = 2000)
	private String noteCom;

	//mapping to student
		@ManyToOne(fetch = FetchType.LAZY)
	    private Student student;
	public String getNameCom() {
		return nameCom;
	}

	public void setNameCom(String nameCom) {
		this.nameCom = nameCom;
	}

	public String getAddresCom() {
		return addresCom;
	}

	public void setAddresCom(String addresCom) {
		this.addresCom = addresCom;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public String getWebsiteCom() {
		return websiteCom;
	}

	public void setWebsiteCom(String websiteCom) {
		this.websiteCom = websiteCom;
	}

	public String getStatusCom() {
		return statusCom;
	}

	public void setStatusCom(String statusCom) {
		this.statusCom = statusCom;
	}

	public String getNoteCom() {
		return noteCom;
	}

	public void setNoteCom(String noteCom) {
		this.noteCom = noteCom;
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

	public Company() {
		super();
	}

	public Company(Long id, String createdBy, String updatedBy, Date createdDate, Date updatedDate, String nameCom,
			String addresCom, String contactPerson, String websiteCom, String statusCom, String noteCom) {
		super();
		this.id = id;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
		this.createdDate = createdDate;
		this.updatedDate = updatedDate;
		this.nameCom = nameCom;
		this.addresCom = addresCom;
		this.contactPerson = contactPerson;
		this.websiteCom = websiteCom;
		this.statusCom = statusCom;
		this.noteCom = noteCom;
	}

	public Company(Long id) {
		super();
		this.id = id;
	}

	
	
	
	
	

}
