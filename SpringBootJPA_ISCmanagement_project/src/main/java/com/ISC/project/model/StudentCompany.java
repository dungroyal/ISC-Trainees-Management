package com.ISC.project.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
@Entity
@Table(name = "StudentCompany")
public class StudentCompany implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private EmbemdedStudentCompanyId id = new EmbemdedStudentCompanyId();

	//map to student
	@JsonBackReference
	@ManyToOne(optional = false)
	@MapsId("studentId")
	private Student student;

	//map to company
	@JsonBackReference
	@ManyToOne(optional = false)
	@MapsId("companyId")
	private Company company;

	public StudentCompany(EmbemdedStudentCompanyId id, Student student, Company company) {
		super();
		this.id = id;
		this.student = student;
		this.company = company;
	}

	public StudentCompany(EmbemdedStudentCompanyId id) {
		super();
		this.id = id;
	}

	public StudentCompany() {
		super();
	}

	public EmbemdedStudentCompanyId getId() {
		return id;
	}

	public void setId(EmbemdedStudentCompanyId id) {
		this.id = id;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	//hash code
	@Override
	public int hashCode() {
		return Objects.hash(student, company);
	}

	//Override equals
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;

		if (o == null || getClass() != o.getClass())
			return false;

		StudentCompany that = (StudentCompany) o;
		return Objects.equals(this.student, that.student) &&
				Objects.equals(this.company, that.company);
	}	

}
