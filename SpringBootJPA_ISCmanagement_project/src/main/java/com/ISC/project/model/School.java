package com.ISC.project.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "schools")
public class School extends BaseEntity{
	
	@Column(nullable = false, length = 50)
	private String nameShc;
	
	@Column(nullable = false, length = 50)
	private String addressShc;
	
	@Column(nullable = false, length = 150)
	private String contactPerson;
	
	@Column(length = 50)
	private String websiteShc;
	
	@Column(length = 150)
	private String noteShc;

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
	
	
}
