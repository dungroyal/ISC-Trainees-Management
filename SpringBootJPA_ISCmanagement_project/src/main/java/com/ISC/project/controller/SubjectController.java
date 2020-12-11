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
import com.ISC.project.model.Subject;
import com.ISC.project.payload.ResultRespon;
import com.ISC.project.service.SubjectService;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class SubjectController {
	@Autowired
	private SubjectService subjectService;

	// get all subject
	@GetMapping("/listSubject")
	public ResultRespon listSubject() {
		return new ResultRespon(0, "Success", this.subjectService.listAllSubject());
	}

	// get one subject
	@GetMapping("/getSubject")
	public ResultRespon getSubject(@RequestParam("id") long id) {
		List<Subject> subject = new ArrayList<Subject>();
		subject.add(subjectService.findById(id)
				.orElseThrow(() -> new ResourseNotFoundException("not found subject with id: " + id)));
		return new ResultRespon(0, "Success", subject);
	}

	// post subject
	@PostMapping("/newSubject")
	public ResultRespon addSubject(@RequestBody Subject subject) {
		List<Subject> subjectList = new ArrayList<Subject>();
		subject.setCreatedDate(LocalDateTime.now());
		if (this.subjectService.checkCodeSubject(subject.getCodeSub()).isEmpty()) {
			subjectList.add(subjectService.save(subject));
			return new ResultRespon(0, "Success", subjectList);
		} else {
			throw new ResourseNotFoundException("Duplicate Subject Code");
		}
	}

	// update Subject
	@PutMapping("/editSubject")
	public ResultRespon editSubject(@RequestBody Subject subject, @RequestParam("id") long id) {
		List<Subject> subjectList = new ArrayList<Subject>();
		Subject oldSubject = subjectService.findById(id)
				.orElseThrow(() -> new ResourseNotFoundException("not found subject with id: " + id));
		if (this.subjectService.checkCodeSubject(subject.getCodeSub()).isEmpty()) {
			oldSubject.setUpdatedBy(subject.getUpdatedBy());
			oldSubject.setUpdatedDate(LocalDateTime.now());
			oldSubject.setCodeSub(subject.getCodeSub());
			oldSubject.setNameSub(subject.getNameSub());
			oldSubject.setCreditSub(subject.getCreditSub());
			oldSubject.setPassCore(subject.getPassCore());
			oldSubject.setStatusSub(subject.getStatusSub());
			oldSubject.setNoteSub(subject.getNoteSub());
			subjectList.add(oldSubject);
			this.subjectService.save(subjectList.get(0));
			return new ResultRespon(0, "Success", subjectList);
		} else {
			throw new ResourseNotFoundException("Duplicate subject code");
		}
	}

	// delete subject
	@DeleteMapping("/deleteSubject")
	public ResultRespon deleteSubject(@RequestParam("id") long id) {
		subjectService.findById(id)
				.orElseThrow(() -> new ResourseNotFoundException("not found subject with id: " + id));
		this.subjectService.delete(id);
		return new ResultRespon(0, "Success");
	}

	// Pagination
	@GetMapping(value = "/subject/pagination")
	public ResultRespon paginationSubject(
			@RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
			@RequestParam(name = "size", required = false, defaultValue = "1") Integer size,
			@RequestParam(name = "sort", required = false, defaultValue = "ASC") String sort) {
		Sort sortable = null;
		if (sort.equals("ASC")) {
			sortable = Sort.by("nameSub").ascending();
		}
		if (sort.equals("DESC")) {
			sortable = Sort.by("nameSub").descending();
		}
		Pageable pageable = PageRequest.of(page, size, sortable);
		Page<Subject> subject = subjectService.findSubject(pageable);
		List<Page<Subject>> subjects = new ArrayList<Page<Subject>>();
		subjects.add(subject);
		return new ResultRespon(0, "Success", subjects);
	}

	// Search subject by keyword
	@GetMapping(value = "/searchSubject")
	public ResultRespon searchSubject(@RequestParam("keyWord") String keyWord) {
		if (this.subjectService.searchSubject(keyWord).isEmpty()) {
			throw new ResourceNotFoundException("Not found subject by keyword " + keyWord);
		} else {
			return new ResultRespon(0, "Success", this.subjectService.searchSubject(keyWord));
		}
	}
}
