package com.ISC.project.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ISC.project.dao.IntakeStudentRepo;
import com.ISC.project.model.IntakeStudent;
import com.ISC.project.model.EmbemdedIntakeStudentId;

@Service
@Transactional
public class IntakeStudentService {
	@Autowired
	private IntakeStudentRepo courseStudentRepo;
	
	public Optional<IntakeStudent> findById(EmbemdedIntakeStudentId id){
		return courseStudentRepo.findById(id);
	}
	public IntakeStudent save(IntakeStudent courseStudent) {
		return courseStudentRepo.save(courseStudent);
	}
	
	public List<IntakeStudent> listAllCourseStudent(){
		return courseStudentRepo.findAll();
	}
	
	public IntakeStudent get(EmbemdedIntakeStudentId id) {
		return courseStudentRepo.findById(id).get();
	}
	
	public void delete(EmbemdedIntakeStudentId id) {
		courseStudentRepo.deleteById(id);
	}
}