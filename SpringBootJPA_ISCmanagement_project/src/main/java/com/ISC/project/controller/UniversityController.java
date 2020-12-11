package com.ISC.project.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
		if(univer.isEmpty()) {
			throw new ResourseNotFoundException("Not found university by id: " + id);
		}else {
			return new ResultRespon(0,"Success",univer);
		}

	}
	
	//post university
	@PostMapping(value = "/newUniversity")
	public ResultRespon addUniversity(@RequestBody University university) throws JsonMappingException,JsonProcessingException {
		List<University> univer = new ArrayList<>();
		univer.add(university);
		univer.get(0).setCreatedDate(LocalDateTime.now());
		if(this.universityService.checkNameUni(university.getNameUni()).isEmpty()) {
			this.universityService.save(univer.get(0));
			return new ResultRespon(0,"Add university success",univer);
		}else {
			throw new ResourseNotFoundException("Duplicate University Name");
		}
	}
	
	
	//update university
	@PutMapping(value="/editUniversity")
	public ResultRespon editUniversity(@RequestBody University university,@RequestParam("id") long id) {
		List<University> univer = new ArrayList<>();
		University olduniversity = universityService.findById(id).orElseThrow(()->new ResourseNotFoundException("not found university with id: " + id));
		if(this.universityService.checkNameUni(university.getNameUni()).isEmpty()) {
			olduniversity.setUpdatedDate(LocalDateTime.now());
			olduniversity.setNameUni(university.getNameUni());
			olduniversity.setAddressUni(university.getAddressUni());
			olduniversity.setContactPerson(university.getContactPerson());
			olduniversity.setUpdatedBy(university.getUpdatedBy());
			olduniversity.setNoteUni(university.getNoteUni());
			olduniversity.setWebsiteUni(university.getWebsiteUni());
			univer.add(olduniversity);
			this.universityService.save(univer.get(0));
			return new ResultRespon(0,"Update university success",univer);
		}else {
			throw new ResourseNotFoundException("Duplicate University Name");
		}

		
	}
	
	//delete university
	@DeleteMapping(value="/deleteUniversity")
	public ResultRespon deleteUniversity(@RequestParam("id") long id) {
		universityService.findById(id).orElseThrow(()->new ResourseNotFoundException("not found university with id: " + id));
		this.universityService.delete(id);
		return new ResultRespon(0,"Delete university id = "+id+ " success");
	}
	
	
	//search university by keyword
	@GetMapping(value="/searchUniversity")
	public ResultRespon searchUni(@RequestParam("keyWord") String keyWord) {
		if(this.universityService.searchUni(keyWord).isEmpty()) {
			throw new ResourseNotFoundException("Not found university by keyword " + keyWord);
		}else {
			return new ResultRespon(0,"Success",this.universityService.searchUni(keyWord));
		}
	}
	
	//Pagination
	@GetMapping("/university/pagination")
	public ResultRespon paginationUniversity(@RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
			@RequestParam(name = "size",required = false,defaultValue = "1") Integer size,
			@RequestParam(name = "sort",required = false,defaultValue = "1") String sort) {
		Sort sortable = null;
		if(sort.equals("ASC")) {
			sortable = Sort.by("nameUni").ascending();
		}
		if(sort.equals("DESC")) {
			sortable = Sort.by("nameUni").descending();
		}
		Pageable pageable = PageRequest.of(page, size,sortable);
		Page<University> uni = universityService.findUni(pageable);
		List<Page<University>> universities = new ArrayList<Page<University>>();
		universities.add(uni);
		return new ResultRespon(0,"Success",universities);
	}
}
