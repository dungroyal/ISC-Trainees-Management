package com.ISC.project.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ISC.project.exception.ResourseNotFoundException;
import com.ISC.project.model.University;
import com.ISC.project.payload.ResultRespon;
import com.ISC.project.service.UniversityService;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class UniversityController {
	@Autowired
	private UniversityService universityService;
	
	//get all university
	@GetMapping("/listUniversity")
	public ResultRespon listUniversity() {
		return new ResultRespon(0,"Success",this.universityService.listAlluniversity());
	}
	
	//get one university
	@GetMapping("/getUniversity") 
	public ResultRespon getUniversity(@RequestParam("id") long id) {
		List<University> univer = new ArrayList<>();
		univer.add(universityService.findById(id).orElseThrow(() -> new ResourseNotFoundException("not found university with id: " + id)));
		return new ResultRespon(0,"Success",univer);
	}
	
	//post university
	@PostMapping("/newUniversity")
	public ResultRespon addUniversity(@RequestBody University university) {
		List<University> univer = new ArrayList<>();
		university.setCreatedDate(LocalDateTime.now());
		univer.add(universityService.save(university));
		return new ResultRespon(0,"Success",univer);
	}
	
	
	//update university
	@PutMapping("/editUniversity")
	public ResultRespon editUniversity(@RequestBody University university,@RequestParam("id") long id) {
		List<University> univer = new ArrayList<>();
		University olduniversity = universityService.findById(id).orElseThrow(()->new ResourseNotFoundException("not found university with id: " + id));
		univer.add(universityService.save(olduniversity));
		return new ResultRespon(0,"Success",univer);
		
	}
	
	//delete university
	@DeleteMapping("/deleteUniversity")
	public ResultRespon deleteUniversity(@RequestParam("id") long id) {
		universityService.findById(id).orElseThrow(()->new ResourseNotFoundException("not found university with id: " + id));
		this.universityService.delete(id);
		return new ResultRespon(0,"Success");
	}
	
}
