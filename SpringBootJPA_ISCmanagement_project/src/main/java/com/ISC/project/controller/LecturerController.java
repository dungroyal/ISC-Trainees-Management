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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ISC.project.exception.ResourseNotFoundException;
import com.ISC.project.model.Lecturer;
import com.ISC.project.model.StatusAc;
import com.ISC.project.payload.ResultRespon;
import com.ISC.project.service.LecturerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class LecturerController {
	@Autowired
	private LecturerService lecturerService;

	// get all lecturer
	@GetMapping("/listLecturer")
	public ResultRespon listLecturer() {
		return new ResultRespon(0, "Success", this.lecturerService.listAllLecture());
	}

	// get one lecturer
	@GetMapping("/getLecturer")
	public ResultRespon getLecturer(@RequestParam("id") long id) {
		List<Lecturer> lecturer = new ArrayList<>();
		lecturer.add(lecturerService.findById(id)
				.orElseThrow(() -> new ResourseNotFoundException("not found lecturer with id: " + id)));
		return new ResultRespon(0, "Success", lecturer);
	}

	// post lecturer
	@PostMapping(value = "/newLecturer", consumes = "multipart/form-data")
	public ResultRespon addLecturer(@RequestParam("createdBy") String createdBy,
			@RequestParam("updatedBy") String updatedBy, @RequestParam("codeLec") String codeLec,
			@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName,
			@RequestParam("addressLec") String addressLec, @RequestParam("phoneLec") String phoneLec,
			@RequestParam("emailLec") String emailLec, @RequestParam("degree") String degree,
			@RequestParam("image") MultipartFile image, @RequestParam("statusLec") StatusAc statusLec,
			@RequestParam("noteLec") String noteLec) throws JsonMappingException, JsonProcessingException {

		// Save lecturer
		List<Lecturer> lecturers = new ArrayList<Lecturer>();

		// Getting URL image
		String fileName = this.fileStorageService.storeFile(image);
		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/")
				.path(fileName).toUriString();

		// createDate = LocalDateTime.now()
		lecturers.add(new Lecturer(createdBy, updatedBy, codeLec, firstName, lastName, addressLec, phoneLec, emailLec,
				degree, image, statusLec, noteLec));
		lecturers.get(0).setCreatedDate(LocalDateTime.now());

		// check EXISTS of code Lecturer
		if (this.lecturerService.checkCodeLecturer(codeLec).isEmpty()
				&& this.lecturerService.checkEmailLecturer(emailLec).isEmpty()) {
			this.lecturerService.save(lecturers.get(0));
			return new ResultRespon(0, "Add Lecturer Success", lecturers);
		} else {
			if (!this.lecturerService.checkCodeLecturer(codeLec).isEmpty()) {
				throw new ResourseNotFoundException("Duplicate Lecturer Code");
			} else {
				throw new ResourseNotFoundException("Duplicate Lecturer Email");
			}
		}
	}

	// update lecturer
	@PutMapping("editLecturer")
	public ResultRespon editLecturer(@RequestParam("createdBy") String createdBy,
			@RequestParam("updatedBy") String updatedBy, @RequestParam("codeLec") String codeLec,
			@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName,
			@RequestParam("addressLec") String addressLec, @RequestParam("phoneLec") String phoneLec,
			@RequestParam("emailLec") String emailLec, @RequestParam("degree") String degree,
			@RequestParam("image") MultipartFile image, @RequestParam("statusLec") StatusAc statusLec,
			@RequestParam("noteLec") String noteLec) throws JsonMappingException, JsonProcessingException {
		List<Lecturer> lecturers = new ArrayList<Lecturer>();

		// Getting URL image
		String fileName = this.fileStorageService.storeFile(image);
		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/")
				.path(fileName).toUriString();

		// updateDate = LocalDateTime.now()
		lecturers.add(new Lecturer(createdBy, updatedBy, codeLec, firstName, lastName, addressLec, phoneLec, emailLec,
				degree, image, statusLec, noteLec));
		lecturers.get(0).setUpdatedDate(LocalDateTime.now());

		if (!this.lecturerService.checkCodeLecturer(codeLec).isEmpty()
				&& !this.lecturerService.checkEmailLecturer(emailLec).isEmpty()) {
			this.lecturerService.save(lecturers.get(0));
			return new ResultRespon(0, "Update Lecturer Success", lecturers);
		} else if (this.lecturerService.checkCodeLecturer(codeLec).isEmpty()
				&& !this.lecturerService.checkEmailLecturer(emailLec).isEmpty()) {
			if (!this.lecturerService.checkCodeLecturerUpdate(codeLec).contains(codeLec)) {
				this.lecturerService.save(lecturers.get(0));
				return new ResultRespon(0, "Update Lecturer Success", lecturers);
			}
			throw new ResourseNotFoundException("Duplicate Lecturer code");
		} else {
			for (int i = 0; i < this.lecturerService.checkEmailLecturer(emailLec).size(); i++) {
				if (emailLec.toUpperCase()
						.compareTo(this.lecturerService.checkEmailLecturer(emailLec).get(i).toUpperCase()) != 0) {
					this.lecturerService.save(lecturers.get(0));
					return new ResultRespon(0, "Update Lecturer Success", lecturers);
				}
			}
			throw new ResourseNotFoundException("Duplicate Lecturer email");
		}
	}

	// delete lecturer
	@DeleteMapping("/deleteLecturer")
	public ResultRespon deleteLecturer(@RequestParam("id") long id) {
		lecturerService.findById(id).orElseThrow(() -> new ResourseNotFoundException("not found major with id: " + id));
		this.lecturerService.delete(id);
		return new ResultRespon(0, "Success");
	}

	// Pagination
	@GetMapping(value = "/lecturer/pagination")
	public ResultRespon paginationLecturer(
			@RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
			@RequestParam(name = "size", required = false, defaultValue = "1") Integer size,
			@RequestParam(name = "sort", required = false, defaultValue = "ASC") String sort) {
		Sort sortable = null;
		if (sort.equals("ASC")) {
			sortable = Sort.by("lastName").ascending();
		}
		if (sort.equals("DESC")) {
			sortable = Sort.by("lastName").descending();
		}
		Pageable pageable = PageRequest.of(page, size, sortable);
		Page<Lecturer> lecturer = lecturerService.findLecturer(pageable);
		List<Page<Lecturer>> lecturers = new ArrayList<Page<Lecturer>>();
		lecturers.add(lecturer);
		return new ResultRespon(0, "Success", lecturers);
	}

	// Search lecturer by keyword
	@GetMapping(value = "/searchLecturer")
	public ResultRespon searchLecturer(@RequestParam("keyWord") String keyWord) {
		if (this.lecturerService.searchLecturer(keyWord).isEmpty()) {
			throw new ResourceNotFoundException("Not found lecturer by keyword " + keyWord);
		} else {
			return new ResultRespon(0, "Success", this.lecturerService.searchLecturer(keyWord));
		}
	}
}
