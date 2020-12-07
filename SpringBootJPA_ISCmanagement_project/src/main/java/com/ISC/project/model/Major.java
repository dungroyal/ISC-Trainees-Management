package com.ISC.project.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "majors")
public class Major extends BaseEntity {
	@Column(nullable = false, length = 50)
	private String codeMajor;
	
	@Column(nullable = false, length = 50)
	private String nameMajor;
	
	@Column(nullable = false, length = 150)
	private String descriptionMajor;

	public String getCodeMajor() {
		return codeMajor;
	}

	public void setCodeMajor(String codeMajor) {
		this.codeMajor = codeMajor;
	}

	public String getNameMajor() {
		return nameMajor;
	}

	public void setNameMajor(String nameMajor) {
		this.nameMajor = nameMajor;
	}

	public String getDescriptionMajor() {
		return descriptionMajor;
	}

	public void setDescriptionMajor(String descriptionMajor) {
		this.descriptionMajor = descriptionMajor;
	}

		
	
 
}
