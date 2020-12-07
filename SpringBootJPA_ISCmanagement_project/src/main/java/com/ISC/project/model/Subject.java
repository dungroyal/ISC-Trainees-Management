package com.ISC.project.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "subjects")
public class Subject extends BaseEntity{
	
	@Column
	private String codeSub;
	
	@Column
	private String nameSub;
	
	@Column
	private Double creditSub;
	
	@Column
	private Double passCore;
	
	@Column
	private String statusSub;
	
	@Column
	private String noteSub;

	public String getCodeSub() {
		return codeSub;
	}

	public void setCodeSub(String codeSub) {
		this.codeSub = codeSub;
	}

	public String getNameSub() {
		return nameSub;
	}

	public void setNameSub(String nameSub) {
		this.nameSub = nameSub;
	}

	public Double getCreditSub() {
		return creditSub;
	}

	public void setCreditSub(Double creditSub) {
		this.creditSub = creditSub;
	}

	public Double getPassCore() {
		return passCore;
	}

	public void setPassCore(Double passCore) {
		this.passCore = passCore;
	}

	public String getStatusSub() {
		return statusSub;
	}

	public void setStatusSub(String statusSub) {
		this.statusSub = statusSub;
	}

	public String getNoteSub() {
		return noteSub;
	}

	public void setNoteSub(String noteSub) {
		this.noteSub = noteSub;
	}
	
	
}
