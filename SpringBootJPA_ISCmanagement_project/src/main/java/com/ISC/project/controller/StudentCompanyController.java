package com.ISC.project.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ISC.project.exception.ResourseNotFoundException;
import com.ISC.project.model.EmbemdedStudentCompanyId;
import com.ISC.project.model.Student;
import com.ISC.project.model.StudentCompany;
import com.ISC.project.payload.ResultRespon;
import com.ISC.project.service.CompanyService;
import com.ISC.project.service.StudentCompanyService;
import com.ISC.project.service.StudentService;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class StudentCompanyController {
	@Autowired
	private StudentCompanyService studentCompanyService;
	@Autowired
	private StudentService studentService;
	@Autowired
	private CompanyService companyService;
	
	//get all Student at Company
	@GetMapping("/listStudentCompany")
	public ResultRespon listStudentCompany() {
		return new ResultRespon(0,"Success",this.studentCompanyService.listAllStudentCompany());
	}
	
	@GetMapping("/getStudentCompany")
	public ResultRespon getStudentCompany(@RequestParam("studentId") Long studentId) {
		if(studentCompanyService.getStudentCompany(studentId).isEmpty()) {
			throw new ResourseNotFoundException("Not found");
		}else {
			return new ResultRespon(0,"Success",this.studentCompanyService.getStudentCompany(studentId));
		}
	}
	
	@PostMapping("/addStudentCompany")
	public ResultRespon addStudentCompany(@RequestParam("studentId") Long studentId,@RequestParam("companyId") Long [] companyId) {
		List<StudentCompany> studentCompanies = new ArrayList<StudentCompany>();
		Student student = this.studentService.findById(studentId).orElseThrow(()-> new ResourseNotFoundException("Not found student"));
		for(int i=0; i< companyId.length; i++) {
			StudentCompany studentCompany = new StudentCompany();
			studentCompany.setStudent(student);
			studentCompany.setCompany(this.companyService.findById(companyId[i])
					.orElseThrow(()-> new ResourseNotFoundException("Not found company id")));
			studentCompanies.add(this.studentCompanyService.save(studentCompany));
		}
		return new ResultRespon(0, "Add Company of Student success", studentCompanies);
	}
	
	@PutMapping("/updateStudentCompany")
	public ResultRespon updateStudentCompany(@RequestParam("studentId") Long studentId, @RequestParam("companyId") Long [] companyId
			, @RequestParam("newCompanyId") Long [] newCompanyId) {
		List<StudentCompany> studentCompanies = new ArrayList<StudentCompany>();
		
		Student student = this.studentService.findById(studentId).orElseThrow(()-> new ResourseNotFoundException("Not found student"));
		
		for(int i=0; i< companyId.length; i++) {
			this.studentCompanyService.delete(new EmbemdedStudentCompanyId(studentId, companyId[i]));
		}
		
		for(int j=0; j <= newCompanyId.length - 1; j++) {
			StudentCompany studentCompany = new StudentCompany();
			studentCompany.setStudent(student);
			studentCompany.setCompany(this.companyService.findById(newCompanyId[j]).orElseThrow(()-> new ResourseNotFoundException("Not found company")));
			studentCompanies.add(this.studentCompanyService.save(studentCompany));
		}
		return new ResultRespon(0, "Update Company of Student success", studentCompanies);
	}
	
	@DeleteMapping("/deleteStudentCompany")
	public ResultRespon deleteStudentCompany(@RequestParam("studentId") Long studentId,@RequestParam("companyId") Long companyId) {
		this.studentCompanyService.findById(new EmbemdedStudentCompanyId(studentId, companyId))
		.orElseThrow(()-> new ResourseNotFoundException("Not found StudentCompany"));
		try {
			this.studentCompanyService.delete(new EmbemdedStudentCompanyId(studentId, companyId));
			return new ResultRespon(0,"Delete StudentCompany success");
		} catch (Exception e) {
			throw new ResourseNotFoundException("StudentCompany can not be delete!");
		}
	}
}
	

