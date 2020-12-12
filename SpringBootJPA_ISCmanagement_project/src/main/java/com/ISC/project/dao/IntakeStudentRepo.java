package com.ISC.project.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestParam;

import com.ISC.project.model.IntakeStudent;
import com.ISC.project.model.EmbemdedIntakeStudentId;

public interface IntakeStudentRepo extends JpaRepository<IntakeStudent, EmbemdedIntakeStudentId>{
	//Get Intake of Student
	@Query("select instu from IntakeStudent instu where instu.id.studentId = :studentId")
	public List<IntakeStudent> listIntakeOfStu(@RequestParam("studentId") Long studentId);
	
	//Update intake student for student
	@Transactional
	@Modifying
	@Query("update IntakeStudent instu set instu.intake.id = :newIntakeId where instu.student.id = :studentId and instu.intake.id = :intakeId")
	public List<IntakeStudent> updateIntakeOfStu(@RequestParam("newIntakeId") Long newIntakeId, @RequestParam("studentId") Long studentId
			, @RequestParam("intakeId") Long intakeId);
	
	//Update Intake of Student array
	@Transactional()
	@Modifying
	@Query("update IntakeStudent instu set instu.intake.id = :newIntakeId where instu.student.id = :studentId and instu.intake.id = :intakeId")
	public void updateIntakeOfStuArray(@RequestParam("newIntakeId") List<Long> newIntakeId, @RequestParam("studentId") Long studentId
			, @RequestParam("intakeId") List<Long> intakeId);
}
