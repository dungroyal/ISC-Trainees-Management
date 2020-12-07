package com.ISC.project.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ISC.project.dao.StudentSubjectRepo;
import com.ISC.project.model.EmbemdedStudentSubjectId;
import com.ISC.project.model.StudentSubject;

@Service
@Transactional
public class StudentSubjectService {
	@Autowired
	private StudentSubjectRepo studentSubjectRepo;
	
	public Optional<StudentSubject> findById(EmbemdedStudentSubjectId id){
		return studentSubjectRepo.findById(id);
	}
	public StudentSubject save(StudentSubject studentSubject) {
		return studentSubjectRepo.save(studentSubject);
	}
	
	public List<StudentSubject> listAllStudentSubject(){
		return studentSubjectRepo.findAll();
	}
	
	public StudentSubject get(EmbemdedStudentSubjectId id) {
		return studentSubjectRepo.findById(id).get();
	}
	
	public void delete(EmbemdedStudentSubjectId id) {
		studentSubjectRepo.deleteById(id);
	}
}
