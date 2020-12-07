package com.ISC.project.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ISC.project.model.EmbemdedStudentSubjectId;
import com.ISC.project.model.StudentSubject;

public interface StudentSubjectRepo extends JpaRepository<StudentSubject, EmbemdedStudentSubjectId>{

}
