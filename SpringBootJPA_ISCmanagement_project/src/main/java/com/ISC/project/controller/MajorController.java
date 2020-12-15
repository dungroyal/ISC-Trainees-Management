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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping(value = "/api/major")
@Tag(name = "Major", description = "CRUD for major")
public class MajorController {
	@Autowired
	private MajorService majorService;

	// get all major
	// Doc for getAll major
	@Operation(summary = "Get all majors", description = "Show all majors under the databse")
	@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = Major.class))), responseCode = "200", description = "Get all majors success")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "401", description = "Authorization Required"),
			@ApiResponse(responseCode = "403", description = "Forbidden"),
			@ApiResponse(responseCode = "500", description = "Internal Error Server") })
	@GetMapping(value = "/listMajor")
	public ResultRespon listMajor() {
		return new ResultRespon(0, "Success", this.majorService.listAllMajor());
	}

	// get one major
	// DOC for getOne major
	@Operation(summary = "Get one major", description = "Show one major under the database")
	@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = Major.class))), responseCode = "200", description = "Get one major success")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "401", description = "Authorization Required"),
			@ApiResponse(responseCode = "403", description = "Forbidden"),
			@ApiResponse(responseCode = "500", description = "Internal Error Server") })

	@GetMapping(value = "/getMajor")
	public ResultRespon getMajor(
			@Parameter(description = "The major's id is required", required = true) @RequestParam("id") long id) {
		List<Major> major = new ArrayList<Major>();
		major.add(majorService.findById(id).orElseThrow(() -> new ResourseNotFoundException("Not found major")));
		return new ResultRespon(0, "Success", major);
	}

	// post major
	// DOC for add new major
	@Operation(summary = "Add new major", description = "Add new major from the database")
	@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = Major.class))), responseCode = "200", description = "Add major success")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "401", description = "Authorization Required"),
			@ApiResponse(responseCode = "403", description = "Forbidden"),
			@ApiResponse(responseCode = "500", description = "Internal Error Server") })
	@PostMapping(value = "/newMajor")
	public ResultRespon addMajor(@RequestBody Major major) {
		List<Major> majorList = new ArrayList<Major>();
		major.setCreatedDate(LocalDateTime.now());
		if (this.majorService.checkCodeMajor(major.getCodeMajor()).isEmpty()) {
			majorList.add(majorService.save(major));
			return new ResultRespon(0, "Add Major Success", majorList);
		} else {
			throw new ResourseNotFoundException("Duplicate Major Code");
		}
	}

	// update major
	// DOC for update major
	@Operation(summary = "Update Major", description = "Update major from the database")
	@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = Major.class))), responseCode = "200", description = "Update major success")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "401", description = "Authorization Required"),
			@ApiResponse(responseCode = "403", description = "Forbidden"),
			@ApiResponse(responseCode = "500", description = "Internal Error Server") })
	@PutMapping(value = "/editMajor")
	public ResultRespon editMajor(@RequestBody Major major, @RequestParam("id") long id) {
		List<Major> majorList = new ArrayList<>();
		Major oldMajor = majorService.findById(id).orElseThrow(() -> new ResourseNotFoundException("Not found major"));
		if (this.majorService.checkCodeMajor(major.getCodeMajor()).isEmpty()) {
			oldMajor.setUpdatedBy(major.getUpdatedBy());
			oldMajor.setUpdatedDate(LocalDateTime.now());
			oldMajor.setCodeMajor(major.getCodeMajor());
			oldMajor.setNameMajor(major.getNameMajor());
			oldMajor.setDescriptionMajor(major.getDescriptionMajor());
			majorList.add(oldMajor);
			this.majorService.save(majorList.get(0));
			return new ResultRespon(0, "Update Major Success", majorList);
		} else {
			if (this.majorService.checkCodeMajor(major.getCodeMajor()).isEmpty()) {
				oldMajor.setCodeMajor(major.getCodeMajor());
				oldMajor.setUpdatedBy(major.getUpdatedBy());
				oldMajor.setUpdatedDate(LocalDateTime.now());
				oldMajor.setCodeMajor(major.getCodeMajor());
				oldMajor.setNameMajor(major.getNameMajor());
				oldMajor.setDescriptionMajor(major.getDescriptionMajor());
				majorList.add(oldMajor);
				this.majorService.save(majorList.get(0));
				return new ResultRespon(0, "Update Major Success", majorList);
			} else {
				throw new ResourseNotFoundException("Duplicate Major Code");
			}
		}
	}

	// delete major
	@Operation(summary = "Delete major", description = "Delete major from the database")
	@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = Major.class))), responseCode = "200", description = "Delete major success")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "401", description = "Authorization Required"),
			@ApiResponse(responseCode = "403", description = "Forbidden"),
			@ApiResponse(responseCode = "500", description = "Internal Error Server") })
	@DeleteMapping("/deleteMajor")
	public ResultRespon deleteMajor(
			@Parameter(description = "The major's id is required", required = true) @RequestParam("id") long id) {
		Major major = this.majorService.findById(id)
				.orElseThrow(() -> new ResourseNotFoundException("Not Found Major"));
		try {
			this.majorService.delete(major.getId());
		} catch (Exception e) {
			throw new ResourseNotFoundException("Major can not be delete");
		}
		return new ResultRespon(0, "Delete Major Success");
	}

	// Pagination
	@Operation(summary = "Pagination major", description = "Pagination major")
	@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = Major.class))), responseCode = "200", description = "Success")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "401", description = "Authorization Required"),
			@ApiResponse(responseCode = "403", description = "Forbidden"),
			@ApiResponse(responseCode = "500", description = "Internal Error Server") })
	@GetMapping(value = "/major/pagination")
	public ResultRespon paginationMajor(
			@Parameter(description = "Number of page", required = false) @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
			@Parameter(description = "Items in page", required = false) @RequestParam(name = "size", required = false, defaultValue = "1") Integer size,
			@Parameter(description = "Sort by filed of Intems", required = false) @RequestParam(name = "sort", required = false, defaultValue = "ASC") String sort) {
		Sort sortable = null;
		if (sort.equals("ASC")) {
			sortable = Sort.by("nameMajor").ascending();
		}
		if (sort.equals("DESC")) {
			sortable = Sort.by("nameMajor").descending();
		}
		Pageable pageable = PageRequest.of(page, size, sortable);
		Page<Major> major = majorService.findMajor(pageable);
		List<Page<Major>> majors = new ArrayList<Page<Major>>();
		majors.add(major);
		return new ResultRespon(0, "Success", majors);
	}

	// Search major by keyword
	@Operation(summary = "Search major", description = "Search major")
	@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = Major.class))), responseCode = "200", description = "Success")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "401", description = "Authorization Required"),
			@ApiResponse(responseCode = "403", description = "Forbidden"),
			@ApiResponse(responseCode = "500", description = "Internal Error Server") })
	@GetMapping(value = "/searchMajor")
	public ResultRespon searchMajor(
			@Parameter(description = "Enter the keywords you want to search", required = false) @RequestParam("keyWord") String keyWord) {
		if (this.majorService.searchMajor(keyWord).isEmpty()) {
			throw new ResourceNotFoundException("Not found major by keyword " + keyWord);
		} else {
			System.out.println(this.majorService.searchMajor(keyWord).toString());
			return new ResultRespon(0, "Search Success", this.majorService.searchMajor(keyWord));
		}
	}
}