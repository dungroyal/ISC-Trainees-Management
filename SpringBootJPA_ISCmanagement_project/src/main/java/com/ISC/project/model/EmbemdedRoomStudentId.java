package com.ISC.project.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;

public class EmbemdedRoomStudentId implements Serializable{
	@Column(name = "room_id",nullable = false)
	private Long roomId;
	
	@Column(name = "student_id",nullable = false)
	private Long studentId;

	public EmbemdedRoomStudentId(Long roomId, Long studentId) {
		super();
		this.roomId = roomId;
		this.studentId = studentId;
	}

	public EmbemdedRoomStudentId() {
		super();
	}

	public Long getRoomId() {
		return roomId;
	}

	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}
	
	//hash code
		 @Override
		    public int hashCode() {
		        return Objects.hash(roomId, studentId);
		    }
		
		//Override equals
		@Override
	   public boolean equals(Object o) {
	       if (this == o) return true;

	       if (o == null || getClass() != o.getClass())
	           return false;

	       EmbemdedRoomStudentId that = (EmbemdedRoomStudentId) o;
	       return Objects.equals(this.roomId, that.roomId) &&
	              Objects.equals(this.studentId, that.studentId);
	   }
}
