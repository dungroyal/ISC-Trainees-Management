package com.ISC.project.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
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
import com.ISC.project.model.Major;
import com.ISC.project.payload.ResultRespon;
import com.ISC.project.service.MajorService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class MajorController {
	@Autowired
	private MajorService majorService;

	// get all major
	@GetMapping("/listMajor")
	public ResultRespon listMajor() {
		return new ResultRespon(0, "Success", this.majorService.listAllMajor());
	}

	// get one major
	@GetMapping("/getMajor")
	public ResultRespon getMajor(@RequestParam("id") long id) {
		List<Major> major = new ArrayList<Major>();
		major.add(majorService.findById(id)
				.orElseThrow(() -> new ResourseNotFoundException("not found major with id: " + id)));
		return new ResultRespon(0, "Success", major);
	}

	// post major
	@PostMapping("/newMajor")
	public ResultRespon addMajor(@RequestBody Major major) {
		List<Major> majorList = new ArrayList<Major>();
		major.setCreatedDate(LocalDateTime.now());
		if (this.majorService.checkCodeMajor(major.getCodeMajor()).isEmpty()) {
			majorList.add(majorService.save(major));
			return new ResultRespon(0, "Success", majorList);
		} else {
			throw new ResourseNotFoundException("Duplicate Major Code");
		}
	}

	// update major
	@PutMapping("/editMajor")
	public ResultRespon editMajor(@RequestBody Major major, @RequestParam("id") long id) {
		List<Major> majorList = new ArrayList<>();
		Major oldMajor = majorService.findById(id)
				.orElseThrow(() -> new ResourseNotFoundException("not found major with id: " + id));
		if (this.majorService.checkCodeMajor(major.getCodeMajor()).isEmpty()) {
			oldMajor.setUpdatedBy(major.getUpdatedBy());
			oldMajor.setUpdatedDate(LocalDateTime.now());
			oldMajor.setCodeMajor(major.getCodeMajor());
			oldMajor.setNameMajor(major.getNameMajor());
			oldMajor.setDescriptionMajor(major.getDescriptionMajor());
			majorList.add(oldMajor);
			this.majorService.save(majorList.get(0));
			return new ResultRespon(0, "Success", majorList);
		} else {
			throw new ResourseNotFoundException("Duplicate major code");
		}
	}

	// delete major
	@DeleteMapping("/deleteMajor")
	public ResultRespon deleteMajor(@RequestParam("id") long id) {
		majorService.findById(id).orElseThrow(() -> new ResourseNotFoundException("not found major with id: " + id));
		this.majorService.delete(id);
		return new ResultRespon(0, "Success");
	}
	
	//Pagination
	@GetMapping(value = "/major/pagination")
	public ResultRespon paginationMajor(@RequestParam(name = "page", required = false, defaultValue = "1") Integer page, 
			@RequestParam(name = "size", required = false, defaultValue = "1") Integer size,
			@RequestParam(name = "sort", required = false, defaultValue = "ASC") String sort) {
		Sort sortable = null;
		if(sort.equals("ASC")) {
			sortable = Sort.by("nameMajor").ascending();
		}
		if(sort.equals("DESC")) {
			sortable = Sort.by("nameMajor").descending();
		}
		Pageable pageable = PageRequest.of(page, size, sortable);
		Page<Major> major = majorService.findMajor(pageable);
		List<Page<Major>> majors = new ArrayList<Page<Major>>();
		majors.add(major);
		return new ResultRespon(0, "Success", majors);
	}
	
	//Search major by keyword
	@GetMapping(value = "/searchMajor")
	public ResultRespon searchMajor(@RequestParam("keyWord") String keyWord) {
		if(this.majorService.searchMajor(keyWord).isEmpty()) {
			throw new ResourceNotFoundException("Not found major by keyword " + keyWord);
		} else {
			return new ResultRespon(0, "Success", this.majorService.searchMajor(keyWord));
		}
	}
}
