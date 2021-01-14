package com.ISC.project.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

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
	
	public List<String> checkCodeRoom(String codeRoom){
		return roomRepository.checkCodeRoom(codeRoom);
	}
	
	public Page<Room> findRoom(Pageable pageable){
		return roomRepository.findRoom(pageable);
	}
	
	public Page<Room> searchRoom(String keyWord, Pageable pageable){
		return roomRepository.searchRoom(keyWord, pageable);
	}
	

	public List<String> checkCodeRoomUpdate(@RequestParam("newCodeRoom") String newCodeRoom){
		return roomRepository.checkCodeRoomUpdate(newCodeRoom);
	}
}
