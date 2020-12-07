package com.ISC.project.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "rooms")
public class Room extends BaseEntity{
	@Column(nullable = false, length = 50)
	private String codeRoom;
	
	@Column(nullable = false, length = 50)
	private String nameRoom;
	
	@Column(nullable = false)
	private TypeRoom typeRoom;
	
	@Column(length = 50)
	private String statusRoom;
	
	@Column(length = 150)
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

	public String getStatusRoom() {
		return statusRoom;
	}

	public void setStatusRoom(String statusRoom) {
		this.statusRoom = statusRoom;
	}

	public String getNoteRoom() {
		return noteRoom;
	}

	public void setNoteRoom(String noteRoom) {
		this.noteRoom = noteRoom;
	}

	
	
}
