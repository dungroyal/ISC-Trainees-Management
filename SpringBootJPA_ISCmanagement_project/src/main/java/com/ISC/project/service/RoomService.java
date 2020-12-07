package com.ISC.project.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ISC.project.dao.RoomRepository;
import com.ISC.project.model.Lecturer;
import com.ISC.project.model.Room;

@Service
@Transactional
public class RoomService {
	@Autowired
	private RoomRepository roomRepository;
	
	public Optional<Room> findById(long id){
		return roomRepository.findById(id);
	}
	
	public Room save(Room room) {
		return roomRepository.save(room);
	}
	
	public List<Room> listAllRoom(){
		return roomRepository.findAll();
	}
	
	public Room get(long id) {
		return roomRepository.findById(id).get();
	}
	
	public void delete(long id) {
		roomRepository.deleteById(id);
	}
}
