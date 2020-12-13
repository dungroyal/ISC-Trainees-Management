package com.ISC.project.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

@RestController
@RequestMapping(value = "/api")
@Tag(name = "Subject", description = "CRUD for Subject")
public class SubjectController {
	@Autowired
	private SubjectService subjectService;

	//Get All Subject
	// DOC for getAll Subjects
	@Operation(summary = "Get all Subjects", description = "Show all Subjects under the database")
	@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = Subject.class))),
	responseCode = "200", description = "Get all Subjects success")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "401", description = "Authorization Required"),
			@ApiResponse(responseCode = "403", description = "Forbidden"),
			@ApiResponse(responseCode = "500", description = "Internal Error Server")
	})
	@GetMapping(value = "/listSubject")
	public ResultRespon allSubject() {
		return new ResultRespon(0, "Success", this.subjectService.listAllSubject());
	}
	
	//Get One Subject
	// DOC for getOne  Subject
	@Operation(summary = "Get one Subjects", description = "Show one Subject under the database")
	@ApiResponse(responseCode = "200", description = "Get one Subjects success", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Subject.class)), mediaType = "application/json"))
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "401", description = "Authorization Required"),
			@ApiResponse(responseCode = "403", description = "Forbidden"),
			@ApiResponse(responseCode = "500", description = "Internal Error Server")
	})
	@GetMapping(value = "/getSubject")
	public ResultRespon getSubject(
			// DOC for id of Subject
			@Parameter(description = "The Subject's id is required", required = true)
			@RequestParam("id") Long id) {
		List<Subject> subjects = new ArrayList<>();
		subjects.add(this.subjectService.findById(id).orElseThrow(() -> new ResourseNotFoundException("Not Found Subject")));
		return new ResultRespon(0,"Success",subjects);
	}

		// Add new Subject
		// DOC for add new Subject
		@Operation(summary = "Add new Subject", description = "Add new Subject")
		@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = Subject.class))),
		responseCode = "200", description = "Add new Subject success")
		@ApiResponses(value = {
				@ApiResponse(responseCode = "200", description = "Success"),
				@ApiResponse(responseCode = "404", description = "Not found"),
				@ApiResponse(responseCode = "401", description = "Authorization Required"),
				@ApiResponse(responseCode = "403", description = "Forbidden"),
				@ApiResponse(responseCode = "500", description = "Internal Error Server")
		})
		@PostMapping("/addSubject")
		public ResultRespon addSubject(@RequestBody Subject subject) {
			if (subjectService.checkCodeSubject(subject.getCodeSub()).isEmpty()) {
				List<Subject> newSub = new ArrayList<Subject>();
				subject.setCreatedDate(LocalDateTime.now());
				newSub.add(subjectService.save(subject));
				return new ResultRespon(0, "success", newSub);
			}else {
				throw new ResourseNotFoundException("Duplicate Code");
			}
		}
		// Update Subject
		// DOC for update Subject
		@Operation(summary = "Update Subject with ID", description = "Update Subject with ID")
		@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = Subject.class))),
		responseCode = "200", description = "Update Subject success")
		@ApiResponses(value = {
				@ApiResponse(responseCode = "200", description = "Success"),
				@ApiResponse(responseCode = "404", description = "Not found"),
				@ApiResponse(responseCode = "401", description = "Authorization Required"),
				@ApiResponse(responseCode = "403", description = "Forbidden"),
				@ApiResponse(responseCode = "500", description = "Internal Error Server")
		})
		@PutMapping("/editSubject")
		public ResultRespon editSubject(@RequestBody Subject subject, @Parameter(description = "The Subject id is required", required = true)  @RequestParam("id") Long id) {
			List<Subject> newSubject = new ArrayList<>();
			Subject oldSubject = subjectService.findById(id).orElseThrow(()->new ResourseNotFoundException("Not found Subject with id: " + id));
			if(!this.subjectService.checkCodeSubject(subject.getCodeSub()).isEmpty()) {
					oldSubject.setCodeSub(subject.getCodeSub());
					oldSubject.setNameSub(subject.getNameSub());
					oldSubject.setCreditSub(subject.getCreditSub());
					oldSubject.setStatusSub(subject.getStatusSub());
					oldSubject.setNoteSub(subject.getNoteSub());
					oldSubject.setCreatedBy(subject.getCreatedBy());
					oldSubject.setUpdatedBy(subject.getUpdatedBy());
					oldSubject.setCreatedDate(subject.getCreatedDate());
					oldSubject.setUpdatedDate(LocalDateTime.now());
					newSubject.add(subjectService.save(oldSubject));
					return new ResultRespon(0,"Update success",newSubject);
			}else if(this.subjectService.checkCodeSubject(subject.getCodeSub()).isEmpty()){
				if(!this.subjectService.checkCodeSubject(subject.getCodeSub()).contains(subject.getCodeSub())) {
					System.out.println(this.subjectService.checkCodeSubject(subject.getCodeSub()));
					oldSubject.setCodeSub(subject.getCodeSub());
					oldSubject.setNameSub(subject.getNameSub());
					oldSubject.setCreditSub(subject.getCreditSub());
					oldSubject.setStatusSub(subject.getStatusSub());
					oldSubject.setNoteSub(subject.getNoteSub());
					oldSubject.setCreatedBy(subject.getCreatedBy());
					oldSubject.setUpdatedBy(subject.getUpdatedBy());
					oldSubject.setCreatedDate(subject.getCreatedDate());
					oldSubject.setUpdatedDate(LocalDateTime.now());
					newSubject.add(subjectService.save(oldSubject));
					return new ResultRespon(0,"Update success with new code Subject",newSubject);
				}
				throw new ResourseNotFoundException("Duplicate code Subject");
			}
			throw new ResourseNotFoundException("Duplicate code Subject");
		}
		
		// Delete Subject
		// DOC for delete Subject
		@Operation(summary = "Delete Subject with ID", description = "Delete Subject with ID")
		@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = Subject.class))),
		responseCode = "200", description = "Delete Subject success")
		@ApiResponses(value = {
				@ApiResponse(responseCode = "200", description = "Success"),
				@ApiResponse(responseCode = "404", description = "Not found"),
				@ApiResponse(responseCode = "401", description = "Authorization Required"),
				@ApiResponse(responseCode = "403", description = "Forbidden"),
				@ApiResponse(responseCode = "500", description = "Internal Error Server")
		})
		@DeleteMapping("/deleteSubject")
		public ResultRespon deleteSubject(
		@Parameter(description = "The Subject id is required", required = true) 
		@RequestParam("id") Long id) {
			subjectService.findById(id).orElseThrow(()->new ResourseNotFoundException("Not found Subject with id: " + id));
			this.subjectService.delete(id);
			return new ResultRespon(0,"Delete Subject witd id : "+id+" success");
		}
		
		// Pagination Subject
		// DOC for Pagination Subject
		@Operation(summary = "Pagination Subject", description = "Pagination Subject")
		@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = Subject.class))),
		responseCode = "200", description = "Pagination Subject success")
		@ApiResponses(value = {
				@ApiResponse(responseCode = "200", description = "Success"),
				@ApiResponse(responseCode = "404", description = "Not found"),
				@ApiResponse(responseCode = "401", description = "Authorization Required"),
				@ApiResponse(responseCode = "403", description = "Forbidden"),
				@ApiResponse(responseCode = "500", description = "Internal Error Server")
		})
		@GetMapping("/subject/pagination") 
		public ResultRespon paginationSubject(
				@RequestParam(name="page", required = false, defaultValue = "1") Integer page,
				@RequestParam(name="size", required = false, defaultValue = "1") Integer size,
				@RequestParam(name="sort", required = false, defaultValue = "ASC") String sort) {
			Sort sortable = null;
			if(sort.equals("ASC")) {
				sortable = Sort.by("nameSub").ascending();
			}
			if(sort.equals("DESC")) {
				sortable = Sort.by("nameSub").descending();
			}
			
			Pageable pageable = PageRequest.of(page, size, sortable);
			Page<Subject> subject = subjectService.findSubject(pageable);
			List<Page<Subject>> subjects = new ArrayList<>();
			subjects.add(subject);
			return new ResultRespon(0, "Success", subjects);
		}
		
		// Search Subject
		// DOC for search Subject
		@Operation(summary = "Search Subject with keyword", description = "Search Subject with keyword")
		@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = Subject.class))),
		responseCode = "200", description = "Search Subject success")
		@ApiResponses(value = {
				@ApiResponse(responseCode = "200", description = "Success"),
				@ApiResponse(responseCode = "404", description = "Not found"),
				@ApiResponse(responseCode = "401", description = "Authorization Required"),
				@ApiResponse(responseCode = "403", description = "Forbidden"),
				@ApiResponse(responseCode = "500", description = "Internal Error Server")
		})	
		@GetMapping("/searchSubject") 
		public ResultRespon searchSubject(
				@Parameter(description = "Search keyword id is required", required = true) 
				@RequestParam("keyWord") String keyWord) {
			if(this.subjectService.searchSubject(keyWord).isEmpty()) {
				throw new ResourseNotFoundException("Not found Subject witd keyword: "+keyWord);
			}else {
				return new ResultRespon(0, "Search success", this.subjectService.searchSubject(keyWord));
			}
		}
	}