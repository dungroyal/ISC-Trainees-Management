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
@Table(name = "accounts_admin")
public class AccountAdmin {
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
	
	@Column(nullable = false, length = 50)
	private String firstName;

	@Column(nullable = false, length = 50)
	private String lastName;
	
	@Column(nullable = false, length = 100)
	private String address;
	
	@Column(nullable = false, length = 50)
	private String phone;
	
	@Column(length = 100,unique = true, nullable= false)
	private String email;
	
	@Column(nullable = false,columnDefinition = "TEXT")
	private String image;
	
	
	public AccountAdmin() {
		super();
	}

	public AccountAdmin(Long id, String createdBy, String updatedBy, LocalDateTime createdDate, LocalDateTime updatedDate,
			String firstName, String lastName, String address, String phone, String email, String image) {
		super();
		this.id = id;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
		this.createdDate = createdDate;
		this.updatedDate = updatedDate;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.phone = phone;
		this.email = email;
		this.image = image;
	}

	public AccountAdmin(String createdBy, String updatedBy, LocalDateTime createdDate, LocalDateTime updatedDate,
			String firstName, String lastName, String address, String phone, String email, String image) {
		super();
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
		this.createdDate = createdDate;
		this.updatedDate = updatedDate;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.phone = phone;
		this.email = email;
		this.image = image;
	}

	public AccountAdmin(String createdBy, String updatedBy, String firstName, String lastName, String address,
			String phone, String email, String image) {
		super();
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.phone = phone;
		this.email = email;
		this.image = image;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String addressAcc) {
		this.address = addressAcc;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return "AccountAdmin [id=" + id + ", createdBy=" + createdBy + ", updatedBy=" + updatedBy + ", createdDate="
				+ createdDate + ", updatedDate=" + updatedDate + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", address=" + address + ", phone=" + phone + ", email=" + email + ", image=" + image + "]";
	}
}
