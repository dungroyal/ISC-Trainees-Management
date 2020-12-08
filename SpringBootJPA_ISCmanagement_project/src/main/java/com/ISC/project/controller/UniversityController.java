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
import com.ISC.project.model.University;
import com.ISC.project.service.UniversityService;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class UniversityController {
	@Autowired
	private UniversityService universityService;
	
	//get all university
	@GetMapping("/listUniversity")
	public List<University> listuniversity(){
		return universityService.listAlluniversity();
	}
	
	//get one university
	@GetMapping("/getUniversity") 
	public University getuniversity(@RequestParam("id") long id) {
		return universityService.findById(id).orElseThrow(() ->new ResourseNotFoundException("not found university with id: " + id));
	}  
	
	//post university
	@PostMapping("/newUniversity")
	public University adduniversity(@RequestBody University university) {
	university.setCreatedDate(LocalDateTime.now());
		return universityService.save(university);
	}
	
	//update university
	@PutMapping("/editUniversity")
	public University edituniversity(@RequestBody University university,@RequestParam("id") long id) {
		University olduniversity = universityService.findById(id).orElseThrow(()->new ResourseNotFoundException("not found university with id: " + id));
		olduniversity.setNameUni(university.getNameUni());
		olduniversity.setNoteUni(university.getNoteUni());
		olduniversity.setAddressUni(university.getAddressUni());
		olduniversity.setContactPerson(university.getContactPerson());
		olduniversity.setWebsiteUni(university.getWebsiteUni());
		olduniversity.setUpdatedDate(LocalDateTime.now());
		return universityService.save(olduniversity);
		
	}
	
	//delete university
	@DeleteMapping("/deleteUniversity")
	public void deleteuniversity(@RequestParam("id") long id) {
		universityService.findById(id).orElseThrow(()->new ResourseNotFoundException("not found university with id: " + id));
		universityService.delete(id);
	}
}
