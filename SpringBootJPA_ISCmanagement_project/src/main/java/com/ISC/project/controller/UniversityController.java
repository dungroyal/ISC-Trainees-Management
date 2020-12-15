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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ISC.project.exception.ResourseNotFoundException;
import com.ISC.project.model.Company;
import com.ISC.project.model.University;
import com.ISC.project.payload.ResultRespon;
import com.ISC.project.service.UniversityService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin
@RestController
@RequestMapping("/api")
@Tag(name = "University", description = "CRUD for University")
public class UniversityController {
	@Autowired
	private UniversityService universityService;

	// get all university
	// Doc for getAll university
	@Operation(summary = "Get all universities", description = "Show all universities under the databse")
	@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = University.class))), responseCode = "200", description = "Get all universities success")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "401", description = "Authorization Required"),
			@ApiResponse(responseCode = "403", description = "Forbidden"),
			@ApiResponse(responseCode = "500", description = "Internal Error Server") })
	@GetMapping("/listUniversity")
	public ResultRespon listUniversity() {
		return new ResultRespon(0, "Success", this.universityService.listAlluniversity());
	}

	// get one university
	// DOC for getOne university
	@Operation(summary = "Get one universities", description = "Show one university under the database")
	@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = University.class))), responseCode = "200", description = "Get one universities success")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "401", description = "Authorization Required"),
			@ApiResponse(responseCode = "403", description = "Forbidden"),
			@ApiResponse(responseCode = "500", description = "Internal Error Server") })
	@GetMapping("/getUniversity")
	public ResultRespon getUniversity(@RequestParam("id") long id) {
		List<University> univer = new ArrayList<>();
		univer.add(universityService.findById(id)
				.orElseThrow(() -> new ResourseNotFoundException("not found university with id: " + id)));
		return new ResultRespon(0, "Success", univer);

	}

	// post university
	// DOC for add new university
	@Operation(summary = "Add new university", description = "Add new university from the database")
	@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = University.class))), responseCode = "200", description = "Add universities success")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "401", description = "Authorization Required"),
			@ApiResponse(responseCode = "403", description = "Forbidden"),
			@ApiResponse(responseCode = "500", description = "Internal Error Server") })
	@PostMapping(value = "/newUniversity")
	public ResultRespon addUniversity(@RequestBody University university)
			throws JsonMappingException, JsonProcessingException {
		List<University> univer = new ArrayList<>();
		univer.add(university);
		univer.get(0).setCreatedDate(LocalDateTime.now());
		if (this.universityService.checkNameUni(university.getNameUni()).isEmpty()) {
			this.universityService.save(univer.get(0));
			return new ResultRespon(0, "Add university success", univer);
		} else {
			throw new ResourseNotFoundException("Duplicate University Name");
		}
	}

	// update university
	// DOC for update university
	@Operation(summary = "update university", description = "update university from the database")
	@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = Company.class))), responseCode = "200", description = "update university success")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "401", description = "Authorization Required"),
			@ApiResponse(responseCode = "403", description = "Forbidden"),
			@ApiResponse(responseCode = "500", description = "Internal Error Server") })
	@PutMapping(value = "/editUniversity")
	public ResultRespon editUniversity(@RequestBody University university, @RequestParam("id") long id) {
		List<University> univer = new ArrayList<>();
		University olduniversity = universityService.findById(id)
				.orElseThrow(() -> new ResourseNotFoundException("not found university with id: " + id));

		if (this.universityService.getNameById(id).equals(university.getNameUni())) {
			olduniversity.setUpdatedDate(LocalDateTime.now());
			olduniversity.setAddressUni(university.getAddressUni());
			olduniversity.setContactPerson(university.getContactPerson());
			olduniversity.setUpdatedBy(university.getUpdatedBy());
			olduniversity.setNoteUni(university.getNoteUni());
			olduniversity.setWebsiteUni(university.getWebsiteUni());
			univer.add(olduniversity);
			this.universityService.save(univer.get(0));
			return new ResultRespon(0, "Update university success", univer);
		} else {
			if (this.universityService.checkNameUni(university.getNameUni()).isEmpty()) {
				olduniversity.setUpdatedDate(LocalDateTime.now());
				olduniversity.setNameUni(university.getNameUni());
				olduniversity.setAddressUni(university.getAddressUni());
				olduniversity.setContactPerson(university.getContactPerson());
				olduniversity.setUpdatedBy(university.getUpdatedBy());
				olduniversity.setNoteUni(university.getNoteUni());
				olduniversity.setWebsiteUni(university.getWebsiteUni());
				univer.add(olduniversity);
				this.universityService.save(univer.get(0));
				return new ResultRespon(0, "Update university success", univer);
			} else {
				throw new ResourseNotFoundException("Duplicate University Name");
			}
		}

	}

	// delete university
	// DOC for delete university
	@Operation(summary = "delete university", description = "delete university from the database")
	@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = Company.class))), responseCode = "200", description = "delete university success")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "401", description = "Authorization Required"),
			@ApiResponse(responseCode = "403", description = "Forbidden"),
			@ApiResponse(responseCode = "500", description = "Internal Error Server") })
	@DeleteMapping(value = "/deleteUniversity")
	public ResultRespon deleteUniversity(@RequestParam("id") long id) {
		universityService.findById(id)
				.orElseThrow(() -> new ResourseNotFoundException("not found university with id: " + id));
		this.universityService.delete(id);
		return new ResultRespon(0, "Delete university id = " + id + " success");
	}

	// search university by keyword
	// DOC for search university
	@Operation(summary = "search university", description = "search university from the database")
	@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = Company.class))), responseCode = "200", description = "search university success")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "401", description = "Authorization Required"),
			@ApiResponse(responseCode = "403", description = "Forbidden"),
			@ApiResponse(responseCode = "500", description = "Internal Error Server") })
	@GetMapping(value = "/searchUniversity")
	public ResultRespon searchUni(@RequestParam("keyWord") String keyWord) {
		if (this.universityService.searchUni(keyWord).isEmpty()) {
			throw new ResourseNotFoundException("Not found university by keyword " + keyWord);
		} else {
			return new ResultRespon(0, "Success", this.universityService.searchUni(keyWord));
		}
	}

	// Pagination
	// DOC for Pagination university
	@Operation(summary = "Pagination university", description = "Pagination university from the database")
	@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = Company.class))), responseCode = "200", description = "Pagination university success")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "401", description = "Authorization Required"),
			@ApiResponse(responseCode = "403", description = "Forbidden"),
			@ApiResponse(responseCode = "500", description = "Internal Error Server") })
	@GetMapping("/university/pagination")
	public ResultRespon paginationUniversity(
			@RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
			@RequestParam(name = "size", required = false, defaultValue = "1") Integer size,
			@RequestParam(name = "sort", required = false, defaultValue = "1") String sort) {
		Sort sortable = null;
		if (sort.equals("ASC")) {
			sortable = Sort.by("nameUni").ascending();
		}
		if (sort.equals("DESC")) {
			sortable = Sort.by("nameUni").descending();
		}
		Pageable pageable = PageRequest.of(page, size, sortable);
		Page<University> uni = universityService.findUni(pageable);
		List<Page<University>> universities = new ArrayList<Page<University>>();
		universities.add(uni);
		return new ResultRespon(0, "Success", universities);
	}
}