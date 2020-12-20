package com.ISC.project.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestParam;

import com.ISC.project.model.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Long>{

	@Query("select codeSub from Subject where codeSub = :newCodeSub")
	public List<String> checkCodeSubject(@RequestParam("codeSub") String newCodeSub);

	//Pagination
	@Query("select subject from Subject subject")
	public Page<Subject> findSubject(Pageable pageable);

	//Search Major
	@Query("select subject from Subject subject where concat(subject.createdBy, subject.updatedBy, subject.createdDate, subject.updatedDate, subject.codeSub, subject.nameSub, subject.creditSub, subject.passCore, subject.statusSub, subject.noteSub) like %?1%")
	public List<Subject> searchSubject(String keyWord);
}
