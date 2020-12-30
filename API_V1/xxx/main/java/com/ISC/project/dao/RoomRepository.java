package com.ISC.project.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestParam;

import com.ISC.project.model.Room;

public interface RoomRepository extends JpaRepository<Room, Long>{
	@Query("Select codeRoom From Room Where codeRoom = :newCodeRoom")
	public List<String> checkCodeRoom(@RequestParam("codeRoom") String newCodeRoom);

	@Query("Select codeRoom From Room Where codeRoom not in (:newCodeRoom)")
	public List<String> checkCodeRoomUpdate(@RequestParam("newCodeRoom") String newCodeRoom);
	
	@Query("select ro from Room ro")
	public Page<Room> findRoom(Pageable pageable);
	
	@Query("select ro from Room ro where concat(ro.codeRoom, ro.nameRoom, ro.noteRoom) like %?1%")
	public List<Room> searchRoom(String keyWord);
}
