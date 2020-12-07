package com.ISC.project.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ISC.project.model.EmbemdedRoomSubjectId;
import com.ISC.project.model.RoomSubject;

public interface RoomSubjectRepo extends JpaRepository<RoomSubject, EmbemdedRoomSubjectId>{

}
