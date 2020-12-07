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
@Table(name = "RoomLecturer")
public class RoomLecturer {
	@EmbeddedId
	private EmbemdedRoomLecturerId id = new EmbemdedRoomLecturerId();
	
	//map to room
			@ManyToOne(optional = false)
			@MapsId("roomId")
			private Room room;
			
			//map to lecturer
			@ManyToOne(optional = false)
			@MapsId("lecturerId")
			private Lecturer lecturer;
			
			@Temporal(TemporalType.DATE)
			private Date dateTeach;
			
			

			public RoomLecturer(EmbemdedRoomLecturerId id, Room room, Lecturer lecturer, Date dateTeach) {
				super();
				this.id = id;
				this.room = room;
				this.lecturer = lecturer;
				this.dateTeach = dateTeach;
			}

			public RoomLecturer() {
				super();
			}

			public RoomLecturer(EmbemdedRoomLecturerId id) {
				super();
				this.id = id;
			}

			public EmbemdedRoomLecturerId getId() {
				return id;
			}

			public void setId(EmbemdedRoomLecturerId id) {
				this.id = id;
			}

			public Room getRoom() {
				return room;
			}

			public void setRoom(Room room) {
				this.room = room;
			}

			public Lecturer getLecturer() {
				return lecturer;
			}

			public void setLecturer(Lecturer lecturer) {
				this.lecturer = lecturer;
			}

			//hash code
			 @Override
			    public int hashCode() {
			        return Objects.hash(room, lecturer);
			    }
			
			//Override equals
			@Override
		   public boolean equals(Object o) {
		       if (this == o) return true;

		       if (o == null || getClass() != o.getClass())
		           return false;

		       RoomLecturer that = (RoomLecturer) o;
		       return Objects.equals(this.room, that.room) &&
		              Objects.equals(this.lecturer, that.lecturer);
		   }
}
