package com.ISC.project.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "intakes")
public class Intake {
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
	
	@Column(nullable = false, length = 50, unique = true)
	private String codeIntake;
	
	@Column(nullable = false, length = 200)
	private String nameIntake;
	
	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date startDay;
	
	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date endDay;
	
	@Column(length = 150)
	private StatusIntake statusIntake;

	//mapping to major
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Major major;
	
	// mapping to intakeSubject
	@OneToMany(mappedBy = "intake")
	private List<IntakeSubject> intakeSubject = new ArrayList<>();

	// mapping to intakeStudent
	@OneToMany(mappedBy = "intake")
	private List<IntakeStudent> intakeStudent = new ArrayList<>();
	
	public Major getMajor() {
		return major;
	}

	public void setMajor(Major major) {
		this.major = major;
	}

	public String getCodeIntake() {
		return codeIntake;
	}

	public void setCodeIntake(String codeIntake) {
		this.codeIntake = codeIntake;
	}

	public String getNameIntake() {
		return nameIntake;
	}

	public void setNameIntake(String nameIntake) {
		this.nameIntake = nameIntake;
	}

	public Date getStartDay() {
		return startDay;
	}

	public void setStartDay(Date startDay) {
		this.startDay = startDay;
	}

	public Date getEndDay() {
		return endDay;
	}

	public void setEndDay(Date endDay) {
		this.endDay = endDay;
	}

	

	public StatusIntake getStatusIntake() {
		return statusIntake;
	}

	public void setStatusIntake(StatusIntake statusIntake) {
		this.statusIntake = statusIntake;
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

	

	public Intake(Long id, String createdBy, String updatedBy, LocalDateTime createdDate, LocalDateTime updatedDate, String codeIntake,
			String nameIntake, Date startDay, Date endDay, StatusIntake statusIntake) {
		super();
		this.id = id;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
		this.createdDate = createdDate;
		this.updatedDate = updatedDate;
		this.codeIntake = codeIntake;
		this.nameIntake = nameIntake;
		this.startDay = startDay;
		this.endDay = endDay;
		this.statusIntake = statusIntake;
	}

	public Intake() {
		super();
	}

	public Intake(Long id) {
		super();
		this.id = id;
	}
	
}
