package com.ISC.project.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ISC.project.dao.StudentCompanyRepo;
import com.ISC.project.model.EmbemdedStudentCompanyId;
import com.ISC.project.model.StudentCompany;

@Service
@Transactional
public class StudentCompanyService {
	@Autowired
	private StudentCompanyRepo studentCompanyRepo;
	
	public Optional<StudentCompany> findById(EmbemdedStudentCompanyId id){
		return studentCompanyRepo.findById(id);
	}
	
	public StudentCompany save(StudentCompany studentCompany) {
		return studentCompanyRepo.save(studentCompany);
	}
	
	public List<StudentCompany> listAllStudentCompany(){
		return studentCompanyRepo.findAll();
	}
	
	public StudentCompany get(EmbemdedStudentCompanyId id) {
		return studentCompanyRepo.findById(id).get();
	}
	
	public void delete(EmbemdedStudentCompanyId id) {
		studentCompanyRepo.deleteById(id);
	}
}
