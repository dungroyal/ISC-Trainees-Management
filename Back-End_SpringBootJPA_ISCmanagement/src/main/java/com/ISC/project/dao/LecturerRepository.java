package com.ISC.project.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestParam;

import com.ISC.project.model.Lecturer;

public interface LecturerRepository extends JpaRepository<Lecturer, Long>{
	
	@Query("select codeLec from Lecturer where codeLec = :newCodeLec")
	public List<String> checkCodeLecturer(@RequestParam("codeLec") String newCodeLec);

	@Query("select emailLec from Lecturer where emailLec = :newEmailLec")
	public List<String> checkEmailLecturer(@RequestParam("emailLec") String newEmailLec);

	@Query("select codeLec from Lecturer where codeLec not in (:updateCodeLec)")
	public List<String> checkCodeLecturerUpdate(@RequestParam("updateCodeLec") String updateCodeLec);

	// Pagination
	@Query("select lecturer from Lecturer lecturer")
	public Page<Lecturer> findLecturer(Pageable pageable);

	// Search Major
	@Query("select lecturer from Lecturer lecturer where concat(lecturer.createdBy, lecturer.updatedBy, lecturer.createdDate, lecturer.updatedDate, lecturer.codeLec, lecturer.firstName, lecturer.lastName, lecturer.addressLec, lecturer.phoneLec, lecturer.emailLec, lecturer.degree, lecturer.image, lecturer.statusLec, lecturer.noteLec) like %?1%")
	public Page<Lecturer> searchLecturer(String keyWord, Pageable pageable);
}
