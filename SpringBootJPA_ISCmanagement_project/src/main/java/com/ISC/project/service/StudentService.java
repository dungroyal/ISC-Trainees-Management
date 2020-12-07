package com.ISC.project.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ISC.project.dao.StudentRepository;
import com.ISC.project.model.Student;

@Service
@Transactional
public class StudentService {
	@Autowired
	private StudentRepository studentRepository;
	
	public Student save(Student student) {
		return studentRepository.save(student);
	}
	
	public List<Student> listAllStudent(){
		return studentRepository.findAll();
	}
	
	public Student get(long id) {
		return studentRepository.findById(id).get();
	}
	
	public void delete(long id) {
		studentRepository.deleteById(id);
	}
}
