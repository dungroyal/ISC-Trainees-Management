package com.ISC.project.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.ISC.project.dao.IntakeRepository;
import com.ISC.project.model.Intake;

@Service
@Transactional
public class IntakeService {
	@Autowired
	private IntakeRepository intakeRepository;
	
	public Optional<Intake> findById(long id){
		return intakeRepository.findById(id);
	} 
	public Intake save(Intake course) {
		return intakeRepository.save(course);
	}
	
	public List<Intake> listAllCourse(){
		return intakeRepository.findAll();
	}
	
	public Intake get(long id) {
		return intakeRepository.findById(id).get();
	}
	
	public void delete(long id) {
		intakeRepository.deleteById(id);
	}
	
	//check code Intake
	public List<String> checkCodeIntake(@RequestParam("newCodeIntake") String newCodeIntake){
		return this.intakeRepository.checkCodeIntake(newCodeIntake);
	}
	
	public Page<Intake> findIntake(Pageable pageable){
		return this.intakeRepository.findIntake(pageable);
	}
	
	public List<Intake> searchIntake(String keyWord){
		return this.intakeRepository.searchIntake(keyWord);
	}
}
