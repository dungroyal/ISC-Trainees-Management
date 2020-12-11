package com.ISC.project.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ISC.project.model.EmbemdedStudentCompanyId;
import com.ISC.project.model.StudentCompany;

public interface StudentCompanyRepo extends JpaRepository<StudentCompany, EmbemdedStudentCompanyId>{

}
