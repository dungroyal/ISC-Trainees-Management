package com.ISC.project.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ISC.project.dao.RoomStudentRepo;
import com.ISC.project.model.EmbemdedRoomStudentId;
import com.ISC.project.model.RoomStudent;

@Service
@Transactional
public class RoomStudentService {
	@Autowired
	private RoomStudentRepo roomStudentRepo;
	
	public RoomStudent save(RoomStudent roomStudent) {
		return roomStudentRepo.save(roomStudent);
	}
	
	public List<RoomStudent> listAllRoomStudent(){
		return roomStudentRepo.findAll();
	}
	
	public RoomStudent get(EmbemdedRoomStudentId id) {
		return roomStudentRepo.findById(id).get();
	}
	
	public void delete(EmbemdedRoomStudentId id) {
		roomStudentRepo.deleteById(id);
	}
}
