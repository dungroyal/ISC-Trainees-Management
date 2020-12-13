package com.ISC.project.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestParam;

import com.ISC.project.model.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Long>{
		//Check Subject Code
		@Query("select codeSub from Subject where codeSub = :newCodeSub")
		public List<String> checkCodeSubject(@RequestParam("newCodeSub") String newCodeSub);
		//Pagination
		@Query("select sub from Subject sub")
		public Page<Subject> findSubject(Pageable pageable);
		
		//Search Subject
		@Query("select sub from Subject sub where concat(sub.codeSub, sub.nameSub, sub.creditSub, sub.passCore, sub.statusSub, "
				+ " sub.noteSub) like %?1%")
		public List<Subject> searchSubject( String keyWord);
}
