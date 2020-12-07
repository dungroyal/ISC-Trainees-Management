package com.ISC.project.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ISC.project.model.EmbemdedRoomLecturerId;
import com.ISC.project.model.RoomLecturer;

public interface RoomLecturerRepo extends JpaRepository<RoomLecturer, EmbemdedRoomLecturerId>{

}
