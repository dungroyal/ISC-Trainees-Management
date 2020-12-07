package com.ISC.project.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;

public class EmbemdedRoomLecturerId implements Serializable{
	@Column(name = "room_id",nullable = false)
	private Long roomId;
	
	@Column(name = "lecturer_id",nullable = false)
	private Long lecturerId;

	public EmbemdedRoomLecturerId(Long roomId, Long lecturerId) {
		super();
		this.roomId = roomId;
		this.lecturerId = lecturerId;
	}

	public EmbemdedRoomLecturerId() {
		super();
	}

	public Long getRoomId() {
		return roomId;
	}

	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}

	public Long getLecturerId() {
		return lecturerId;
	}

	public void setLecturerId(Long lecturerId) {
		this.lecturerId = lecturerId;
	}
	
	//hash code
	 @Override
	    public int hashCode() {
	        return Objects.hash(roomId, lecturerId);
	    }
	
	//Override equals
	@Override
  public boolean equals(Object o) {
      if (this == o) return true;

      if (o == null || getClass() != o.getClass())
          return false;

      EmbemdedRoomLecturerId that = (EmbemdedRoomLecturerId) o;
      return Objects.equals(this.roomId, that.roomId) &&
             Objects.equals(this.lecturerId, that.lecturerId);
  }
}
