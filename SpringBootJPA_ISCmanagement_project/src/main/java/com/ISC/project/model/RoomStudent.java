package com.ISC.project.model;

import java.util.Date;
import java.util.Objects;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "RoomStudent")
public class RoomStudent {
	@EmbeddedId
	private EmbemdedRoomSubjectId id = new EmbemdedRoomSubjectId();
	
	//map to room
		@ManyToOne(optional = false)
		@MapsId("roomId")
		private Room room;
		
		//map to student
		@ManyToOne(optional = false)
		@MapsId("studentId")
		private Student student;

		@Temporal(TemporalType.DATE)
		private Date dateStudy;
		

		
		public RoomStudent(EmbemdedRoomSubjectId id, Room room, Student student, Date dateStudy) {
			super();
			this.id = id;
			this.room = room;
			this.student = student;
			this.dateStudy = dateStudy;
		}

		public RoomStudent() {
			super();
		}

		public RoomStudent(EmbemdedRoomSubjectId id) {
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

		
		
		public Student getStudent() {
			return student;
		}

		public void setStudent(Student student) {
			this.student = student;
		}

		//hash code
		 @Override
		    public int hashCode() {
		        return Objects.hash(room, student);
		    }
		
		//Override equals
		@Override
	   public boolean equals(Object o) {
	       if (this == o) return true;

	       if (o == null || getClass() != o.getClass())
	           return false;

	       RoomStudent that = (RoomStudent) o;
	       return Objects.equals(this.room, that.room) &&
	              Objects.equals(this.student, that.student);
	   }
}
