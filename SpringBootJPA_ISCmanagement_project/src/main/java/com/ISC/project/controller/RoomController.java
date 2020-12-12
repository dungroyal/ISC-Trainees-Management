package com.ISC.project.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ISC.project.exception.ResourseNotFoundException;
import com.ISC.project.model.Room;
import com.ISC.project.payload.ResultRespon;
import com.ISC.project.service.RoomService;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class RoomController {
	@Autowired
	private RoomService roomService;
	
	//get all Room
	@GetMapping("/listRoom")
	public ResultRespon listRoom() {
		return new ResultRespon(0,"Get list room success",this.roomService.listAllRoom());
	}
	//get one Room
	@GetMapping("/getRoom") 
	public ResultRespon getRoom(@RequestParam("id") long id) {
		List<Room> univer = new ArrayList<>();
		univer.add(roomService.findById(id).orElseThrow(() -> new ResourseNotFoundException("Not found room with id: " + id)));
		return new ResultRespon(0,"Get room success",univer);
	}
	
	//post Room
	@PostMapping("/newRoom")
	public ResultRespon addRoom(@RequestBody Room room) {
		if(this.roomService.checkCodeRoom(room.getCodeRoom()).isEmpty()) {
			List<Room> newRoom = new ArrayList<>();
			room.setCreatedDate(LocalDateTime.now());
			newRoom.add(roomService.save(room));
			return new ResultRespon(0,"Add new room success",newRoom);
		}else {
			throw new ResourseNotFoundException("Duplicate code room");
		}
	}
	
	//update Room
	@PutMapping("/editRoom")
	public ResultRespon editRoom(@RequestBody Room room,@RequestParam("id") long id) {
		List<Room> newRoom = new ArrayList<>();
		Room oldRoom = roomService.findById(id).orElseThrow(()->new ResourseNotFoundException("Not found room with id: " + id));
		if(!this.roomService.checkCodeRoom(room.getCodeRoom()).isEmpty()) {
				oldRoom.setCodeRoom(room.getCodeRoom());
				oldRoom.setNameRoom(room.getNameRoom());
				oldRoom.setTypeRoom(room.getTypeRoom());
				oldRoom.setStatusRoom(room.getStatusRoom());
				oldRoom.setNoteRoom(room.getNoteRoom());
				oldRoom.setCreatedBy(room.getCreatedBy());
				oldRoom.setUpdatedBy(room.getUpdatedBy());
				oldRoom.setCreatedDate(room.getCreatedDate());
				oldRoom.setUpdatedDate(LocalDateTime.now());
				newRoom.add(roomService.save(oldRoom));
				return new ResultRespon(0,"Update success",newRoom);
		}else if(this.roomService.checkCodeRoom(room.getCodeRoom()).isEmpty()){
			if(!this.roomService.checkCodeRoomUpdate(room.getCodeRoom()).contains(room.getCodeRoom())) {
				System.out.println(this.roomService.checkCodeRoomUpdate(room.getCodeRoom()));
				oldRoom.setCodeRoom(room.getCodeRoom());
				oldRoom.setNameRoom(room.getNameRoom());
				oldRoom.setTypeRoom(room.getTypeRoom());
				oldRoom.setStatusRoom(room.getStatusRoom());
				oldRoom.setNoteRoom(room.getNoteRoom());
				oldRoom.setCreatedBy(room.getCreatedBy());
				oldRoom.setUpdatedBy(room.getUpdatedBy());
				oldRoom.setCreatedDate(room.getCreatedDate());
				oldRoom.setUpdatedDate(LocalDateTime.now());
				newRoom.add(roomService.save(oldRoom));
				return new ResultRespon(0,"Update success with new code room",newRoom);
			}
			throw new ResourseNotFoundException("Duplicate code room");
		}
		throw new ResourseNotFoundException("Duplicate code room");
	}
	
	//delete Room
	@DeleteMapping("/deleteRoom")
	public ResultRespon deleteRoom(@RequestParam("id") long id) {
		roomService.findById(id).orElseThrow(()->new ResourseNotFoundException("Not found room with id: " + id));
		this.roomService.delete(id);
		return new ResultRespon(0,"Delete room witd id:"+id+" success");
	}
	
	@GetMapping("/room/pagination") 
	public ResultRespon paginationRoom(
			@RequestParam(name="page", required = false, defaultValue = "1") Integer page,
			@RequestParam(name="size", required = false, defaultValue = "1") Integer size,
			@RequestParam(name="sort", required = false, defaultValue = "ASC") String sort) {
		Sort sortable = null;
		if(sort.equals("ASC")) {
			sortable = Sort.by("nameRoom").ascending();
		}
		if(sort.equals("DESC")) {
			sortable = Sort.by("nameRoom").descending();
		}
		
		Pageable pageable = PageRequest.of(page, size, sortable);
		Page<Room> room = roomService.findRoom(pageable);
		List<Page<Room>> rooms = new ArrayList<>();
		rooms.add(room);
		return new ResultRespon(0, "Success", rooms);
	}
	
	@GetMapping("/searchRoom") 
	public ResultRespon searchRoom(@RequestParam("keyWord") String keyWord) {
		if(this.roomService.searchRoom(keyWord).isEmpty()) {
			throw new ResourseNotFoundException("Not found room witd keyword: "+keyWord);
		}else {
			return new ResultRespon(0, "Search success", this.roomService.searchRoom(keyWord));
		}
	}
}