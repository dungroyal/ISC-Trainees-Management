package com.ISC.project.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.ISC.project.dao.IntakeSubjectRepo;
import com.ISC.project.model.IntakeSubject;
import com.ISC.project.model.EmbemdedIntakeSubjectId;

@Service
@Transactional
public class IntakeSubjectService {
	@Autowired
	private IntakeSubjectRepo courseSubjectRepo;
	
	public Optional<IntakeSubject> findById(EmbemdedIntakeSubjectId id){
		return courseSubjectRepo.findById(id);
	}
	public IntakeSubject save(IntakeSubject majorSubject) {
		return courseSubjectRepo.save(majorSubject);
	}
	
	public List<IntakeSubject> listAllmajorSubject(){
		return courseSubjectRepo.findAll();
	}
	
	public IntakeSubject get(EmbemdedIntakeSubjectId id) {
		return courseSubjectRepo.findById(id).get();
	}
	
	public void delete(EmbemdedIntakeSubjectId id) {
		courseSubjectRepo.deleteById(id);
	}
	
	public List<IntakeSubject> getSubjectOfIntake(@RequestParam("intakeId") Long intakeId){
		return courseSubjectRepo.listIntakeOfStu(intakeId);
	}
}
