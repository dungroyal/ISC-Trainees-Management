package com.ISC.project.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.ISC.project.dao.StudentRepository;
import com.ISC.project.model.Student;

@Service
@Transactional
public class StudentService {
	@Autowired
	private StudentRepository studentRepository;
	
	public Optional<Student> findById(long id){
		return studentRepository.findById(id);
	}
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
	
	public void deleteById(long id) {
		this.studentRepository.deleteById(id);
	}
	
	//Check Code Student
	public List<String> checkCodeStu(@RequestParam("newCodeStu") String newCodeStu) {
		return this.studentRepository.checkCodeStudent(newCodeStu);
	}
	
	//Check Email Student
	public List<String> checkEmailStu(@RequestParam("newEmailStu") String newEmailStu) {
		return this.studentRepository.checkEmailStudent(newEmailStu);
	}
	
	//Check Code Student For Upadate
	public List<String> checkCodeStuUpdate(@RequestParam("updateCodeStu") String updateCodeStu) {
		return this.studentRepository.checkCodeStudentUpdate(updateCodeStu);
	}
	
	//Check Email Student
	public List<String> checkEmailStuUpdate(@RequestParam("updateEmailStu") String updateEmailStu) {
		return this.studentRepository.checkEmailStudent(updateEmailStu);
	}
	//Pagination
	public Page<Student> findStudent(Pageable pageable) {
		return studentRepository.findStu(pageable);
	}
	
	//Search Student
	public List<Student> searchStudent(String keyWord) {
		return this.studentRepository.searchStudent(keyWord);
	}
}
