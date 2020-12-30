package com.ISC.project.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ISC.project.exception.ResourseNotFoundException;
import com.ISC.project.model.EmbemdedIntakeSubjectId;
import com.ISC.project.model.Intake;
import com.ISC.project.model.IntakeSubject;
import com.ISC.project.payload.ResultRespon;
import com.ISC.project.service.IntakeService;
import com.ISC.project.service.IntakeSubjectService;
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
@RequestMapping("/api/intake-subject")
@Tag(name = "IntakeSubject", description = "CRUD for Intake at Subject")
public class IntakeSubjectController {
	@Autowired
	private IntakeSubjectService intakeSubjectService;
	@Autowired
	private IntakeService intakeService;
	@Autowired
	private SubjectService subjectService;
	
	// Get all Intake at Subject
	// DOC for get all Room
	@Operation(summary = "Get all Intake at Subject", description = "Get all Intake at Subject")
	@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = IntakeSubject.class))),
	responseCode = "200", description = "Get all Intake at Subject success")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "401", description = "Authorization Required"),
			@ApiResponse(responseCode = "403", description = "Forbidden"),
			@ApiResponse(responseCode = "500", description = "Internal Error Server")
	})
	@GetMapping(value = "/listIntakeSub")
	public ResultRespon listIntakeSubject() {
		return new ResultRespon(0,"Success",this.intakeSubjectService.listAllmajorSubject());
	}
	
	// Get one Intake at Subject
	// DOC for get one Intake at Subject
	@Operation(summary = "Get one Intake at Subject witd intake ID", description = "Get one Intake at Subject witd intake ID")
	@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = IntakeSubject.class))),
	responseCode = "200", description = "Get one Intake at Subject success")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "401", description = "Authorization Required"),
			@ApiResponse(responseCode = "403", description = "Forbidden"),
			@ApiResponse(responseCode = "500", description = "Internal Error Server")
	})
	@GetMapping(value = "/getIntakeSub")
	public ResultRespon getIntakeSubject(@Parameter(description = "Intake ID is required!", required = true) @RequestParam("intakeId") Long intakeId) {
		if(intakeSubjectService.getSubjectOfIntake(intakeId).isEmpty()) {
			throw new ResourseNotFoundException("Not found");
		}else {
			return new ResultRespon(0,"Success",this.intakeSubjectService.getSubjectOfIntake(intakeId));
		}
	}
	
	// Add Intake at Subject
	// DOC for add Intake at Subject
	@Operation(summary = "Add Intake at Subject", description = "Add Intake at Subject")
	@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = IntakeSubject.class))),
	responseCode = "200", description = "Add Intake at Subject success")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "401", description = "Authorization Required"),
			@ApiResponse(responseCode = "403", description = "Forbidden"),
			@ApiResponse(responseCode = "500", description = "Internal Error Server")
	})
	@PostMapping(value = "/postIntakeSub", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = "application/json")
	public ResultRespon addIntakeSubject(@Parameter(description = "Intake ID is required!", required = true) @RequestParam("intakeId") Long intakeId,@Parameter(description = "Subject ID is required!", required = true) @RequestParam("subjectId") Long [] subjectId) {
		List<IntakeSubject> intakeSubjects = new ArrayList<IntakeSubject>();
		Intake intake = this.intakeService.findById(intakeId).orElseThrow(()-> new ResourseNotFoundException("Not found intake"));
		for(int i=0; i< subjectId.length; i++) {
			IntakeSubject intakeSubject = new IntakeSubject();
			intakeSubject.setIntake(intake);
			intakeSubject.setSubject(this.subjectService.findById(subjectId[i])
					.orElseThrow(()-> new ResourseNotFoundException("Not found subject id")));
			intakeSubjects.add(this.intakeSubjectService.save(intakeSubject));
		}
		return new ResultRespon(0, "Add Subject of Intake success", intakeSubjects);
	}
	
	// Update Intake at Subject with IntakeID and SubjectID
	// DOC for add Intake at Subject
	@Operation(summary = "Update Intake at Subject", description = "Update Intake at Subject with IntakeID and SubjectID")
	@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = IntakeSubject.class))),
	responseCode = "200", description = "Update Intake at Subject success")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "401", description = "Authorization Required"),
			@ApiResponse(responseCode = "403", description = "Forbidden"),
			@ApiResponse(responseCode = "500", description = "Internal Error Server")
	})
	@PutMapping(value = "/editIntakeSub", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = "application/json")
	public ResultRespon updateIntakeSubject(
			@Parameter(description = "Intake ID is required!", required = true) @RequestParam("intakeId") Long intakeId, 
			@Parameter(description = "Subject ID is required!", required = true) @RequestParam("subjectId") Long [] subjectId, 
			@Parameter(description = "New Subject ID is required!", required = true) @RequestParam("newSubjectId") Long [] newSubjectId) {
		List<IntakeSubject> intakeSubjects = new ArrayList<IntakeSubject>();
		
		Intake intake = this.intakeService.findById(intakeId).orElseThrow(()-> new ResourseNotFoundException("Not found intake"));
		
		for(int i=0; i< subjectId.length; i++) {
			this.intakeSubjectService.delete(new EmbemdedIntakeSubjectId(intakeId, subjectId[i]));
		}
		
		for(int j=0; j <= newSubjectId.length - 1; j++) {
			IntakeSubject intakeSubject = new IntakeSubject();
			intakeSubject.setIntake(intake);
			intakeSubject.setSubject(this.subjectService.findById(newSubjectId[j]).orElseThrow(()-> new ResourseNotFoundException("Not found subject")));
			intakeSubjects.add(this.intakeSubjectService.save(intakeSubject));
		}
		return new ResultRespon(0, "Update Subject of Intake success", intakeSubjects);
	}
	
	// Delete Intake at Subject with IntakeID and SubjectID
	// DOC for add Intake at Subject
	@Operation(summary = "Delete Intake at Subject", description = "Delete Intake at Subject with IntakeID and SubjectID")
	@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = IntakeSubject.class))),
	responseCode = "200", description = "Delete Intake at Subject success")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "401", description = "Authorization Required"),
			@ApiResponse(responseCode = "403", description = "Forbidden"),
			@ApiResponse(responseCode = "500", description = "Internal Error Server")
	})
	@DeleteMapping(value = "/deleteIntakeSub")
	public ResultRespon deleteIntakeSubject(
			@Parameter(description = "Intake ID is required!", required = true) @RequestParam("intakeId") Long intakeId,
			@Parameter(description = "Subject ID is required!", required = true) @RequestParam("subjectId") Long subjectId) {
		this.intakeSubjectService.findById(new EmbemdedIntakeSubjectId(intakeId, subjectId))
		.orElseThrow(()-> new ResourseNotFoundException("Not found IntakeSubject"));
		try {
			this.intakeSubjectService.delete(new EmbemdedIntakeSubjectId(intakeId, subjectId));
			return new ResultRespon(0,"Delete IntakeSubject success");
		} catch (Exception e) {
			throw new ResourseNotFoundException("IntakeSubject can not be delete!");
		}
	}
}