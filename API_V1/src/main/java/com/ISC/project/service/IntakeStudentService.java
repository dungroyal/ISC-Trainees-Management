package com.ISC.project.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

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
	
	//Delete by id
	public void deleteById(EmbemdedIntakeStudentId id) {
		courseStudentRepo.deleteById(id);
	}
	// List intake of stu
	public List<IntakeStudent> listIntakeOfStu(@RequestParam("studentId") Long studentId) {
		return this.courseStudentRepo.listIntakeOfStu(studentId);
	}
	
	//Update intake of stu
	public List<IntakeStudent> updateIntakeOfStu(@RequestParam("newIntakeId") Long newIntakeId, @RequestParam("studentId") Long studentId
			, @RequestParam("intakeId") Long intakeId) {
		this.courseStudentRepo.updateIntakeOfStu(newIntakeId, studentId, intakeId);
		return this.courseStudentRepo.listIntakeOfStu(studentId);
	}
	
	//Update intake of stu array
	public void updateIntakeOfStuArray(@RequestParam("newIntakeId") List<Long> newIntakeId, @RequestParam("studentId") Long studentId
			, @RequestParam("intakeId") List<Long> intakeId) {
		this.courseStudentRepo.updateIntakeOfStuArray(newIntakeId, studentId, intakeId);
//		return this.courseStudentRepo.listIntakeOfStu(studentId);
	}
}
