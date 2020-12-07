package com.ISC.project.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ISC.project.dao.RoomLecturerRepo;

import com.ISC.project.model.EmbemdedRoomLecturerId;

import com.ISC.project.model.RoomLecturer;

@Service
@Transactional
public class RoomLecturerService {
	@Autowired
	private RoomLecturerRepo roomLecturerRepo;
	
	public RoomLecturer save(RoomLecturer roomLecturer) {
		return roomLecturerRepo.save(roomLecturer);
	}
	
	public List<RoomLecturer> listAllRoomLecturer(){
		return roomLecturerRepo.findAll();
	}
	
	public RoomLecturer get(EmbemdedRoomLecturerId id) {
		return roomLecturerRepo.findById(id).get();
	}
	
	public void delete(EmbemdedRoomLecturerId id) {
		roomLecturerRepo.deleteById(id);
	}
}
