package com.ISC.project.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ISC.project.model.EmbemdedRoomStudentId;
import com.ISC.project.model.RoomStudent;

public interface RoomStudentRepo extends JpaRepository<RoomStudent, EmbemdedRoomStudentId>{

}
