package com.ISC.project.dao;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestParam;

import com.ISC.project.model.Major;

public interface MajorRepository extends JpaRepository<Major, Long>{
	
	@Query("select codeMajor from Major where codeMajor = :newCodeMajor")
	public List<String> checkCodeMajor(@RequestParam("codeMajor") String newCodeMajor);
	
	//Pagination
	@Query("select major from Major major")
	public Page<Major> findMajor(Pageable pageable);
	
	//Search Major
	@Query("select major from Major major where concat(major.createdBy, major.updatedBy, major.createdDate, major.updatedDate, major.codeMajor, major.nameMajor, major.descriptionMajor) like %?1%")
	public List<Major> searchMajor(String keyWord);
}
