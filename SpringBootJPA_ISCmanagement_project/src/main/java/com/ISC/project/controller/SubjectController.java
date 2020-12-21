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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/subject")
@Tag(name = "Subject", description = "CRUD for subject")
public class SubjectController {
	@Autowired
	private SubjectService subjectService;

	// get all subject
	// Doc for getAll subject
	@Operation(summary = "Get all subjects", description = "Show all subjects under the databse")
	@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = Subject.class))), responseCode = "200", description = "Get all subjects success")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "401", description = "Authorization Required"),
			@ApiResponse(responseCode = "403", description = "Forbidden"),
			@ApiResponse(responseCode = "500", description = "Internal Error Server") })
	@GetMapping(value = "/listSubject")
	public ResultRespon listSubject() {
		return new ResultRespon(0, "Success", this.subjectService.listAllSubject());
	}

	// get one subject
	// DOC for getOne subject
	@Operation(summary = "Get one subject", description = "Show one subject under the database")
	@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = Subject.class))), responseCode = "200", description = "Get one subject success")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "401", description = "Authorization Required"),
			@ApiResponse(responseCode = "403", description = "Forbidden"),
			@ApiResponse(responseCode = "500", description = "Internal Error Server") })

	@GetMapping(value = "/getSubject")
	public ResultRespon getSubject(
			@Parameter(description = "The subject's id is required", required = true) @RequestParam("id") long id) {
		List<Subject> subject = new ArrayList<Subject>();
		subject.add(subjectService.findById(id).orElseThrow(() -> new ResourseNotFoundException("Not found subject")));
		return new ResultRespon(0, "Success", subject);
	}

	// post subject
	// DOC for add new subject
	@Operation(summary = "Add new subject", description = "Add new subject from the database")
	@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = Subject.class))), responseCode = "200", description = "Add subject success")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "401", description = "Authorization Required"),
			@ApiResponse(responseCode = "403", description = "Forbidden"),
			@ApiResponse(responseCode = "500", description = "Internal Error Server") })
	@PostMapping(value = "/newSubject")
	public ResultRespon addSubject(@RequestBody Subject subject) {
		List<Subject> subjectList = new ArrayList<Subject>();
		subject.setCreatedDate(LocalDateTime.now());
		if (this.subjectService.checkCodeSubject(subject.getCodeSub()).isEmpty()) {
			subjectList.add(subjectService.save(subject));
			return new ResultRespon(0, "Add Subject Success", subjectList);
		} else {
			throw new ResourseNotFoundException("Duplicate Subject Code");
		}
	}

	// update Subject
	// DOC for update subject
	@Operation(summary = "Update subject", description = "Update subject from the database")
	@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = Subject.class))), responseCode = "200", description = "Update subject success")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "401", description = "Authorization Required"),
			@ApiResponse(responseCode = "403", description = "Forbidden"),
			@ApiResponse(responseCode = "500", description = "Internal Error Server") })
	@PutMapping(value = "/editSubject")
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
			return new ResultRespon(0, "Update subject success", subjectList);
		} else {
			if (this.subjectService.checkCodeSubject(subject.getCodeSub()).isEmpty()) {
				oldSubject.setCodeSub(subject.getCodeSub());
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
				return new ResultRespon(0, "Update subject success", subjectList);
			} else {
				throw new ResourseNotFoundException("Duplicate Subject Code");
			}
		}
	}

	// delete subject
	@Operation(summary = "Delete subject", description = "Delete subject from the database")
	@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = Subject.class))), responseCode = "200", description = "Delete major success")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "401", description = "Authorization Required"),
			@ApiResponse(responseCode = "403", description = "Forbidden"),
			@ApiResponse(responseCode = "500", description = "Internal Error Server") })
	@DeleteMapping("/deleteSubject")
	public ResultRespon deleteSubject(
			@Parameter(description = "The lecturer's id is required", required = true) @RequestParam("id") long id) {
		Subject subject = this.subjectService.findById(id)
				.orElseThrow(() -> new ResourseNotFoundException("Not Found Subject"));
		try {
			this.subjectService.delete(subject.getId());
		} catch (Exception e) {
			throw new ResourseNotFoundException("Subject can not be delete");
		}
		return new ResultRespon(0, "Delete Subject Success");
	}

	// Pagination
	@Operation(summary = "Pagination subject", description = "Pagination subject")
	@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = Subject.class))), responseCode = "200", description = "Success")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "401", description = "Authorization Required"),
			@ApiResponse(responseCode = "403", description = "Forbidden"),
			@ApiResponse(responseCode = "500", description = "Internal Error Server") })
	@GetMapping(value = "/pagination")
	public ResultRespon paginationSubject(
			@Parameter(description = "Number of page", required = false) @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
			@Parameter(description = "Items in page", required = false) @RequestParam(name = "size", required = false, defaultValue = "1") Integer size,
			@Parameter(description = "Sort by filed of Intems", required = false) @RequestParam(name = "sort", required = false, defaultValue = "ASC") String sort) {
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
	@Operation(summary = "Search subject", description = "Search subject")
	@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = Subject.class))), responseCode = "200", description = "Success")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "401", description = "Authorization Required"),
			@ApiResponse(responseCode = "403", description = "Forbidden"),
			@ApiResponse(responseCode = "500", description = "Internal Error Server") })
	@GetMapping(value = "/searchSubject")
	public ResultRespon searchSubject(
			@Parameter(description = "Enter the keywords you want to search", required = false) @RequestParam("keyWord") String keyWord) {
		if (this.subjectService.searchSubject(keyWord).isEmpty()) {
			throw new ResourceNotFoundException("Not found subject by keyword " + keyWord);
		} else {
			System.out.println(this.subjectService.searchSubject(keyWord).toString());
			return new ResultRespon(0, "Search Success", this.subjectService.searchSubject(keyWord));
		}
	}
}
