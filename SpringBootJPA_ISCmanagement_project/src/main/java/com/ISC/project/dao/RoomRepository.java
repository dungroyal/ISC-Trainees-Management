package com.ISC.project.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestParam;

import com.ISC.project.model.Room;

public interface RoomRepository extends JpaRepository<Room, Long>{
	@Query("Select codeRoom From Room Where codeRoom = :newCodeRoom")
	public List<String> checkCodeRoom(@RequestParam("codeRoom") String newCodeRoom);
}
