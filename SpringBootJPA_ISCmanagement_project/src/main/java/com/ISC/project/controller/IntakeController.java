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
import org.springframework.http.MediaType;
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
import com.ISC.project.model.Intake;
import com.ISC.project.model.Major;
import com.ISC.project.payload.ResultRespon;
import com.ISC.project.service.IntakeService;
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
@RequestMapping("/api/intake")
@Tag(name = "Intake", description = "CRUD for intake")
public class IntakeController {
	@Autowired
	private IntakeService intakeService;
	@Autowired
	private MajorService majorService;
	// get all intake
	// Doc for getAll intake
	@Operation(summary = "Get all intakes", description = "Show all intakes under the databse")
	@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = Intake.class))), responseCode = "200", description = "Get all intakes success")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "401", description = "Authorization Required"),
			@ApiResponse(responseCode = "403", description = "Forbidden"),
			@ApiResponse(responseCode = "500", description = "Internal Error Server") })
	@GetMapping(value = "/listIntake")
	public ResultRespon listIntake() {
		return new ResultRespon(0, "Success", this.intakeService.listAllCourse());
	}

	// get one intake
	// DOC for getOne intake
	@Operation(summary = "Get one intake", description = "Show one intake under the database")
	@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = Intake.class))), responseCode = "200", description = "Get one intake success")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "401", description = "Authorization Required"),
			@ApiResponse(responseCode = "403", description = "Forbidden"),
			@ApiResponse(responseCode = "500", description = "Internal Error Server") })
	@GetMapping(value = "/getIntake")
	public ResultRespon getIntake(
			@Parameter(description = "The intake's id is required", required = true) 
			@RequestParam("id") long id) {
		List<Intake> intake = new ArrayList<>();
		intake.add(intakeService.findById(id).orElseThrow(() -> new ResourseNotFoundException("Not found intake")));
		return new ResultRespon(0, "Success", intake);
	}

	// post intake
	// DOC for add new intake
	@Operation(summary = "Add new intake", description = "Add new intake from the database")
	@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = Intake.class))), responseCode = "200", description = "Add intake success")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "401", description = "Authorization Required"),
			@ApiResponse(responseCode = "403", description = "Forbidden"),
			@ApiResponse(responseCode = "500", description = "Internal Error Server") })
	@PostMapping(value = "/newIntake" , consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = "application/json")
	public ResultRespon addIntake(
			@Parameter(required = true)
			@RequestBody Intake intake,
			@Parameter(required = true, description = "Major ID")
			@RequestParam("majorId") Long majorId) {
		Major major = this.majorService.findById(majorId)
				.orElseThrow(() -> new ResourceNotFoundException("Not Found Major"));
		List<Intake> intakeList = new ArrayList<>();
		intake.setCreatedDate(LocalDateTime.now());
		intake.setMajor(major);
		if (this.intakeService.checkCodeIntake(intake.getCodeIntake()).isEmpty()) {
			intakeList.add(intakeService.save(intake));
			return new ResultRespon(0, "Add Intake Success", intakeList);
		} else {
			throw new ResourseNotFoundException("Duplicate intake Code");
		}
	}

	// update intake
	// DOC for update intake
	@Operation(summary = "Update intake", description = "Update intake from the database")
	@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = Intake.class))), responseCode = "200", description = "Update intake success")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "401", description = "Authorization Required"),
			@ApiResponse(responseCode = "403", description = "Forbidden"),
			@ApiResponse(responseCode = "500", description = "Internal Error Server") })
	@PutMapping(value = "/editIntake", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = "application/json")
	public ResultRespon editIntake(
			@Parameter(required = true)
			@RequestBody Intake intake, 
			@Parameter(required = true, description = "Intake ID")
			@RequestParam("id") long id, 
			@Parameter(required = true, description = "Major ID")
			@RequestParam("majorId") Long majorId) {
		Major major = this.majorService.findById(majorId)
				.orElseThrow(() -> new ResourceNotFoundException("Not Found Major"));
		List<Intake> intakeList = new ArrayList<>();
		Intake oldIntake = intakeService.findById(id)
				.orElseThrow(() -> new ResourseNotFoundException("Not found intake"));
		if (this.intakeService.checkCodeIntake(intake.getCodeIntake()).isEmpty()) {
			oldIntake.setUpdatedBy(intake.getUpdatedBy());
			oldIntake.setUpdatedDate(LocalDateTime.now());
			oldIntake.setCodeIntake(intake.getCodeIntake());
			oldIntake.setNameIntake(intake.getNameIntake());
			oldIntake.setStartDay(intake.getStartDay());
			oldIntake.setEndDay(intake.getEndDay());
			oldIntake.setStatusIntake(intake.getStatusIntake());
			oldIntake.setMajor(major);
			intakeList.add(oldIntake);
			this.intakeService.save(intakeList.get(0));
			return new ResultRespon(0, "Update Intake Success", intakeList);
		} else {
			if (!this.intakeService.checkCodeIntake(intake.getCodeIntake()).isEmpty()) {
				oldIntake.setCodeIntake(intake.getCodeIntake());
				oldIntake.setUpdatedBy(intake.getUpdatedBy());
				oldIntake.setUpdatedDate(LocalDateTime.now());
				oldIntake.setCodeIntake(intake.getCodeIntake());
				oldIntake.setNameIntake(intake.getNameIntake());
				oldIntake.setStartDay(intake.getStartDay());
				oldIntake.setEndDay(intake.getEndDay());
				oldIntake.setStatusIntake(intake.getStatusIntake());
				oldIntake.setMajor(major);
				intakeList.add(oldIntake);
				this.intakeService.save(intakeList.get(0));
				return new ResultRespon(0, "Update Intake Success", intakeList);
			} else {
				throw new ResourseNotFoundException("Duplicate intake Code");
			}
		}
	}

	// delete intake
	@Operation(summary = "Delete intake", description = "Delete intake from the database")
	@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = Intake.class))), responseCode = "200", description = "Delete intake success")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "401", description = "Authorization Required"),
			@ApiResponse(responseCode = "403", description = "Forbidden"),
			@ApiResponse(responseCode = "500", description = "Internal Error Server") })
	@DeleteMapping( value = "/deleteIntake", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = "application/json")
	public ResultRespon deleteIntake(
			@Parameter(description = "The intake's id is required", required = true) @RequestParam("id") long id) {
		Intake intake = this.intakeService.findById(id)
				.orElseThrow(() -> new ResourseNotFoundException("Not Found Intake"));
		try {
			this.intakeService.delete(intake.getId());
		} catch (Exception e) {
			throw new ResourseNotFoundException("Intake can not be delete");
		}
		return new ResultRespon(0, "Delete Intake Success");
	}

	// Pagination
	@Operation(summary = "Pagination intake", description = "Pagination intake")
	@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = Intake.class))), responseCode = "200", description = "Success")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "401", description = "Authorization Required"),
			@ApiResponse(responseCode = "403", description = "Forbidden"),
			@ApiResponse(responseCode = "500", description = "Internal Error Server") })
	@GetMapping(value = "/pagination")
	public ResultRespon paginationIntake(
			@Parameter(description = "Number of page", required = false) 
			@RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
			@Parameter(description = "Items in page", required = false) 
			@RequestParam(name = "size", required = false, defaultValue = "1") Integer size,
			@Parameter(description = "Sort by filed of Items", required = false)
			@RequestParam(name = "sort", required = false, defaultValue = "ASC") String sort,
			@Parameter(description = "Search Subject", required = false)
			@RequestParam(name = "search", required = false, defaultValue = "") String keywrod) {
		Sort sortable = null;
		if (sort.equals("ASC")) {
			sortable = Sort.by("nameIntake").ascending();
		}
		if (sort.equals("DESC")) {
			sortable = Sort.by("nameIntake").descending();
		}
		List<Page<Intake>> intakes = new ArrayList<Page<Intake>>();
		Pageable pageable = PageRequest.of(page, size, sortable);
		Pageable pageableSearch = PageRequest.of(page, size, sortable);
		if(!keywrod.equals("")) {
			Page<Intake> intakeSearch = intakeService.searchIntake(keywrod, pageableSearch);
			intakes.add(intakeSearch);
		}else {
			Page<Intake> intake = intakeService.findIntake(pageable);
			intakes.add(intake);
		}
		return new ResultRespon(0, "Success", intakes);
	}

	// Search intake by keyword
//	@Operation(summary = "Search intake", description = "Search intake")
//	@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = Intake.class))), responseCode = "200", description = "Success")
//	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
//			@ApiResponse(responseCode = "404", description = "Not found"),
//			@ApiResponse(responseCode = "401", description = "Authorization Required"),
//			@ApiResponse(responseCode = "403", description = "Forbidden"),
//			@ApiResponse(responseCode = "500", description = "Internal Error Server") })
//	@GetMapping(value = "/searchIntake")
//	public ResultRespon searchIntake(
//			@Parameter(description = "Enter the keywords you want to search", required = false) @RequestParam("keyWord") String keyWord) {
//		if (this.intakeService.searchIntake(keyWord).isEmpty()) {
//			throw new ResourceNotFoundException("Not found intake by keyword " + keyWord);
//		} else {
//			System.out.println(this.intakeService.searchIntake(keyWord).toString());
//			return new ResultRespon(0, "Search Success", this.intakeService.searchIntake(keyWord));
//		}
//	}
}