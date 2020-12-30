package com.ISC.project.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestParam;

import com.ISC.project.model.IntakeSubject;
import com.ISC.project.model.EmbemdedIntakeSubjectId;

public interface IntakeSubjectRepo extends JpaRepository<IntakeSubject, EmbemdedIntakeSubjectId>{
	//Get subject of intake
	@Query("select insub from IntakeSubject insub where insub.id.intakeId = :intakeId")
	public List<IntakeSubject> listIntakeOfStu(@RequestParam("intakeId") Long intakeId);
}
