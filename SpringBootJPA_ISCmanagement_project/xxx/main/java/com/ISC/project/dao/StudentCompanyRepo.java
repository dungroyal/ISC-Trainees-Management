package com.ISC.project.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestParam;

import com.ISC.project.model.EmbemdedStudentCompanyId;
import com.ISC.project.model.StudentCompany;

public interface StudentCompanyRepo extends JpaRepository<StudentCompany, EmbemdedStudentCompanyId>{
	@Query("select stucom from StudentCompany stucom where stucom.id.studentId = :studentId")
	public List<StudentCompany> getStudentCompany(@RequestParam("studentId") Long studentId);
}
