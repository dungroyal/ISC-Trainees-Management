package com.ISC.project.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ISC.project.dao.IntakeRepository;
import com.ISC.project.model.Intake;

@Service
@Transactional
public class IntakeService {
	@Autowired
	private IntakeRepository courseRepository;
	
	public Optional<Intake> findById(long id){
		return courseRepository.findById(id);
	} 
	public Intake save(Intake course) {
		return courseRepository.save(course);
	}
	
	public List<Intake> listAllCourse(){
		return courseRepository.findAll();
	}
	
	public Intake get(long id) {
		return courseRepository.findById(id).get();
	}
	
	public void delete(long id) {
		courseRepository.deleteById(id);
	}
	
}
