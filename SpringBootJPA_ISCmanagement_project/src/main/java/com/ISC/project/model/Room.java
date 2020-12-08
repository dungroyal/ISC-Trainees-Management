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
@Table(name = "rooms")
public class Room {
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
	private String codeRoom;
	
	@Column(nullable = false, length = 50)
	private String nameRoom;
	
	@Column(nullable = false)
	private TypeRoom typeRoom;
	
	@Column(nullable = false)
	private StatusAc statusRoom;
	
	@Column(length = 1000)
	private String noteRoom;

	public String getCodeRoom() {
		return codeRoom;
	}

	public void setCodeRoom(String codeRoom) {
		this.codeRoom = codeRoom;
	}

	public String getNameRoom() {
		return nameRoom;
	}

	public void setNameRoom(String nameRoom) {
		this.nameRoom = nameRoom;
	}

	public TypeRoom getTypeRoom() {
		return typeRoom;
	}

	public void setTypeRoom(TypeRoom typeRoom) {
		this.typeRoom = typeRoom;
	}

	public StatusAc getStatusRoom() {
		return statusRoom;
	}

	public void setStatusRoom(StatusAc statusRoom) {
		this.statusRoom = statusRoom;
	}

	public String getNoteRoom() {
		return noteRoom;
	}

	public void setNoteRoom(String noteRoom) {
		this.noteRoom = noteRoom;
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

	public Room(Long id, String createdBy, String updatedBy, LocalDateTime createdDate, LocalDateTime updatedDate, String codeRoom,
			String nameRoom, TypeRoom typeRoom, StatusAc statusRoom, String noteRoom) {
		super();
		this.id = id;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
		this.createdDate = createdDate;
		this.updatedDate = updatedDate;
		this.codeRoom = codeRoom;
		this.nameRoom = nameRoom;
		this.typeRoom = typeRoom;
		this.statusRoom = statusRoom;
		this.noteRoom = noteRoom;
	}

	public Room() {
		super();
	}

	public Room(Long id) {
		super();
		this.id = id;
	}

	
	
	
}
