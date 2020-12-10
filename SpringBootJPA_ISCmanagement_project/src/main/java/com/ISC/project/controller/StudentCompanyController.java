package com.ISC.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ISC.project.payload.ResultRespon;
import com.ISC.project.service.StudentCompanyService;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class StudentCompanyController {
	@Autowired
	private StudentCompanyService studentCompanyService;
	
	//get all Student at Company
	@GetMapping("/listStudentCompany")
	public ResultRespon listStudentCompany() {
		return new ResultRespon(0,"Success",this.studentCompanyService.listAllStudentCompany());
	}
}
