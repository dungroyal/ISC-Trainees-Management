package com.ISC.project.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ISC.project.dao.CourseRepository;
import com.ISC.project.model.Course;

@Service
@Transactional
public class CourseService {
	@Autowired
	private CourseRepository courseRepository;
	
	public Optional<Course> findById(long id){
		return courseRepository.findById(id);
	} 
	public Course save(Course course) {
		return courseRepository.save(course);
	}
	
	public List<Course> listAllCourse(){
		return courseRepository.findAll();
	}
	
	public Course get(long id) {
		return courseRepository.findById(id).get();
	}
	
	public void delete(long id) {
		courseRepository.deleteById(id);
	}
	
}
