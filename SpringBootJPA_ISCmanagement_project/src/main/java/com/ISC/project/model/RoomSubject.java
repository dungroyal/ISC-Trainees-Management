package com.ISC.project.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity
@Table(name = "RoomSubject")
public class RoomSubject implements Serializable{
	@EmbeddedId
	private EmbemdedRoomSubjectId id = new EmbemdedRoomSubjectId();
	
	//map to room
		@ManyToOne(optional = false)
		@MapsId("roomId")
		private Room room;
		
		//map to subject
		@ManyToOne(optional = false)
		@MapsId("subjectId")
		private Subject subject;

		public RoomSubject(EmbemdedRoomSubjectId id, Room room, Subject subject) {
			super();
			this.id = id;
			this.room = room;
			this.subject = subject;
		}

		public RoomSubject() {
			super();
		}

		public RoomSubject(EmbemdedRoomSubjectId id) {
			super();
			this.id = id;
		}

		public EmbemdedRoomSubjectId getId() {
			return id;
		}

		public void setId(EmbemdedRoomSubjectId id) {
			this.id = id;
		}

		public Room getRoom() {
			return room;
		}

		public void setRoom(Room room) {
			this.room = room;
		}

		public Subject getSubject() {
			return subject;
		}

		public void setSubject(Subject subject) {
			this.subject = subject;
		}
		
		//hash code
		 @Override
		    public int hashCode() {
		        return Objects.hash(room, subject);
		    }
		
		//Override equals
		@Override
	   public boolean equals(Object o) {
	       if (this == o) return true;

	       if (o == null || getClass() != o.getClass())
	           return false;

	       RoomSubject that = (RoomSubject) o;
	       return Objects.equals(this.room, that.room) &&
	              Objects.equals(this.subject, that.subject);
	   }
}
