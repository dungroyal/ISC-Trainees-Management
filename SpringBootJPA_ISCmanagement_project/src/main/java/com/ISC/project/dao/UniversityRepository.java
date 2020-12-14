package com.ISC.project.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestParam;

import com.ISC.project.model.Room;
import com.ISC.project.model.University;

public interface UniversityRepository extends JpaRepository<University, Long>{
	
	@Query("select nameUni from University where nameUni = :newNameUni")
	public List<String > checkNameUni(@RequestParam("newNameUni") String newNameUni);
	
	@Query("select nameUni from University where id = ?1")
	public String getNameById(@RequestParam("id") long id);
	
	@Query("select uni from University uni where concat(uni.nameUni,uni.addressUni,uni.contactPerson,uni.websiteUni,uni.noteUni) like %?1%")
	public List<University> searchUni(String keyWord);
	
	@Query("select uni from University uni")
	public Page<University> findUni(Pageable pageable);
}
