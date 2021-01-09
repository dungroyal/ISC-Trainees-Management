package com.ISC.project.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestParam;

import com.ISC.project.model.Intake;

public interface IntakeRepository extends JpaRepository<Intake, Long>{
	
	@Query("select codeIntake from Intake where codeIntake = :newCodeIntake")
	public List<String> checkCodeIntake(@RequestParam("newCodeIntake") String newCodeIntake);
	
	//Pagination
	@Query("select intake from Intake intake")
	public Page<Intake> findIntake(Pageable pageable);
		
	//Search Major
	@Query("select intake from Intake intake where concat(intake.createdBy, intake.updatedBy, intake.createdDate, intake.updatedDate, intake.codeIntake, intake.nameIntake, intake.startDay, intake.endDay, intake.statusIntake) like %?1%")
	public Page<Intake> searchIntake(String keyWord,Pageable pageable);
}
