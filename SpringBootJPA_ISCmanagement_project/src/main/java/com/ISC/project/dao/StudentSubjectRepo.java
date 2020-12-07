package com.ISC.project.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ISC.project.model.EmbemdedStudentSubject;
import com.ISC.project.model.StudentSubject;

public interface StudentSubjectRepo extends JpaRepository<StudentSubject, EmbemdedStudentSubject>{

}
