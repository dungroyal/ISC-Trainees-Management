package com.ISC.project.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ISC.project.dao.RoomSubjectRepo;
import com.ISC.project.model.EmbemdedRoomSubjectId;
import com.ISC.project.model.RoomSubject;

@Service
@Transactional
public class RoomSubjectService {
	@Autowired
	private RoomSubjectRepo roomSubjectRepo;
	public Optional<RoomSubject> findById(EmbemdedRoomSubjectId id){
		return roomSubjectRepo.findById(id);
	}
	public RoomSubject save(RoomSubject roomSubject) {
		return roomSubjectRepo.save(roomSubject);
	}
	
	public List<RoomSubject> listAllRoomSubject(){
		return roomSubjectRepo.findAll();
	}
	
	public RoomSubject get(EmbemdedRoomSubjectId id) {
		return roomSubjectRepo.findById(id).get();
	}
	
	public void delete(EmbemdedRoomSubjectId id) {
		roomSubjectRepo.deleteById(id);
	}
	public void deleteTwo(RoomSubject roomSubject) {
		roomSubjectRepo.delete(roomSubject);
	}
}
