package com.ISC.project.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ISC.project.model.EmbemdedIntakeStudentId;
import com.ISC.project.model.IntakeStudent;
import com.ISC.project.model.Student;
import com.ISC.project.payload.ResultRespon;
import com.ISC.project.service.IntakeService;
import com.ISC.project.service.IntakeStudentService;
import com.ISC.project.service.StudentService;

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
@RequestMapping("/api/student-intake")
@Tag(name = "Student_Intake", description = "CRUD for Student_Intake")
public class StudentIntakeController {
	@Autowired
	private StudentService studentService;
	@Autowired
	private IntakeService intakeService;
	@Autowired
	private IntakeStudentService intakeStudentService;

	//Get all 
	//Get All Id Student and Id Intake
	@Operation(summary = "Get All Id Student and Id Intake", description = "Show all Id Student and Id Intake under the database")
	@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = IntakeStudent.class))),
	responseCode = "200", description = "Get All Id Student and Id Intake success")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "401", description = "Authorization Required"),
			@ApiResponse(responseCode = "403", description = "Forbidden"),
			@ApiResponse(responseCode = "500", description = "Internal Error Server")
	})
	@GetMapping(value = "/allStudentIntake")
	public ResultRespon allStudentIntake() {
		return new ResultRespon(0, "Success", this.intakeStudentService.listAllCourseStudent());
	}

	//Get List ID Intake By Id Student
	//Get List ID Intake By Id Student DOC
	@Operation(summary = "Get List ID Intake By Id Student", description = "Get List ID Intake By Id Student under the database")
	@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = IntakeStudent.class))),
	responseCode = "200", description = "Get List ID Intake By Id Student success")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "401", description = "Authorization Required"),
			@ApiResponse(responseCode = "403", description = "Forbidden"),
			@ApiResponse(responseCode = "500", description = "Internal Error Server")
	})
	@GetMapping(value = "/getIntakeOfStu") 
	public ResultRespon getIntakeOfStu(
			@Parameter(description = ("Id student"), required = true)
			@RequestParam("studentId") Long studentId) {
		return new ResultRespon(0, "Success", this.intakeStudentService.listIntakeOfStu(studentId));
	}
	
	
	//Post IntakeStudent
	//Post ID Intake By Id Student DOC
	@Operation(summary = "Post ID Intake By Id Student DOC", description = "Post ID Intake By Id Student DOC from the database")
	@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = IntakeStudent.class))),
	responseCode = "200", description = "Success")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "401", description = "Authorization Required"),
			@ApiResponse(responseCode = "403", description = "Forbidden"),
			@ApiResponse(responseCode = "500", description = "Internal Error Server")
	})
	@PostMapping(value = "/postIntakeOfStu", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = "application/json")
	public ResultRespon addIntakeOfStu(
			@Parameter(description = ("Id student"), required = true)
			@RequestParam("studentId") Long studentId, 
			@Parameter(description = ("Array intake id"), required = true)
			@RequestParam("intakeId") Long [] intakeId) {
		List<IntakeStudent> intakeStudents = new ArrayList<IntakeStudent>();
		Student student = this.studentService.findById(studentId)
				.orElseThrow(() -> new ResourceNotFoundException("Not found Student id"));
		for(int i = 0; i < intakeId.length; i++) {
			IntakeStudent intakeStudent = new IntakeStudent();
			intakeStudent.setStudent(student);
			intakeStudent.setIntake(this.intakeService.findById(intakeId[i])
					.orElseThrow(() -> new ResourceNotFoundException("Not found intake id")));
			intakeStudents.add(this.intakeStudentService.save(intakeStudent));
		}
		return new ResultRespon(0, "Add Intake Of Student Success");
	}

	//Update intake of student
	//Put ID Intake By Id Student DOC
	@Operation(summary = "Put ID Intake By Id Student DOC", description = "Put ID Intake By Id Student DOC from the database")
	@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = IntakeStudent.class))),
	responseCode = "200", description = "Success")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "401", description = "Authorization Required"),
			@ApiResponse(responseCode = "403", description = "Forbidden"),
			@ApiResponse(responseCode = "500", description = "Internal Error Server")
	})
	@PutMapping(value = "/updateIntakeOfStu", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = "application/json")
	public ResultRespon updateIntakeOfStu(
			@Parameter(description = ("List new Intake ID"), required = true)
			@RequestParam("newIntakeId") List<Long> newIntakeId, 
			@Parameter(description = ("Student ID"), required = true)
			@RequestParam("studentId") Long studentId, 
			@Parameter(description = ("List Old Intake ID"), required = true)
			@RequestParam("intakeId") List<Long> intakeId) {
		this.intakeStudentService.updateIntakeOfStuArray(newIntakeId, studentId, intakeId);
		return new ResultRespon(0, "Update Intake Of Student Success", this.intakeStudentService.listIntakeOfStu(studentId));
	}

	//Update intake of student with array intakeId
	//Put ID Intake By Id Student DOC
	@Operation(summary = "Put ID Intake By Id Student DOC", description = "Put ID Intake By Id Student DOC from the database")
	@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = IntakeStudent.class))),
	responseCode = "200", description = "Success")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "401", description = "Authorization Required"),
			@ApiResponse(responseCode = "403", description = "Forbidden"),
			@ApiResponse(responseCode = "500", description = "Internal Error Server")
	})
	@PutMapping(value = "/updateIntakeOfStuArray", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = "application/json")
	public ResultRespon updateIntakeOfStuArray(
			@Parameter(description = ("Student ID"), required = true)
			@RequestParam("studentId") Long studentId,
			@Parameter(description = ("List Old Intake ID"), required = true)
			@RequestParam("intakeId") Long [] intakeId,
			@Parameter(description = ("List new Intake ID"), required = true)
			@RequestParam("newIntakeId") Long [] newIntakeId) {
		List<IntakeStudent> intakeStudents = new ArrayList<IntakeStudent>();
		 
		Student student = this.studentService.findById(studentId)
				.orElseThrow(() -> new ResourceNotFoundException("Not found student id"));
		
		System.out.println(student.toString());
		
		for(int i = 0; i < intakeId.length; i++) {
			System.out.println(i);
			this.intakeStudentService.deleteById(new EmbemdedIntakeStudentId(intakeId[i], studentId));
		}

		for(int j = 0; j <= newIntakeId.length - 1; j++) {
			System.out.println(j);
			IntakeStudent intakeStudent = new IntakeStudent();
			intakeStudent.setStudent(student);
			intakeStudent.setIntake(this.intakeService.findById(newIntakeId[j]).orElseThrow(() -> new ResourceNotFoundException("Not found Intake Id")));
			intakeStudents.add(this.intakeStudentService.save(intakeStudent));
		}
		return new ResultRespon(0, "Success", intakeStudents);
	}
	
	//Delete intake student
	//Delete ID Intake By Id Student DOC
	@Operation(summary = "Delete ID Intake By Id Student DOC", description = "Delete ID Intake By Id Student DOC from the database")
	@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = IntakeStudent.class))),
	responseCode = "200", description = "Success")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "401", description = "Authorization Required"),
			@ApiResponse(responseCode = "403", description = "Forbidden"),
			@ApiResponse(responseCode = "500", description = "Internal Error Server")
	})
	@DeleteMapping(value = "/deleteIntakeOfStu") 
	public ResultRespon deleteIntakeOfStu(
			@Parameter(description = ("Student ID"), required = true)
			@RequestParam("studentId") Long studentId,
			@Parameter(description = ("Intake ID"), required = true)
			@RequestParam("intakeId") Long intakeId) {
		this.intakeStudentService.findById(new EmbemdedIntakeStudentId(intakeId, studentId))
		.orElseThrow(() -> new ResourceNotFoundException("Not found IntakeStudent"));
		try {
			this.intakeStudentService.delete(new EmbemdedIntakeStudentId(intakeId, studentId));
			return new ResultRespon(0, "Delete Intake Of Student Success");
		} catch (Exception e) {
			throw new ResourceNotFoundException("IntakeStudent can not be delete!");
		}
	}
}
