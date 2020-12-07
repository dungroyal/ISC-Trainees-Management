package com.ISC.project.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class EmbemdedRoomSubjectId implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "room_id",nullable = false)
	private Long roomId;
	
	@Column(name = "subject_id",nullable = false)
	private Long subjectId;

	public EmbemdedRoomSubjectId(Long roomId, Long subjectId) {
		super();
		this.roomId = roomId;
		this.subjectId = subjectId;
	}

	public EmbemdedRoomSubjectId() {
		super();
	}

	public Long getRoomId() {
		return roomId;
	}

	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}

	public Long getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}
	
	//hash code
	 @Override
	    public int hashCode() {
	        return Objects.hash(roomId, subjectId);
	    }
	
	//Override equals
	@Override
   public boolean equals(Object o) {
       if (this == o) return true;

       if (o == null || getClass() != o.getClass())
           return false;

       EmbemdedRoomSubjectId that = (EmbemdedRoomSubjectId) o;
       return Objects.equals(this.roomId, that.roomId) &&
              Objects.equals(this.subjectId, that.subjectId);
   }
}
