package com.ISC.project.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ISC.project.model.Room;
import com.ISC.project.model.School;

public interface SchoolRepository extends JpaRepository<School, Long>{

}
