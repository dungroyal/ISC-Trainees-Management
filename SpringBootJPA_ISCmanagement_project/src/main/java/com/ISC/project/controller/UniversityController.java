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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

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
	@PostMapping(value = "/newUniversity",consumes = "multipart/form-data")
	public ResultRespon addUniversity(
			@RequestParam("nameUni") String nameUni,
			@RequestParam("addressUni") String addressUni,
			@RequestParam("contactPerson") String contactPerson,
			@RequestParam("websiteUni") String websiteUni,
			@RequestParam("noteUni") String noteUni,
			@RequestParam("createdBy") String createdBy,
			@RequestParam("updatedBy") String updatedBy) throws JsonMappingException,JsonProcessingException {
		List<University> univer = new ArrayList<>();
		univer.add(new University(createdBy, updatedBy, nameUni, addressUni, contactPerson, websiteUni, noteUni));
		univer.get(0).setCreatedDate(LocalDateTime.now());
		if(this.universityService.checkNameUni(nameUni).isEmpty()) {
			this.universityService.save(univer.get(0));
			return new ResultRespon(0,"Add university success",univer);
		}else {
			throw new ResourseNotFoundException("Duplicate University Name");
		}
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
