package com.ISC.project.controller;

import java.time.LocalDateTime;
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
import com.ISC.project.model.School;
import com.ISC.project.service.SchoolService;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class SchoolController {
	@Autowired
	private SchoolService schoolService;
	
	//get all school
	@GetMapping("/listSchool")
	public List<School> listSchool(){
		return schoolService.listAllSchool();
	}
	
	//get one school
	@GetMapping("/getSchool") 
	public School getSchool(@RequestParam("id") long id) {
		return schoolService.findById(id).orElseThrow(() ->new ResourseNotFoundException("not found school with id: " + id));
	}  
	
	//post school
	@PostMapping("/newSchool")
	public School addSchool(@RequestBody School school) {
	school.setCreatedDate(LocalDateTime.now());
		return schoolService.save(school);
	}
	
	//update school
	@PutMapping("/editSchool")
	public School editSchool(@RequestBody School school,@RequestParam("id") long id) {
		School oldSchool = schoolService.findById(id).orElseThrow(()->new ResourseNotFoundException("not found school with id: " + id));
		oldSchool.setNameShc(school.getNameShc());
		oldSchool.setNoteShc(school.getNoteShc());
		oldSchool.setAddressShc(school.getAddressShc());
		oldSchool.setContactPerson(school.getContactPerson());
		oldSchool.setWebsiteShc(school.getWebsiteShc());
		oldSchool.setUpdatedDate(LocalDateTime.now());
		return schoolService.save(oldSchool);
		
	}
	
	//delete school
	@DeleteMapping("/deleteSchool")
	public void deleteSchool(@RequestParam("id") long id) {
		schoolService.findById(id).orElseThrow(()->new ResourseNotFoundException("not found school with id: " + id));
		schoolService.delete(id);
	}
}
