package com.ISC.project.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "companies")
public class Company extends BaseEntity{
	@Column(nullable = false, length = 50)
	private String nameCom;
	
	@Column(length = 50)
	private String addresCom;
	
	@Column(length = 50)
	private String contactPerson;
	
	@Column(length = 50)
	private String websiteCom;
	
	@Column(length = 100)
	private String statusCom;
	
	@Column(length = 100)
	private String noteCom;

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
	
	

}
