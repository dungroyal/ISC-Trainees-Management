package com.ISC.project.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ISC.project.model.Room;

public interface SchoolRepository extends JpaRepository<Room, Integer>{

}
