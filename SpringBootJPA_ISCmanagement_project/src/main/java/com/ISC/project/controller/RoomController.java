package com.ISC.project.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin
@RestController
@RequestMapping("/api/room")
@Tag(name = "Room", description = "CRUD for Room")
public class RoomController {
	@Autowired
	private RoomService roomService;
	
	// Get all Room
	// DOC for get all Room
	@Operation(summary = "Get all Room", description = "Show all room under the database")
	@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = Room.class))),
	responseCode = "200", description = "Get all room success")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "401", description = "Authorization Required"),
			@ApiResponse(responseCode = "403", description = "Forbidden"),
			@ApiResponse(responseCode = "500", description = "Internal Error Server")
	})
	//get all Room
	@GetMapping(value = "/listRoom", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = "application/json")
	public ResultRespon listRoom() {
		return new ResultRespon(0,"Get list room success",this.roomService.listAllRoom());
	}
	
	// Get one Room
	// DOC for get one Room
	@Operation(summary = "Get one Room with ID", description = "Show one room with ID under the database")
	@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = Room.class))),
	responseCode = "200", description = "Get one Room success")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "401", description = "Authorization Required"),
			@ApiResponse(responseCode = "403", description = "Forbidden"),
			@ApiResponse(responseCode = "500", description = "Internal Error Server")
	})
	@GetMapping("/getRoom") 
	public ResultRespon getRoom(@Parameter(description = "Room ID is required!", required = true) @RequestParam("id") long id) {
		List<Room> univer = new ArrayList<>();
		univer.add(roomService.findById(id).orElseThrow(() -> new ResourseNotFoundException("Not found room with id: " + id)));
		return new ResultRespon(0,"Get room success",univer);
	}

	// Add new Room
	// DOC for add new Room
	@Operation(summary = "Add new Room", description = "Add new room")
	@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = Room.class))),
	responseCode = "200", description = "Add new room success")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "401", description = "Authorization Required"),
			@ApiResponse(responseCode = "403", description = "Forbidden"),
			@ApiResponse(responseCode = "500", description = "Internal Error Server")
	})
	@PostMapping(value = "/newRoom", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = "application/json")
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
	

	// Update Room
	// DOC for update Room
	@Operation(summary = "Update Room with ID", description = "Update Room with ID")
	@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = Room.class))),
	responseCode = "200", description = "Update room success")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "401", description = "Authorization Required"),
			@ApiResponse(responseCode = "403", description = "Forbidden"),
			@ApiResponse(responseCode = "500", description = "Internal Error Server")
	})
	@PutMapping(value = "/editRoom", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = "application/json")
	public ResultRespon editRoom(
			@Parameter(required = true)
			@RequestBody Room room, 
			@Parameter(description = "The room id is required", required = true) 
			@RequestParam("id") long id) {
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
	
	// Delete Room
	// DOC for delete Room
	@Operation(summary = "Delete Room with ID", description = "Delete Room with ID")
	@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = Room.class))),
	responseCode = "200", description = "Delete room success")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "401", description = "Authorization Required"),
			@ApiResponse(responseCode = "403", description = "Forbidden"),
			@ApiResponse(responseCode = "500", description = "Internal Error Server")
	})
	@DeleteMapping(value = "/deleteRoom", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = "application/json")
	public ResultRespon deleteRoom(@Parameter(description = "The room id is required", required = true) @RequestParam("id") long id) {
		roomService.findById(id).orElseThrow(()->new ResourseNotFoundException("Not found room with id: " + id));
		this.roomService.delete(id);
		return new ResultRespon(0,"Delete room witd id:"+id+" success");
	}
	
	// Pagination Room
	// DOC for Pagination Room
	@Operation(summary = "Pagination Room", description = "Pagination Room")
	@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = Room.class))),
	responseCode = "200", description = "Pagination room success")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "401", description = "Authorization Required"),
			@ApiResponse(responseCode = "403", description = "Forbidden"),
			@ApiResponse(responseCode = "500", description = "Internal Error Server")
	})
	@GetMapping(value = "/pagination", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = "application/json") 
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
	
	// Search Room
	// DOC for search Room
	@Operation(summary = "Search Room with keyword", description = "Search Room with keyword")
	@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = Room.class))),
	responseCode = "200", description = "Search room success")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "401", description = "Authorization Required"),
			@ApiResponse(responseCode = "403", description = "Forbidden"),
			@ApiResponse(responseCode = "500", description = "Internal Error Server")
	})	
	@GetMapping(value = "/searchRoom", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = "application/json") 
	public ResultRespon searchRoom(@Parameter(description = "Search keyword id is required", required = true) @RequestParam("keyWord") String keyWord) {
		if(this.roomService.searchRoom(keyWord).isEmpty()) {
			throw new ResourseNotFoundException("Not found room witd keyword: "+keyWord);
		}else {
			return new ResultRespon(0, "Search success", this.roomService.searchRoom(keyWord));
		}
	}
}