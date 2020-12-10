package com.ISC.project.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestParam;
import com.ISC.project.model.University;

public interface UniversityRepository extends JpaRepository<University, Long>{
	@Query("select nameUni from University where nameUni = :newNameUni")
	public List<String > checkNameUni(@RequestParam("newNameUni") String newNameUni);
}
