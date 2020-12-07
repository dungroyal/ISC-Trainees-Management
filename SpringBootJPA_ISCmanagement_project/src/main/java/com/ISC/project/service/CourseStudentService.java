package com.ISC.project.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ISC.project.dao.CourseStudentRepo;
import com.ISC.project.model.CourseStudent;
import com.ISC.project.model.EmbemdedCourseStudentId;

@Service
@Transactional
public class CourseStudentService {
	@Autowired
	private CourseStudentRepo courseStudentRepo;
	
	public CourseStudent save(CourseStudent courseStudent) {
		return courseStudentRepo.save(courseStudent);
	}
	
	public List<CourseStudent> listAllCourseStudent(){
		return courseStudentRepo.findAll();
	}
	
	public CourseStudent get(EmbemdedCourseStudentId id) {
		return courseStudentRepo.findById(id).get();
	}
	
	public void delete(EmbemdedCourseStudentId id) {
		courseStudentRepo.deleteById(id);
	}
}
