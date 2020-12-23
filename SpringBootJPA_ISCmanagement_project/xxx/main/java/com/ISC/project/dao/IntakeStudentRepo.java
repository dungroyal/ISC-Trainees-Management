package com.ISC.project.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ISC.project.model.IntakeStudent;
import com.ISC.project.model.EmbemdedIntakeStudentId;

public interface IntakeStudentRepo extends JpaRepository<IntakeStudent, EmbemdedIntakeStudentId>{

}
