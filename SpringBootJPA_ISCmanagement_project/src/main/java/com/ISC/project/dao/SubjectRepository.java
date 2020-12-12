package com.ISC.project.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestParam;

import com.ISC.project.model.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Long>{
	//Check Subject Code
	@Query("select codeSub from Subject where codeSub = :newCodeStu")
	public List<String> checkCodeSubject(@RequestParam("newCodeStu") String newCodeStu);
}
