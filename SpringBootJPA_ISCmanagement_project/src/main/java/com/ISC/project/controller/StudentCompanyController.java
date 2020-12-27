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
import com.ISC.project.model.EmbemdedStudentCompanyId;
import com.ISC.project.model.Student;
import com.ISC.project.model.StudentCompany;
import com.ISC.project.payload.ResultRespon;
import com.ISC.project.service.CompanyService;
import com.ISC.project.service.StudentCompanyService;
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
@RequestMapping("/api/student-company")
@Tag(name = "StudentCompany", description = "CRUD for Student at Company")
public class StudentCompanyController {
	@Autowired
	private StudentCompanyService studentCompanyService;
	@Autowired
	private StudentService studentService;
	@Autowired
	private CompanyService companyService;

	// Get all Student at Company
	// DOC for get all Room
	@Operation(summary = "Get all Student at Company", description = "Get all Student at Company")
	@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = StudentCompany.class))),
	responseCode = "200", description = "Get all Student at Company success")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "401", description = "Authorization Required"),
			@ApiResponse(responseCode = "403", description = "Forbidden"),
			@ApiResponse(responseCode = "500", description = "Internal Error Server")
	})
	@GetMapping(value = "/listStudentCompany", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = "application/json")
	public ResultRespon listStudentCompany() {
		return new ResultRespon(0,"Success",this.studentCompanyService.listAllStudentCompany());
	}

	// Get one Student at Company
	// DOC for get one Student at Company
	@Operation(summary = "Get one Student at Company witd student ID", description = "Get one Student at Company witd student ID")
	@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = StudentCompany.class))),
	responseCode = "200", description = "Get one Student at Company success")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "401", description = "Authorization Required"),
			@ApiResponse(responseCode = "403", description = "Forbidden"),
			@ApiResponse(responseCode = "500", description = "Internal Error Server")
	})
	@GetMapping(value = "/getStudentCompany", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = "application/json")
	public ResultRespon getStudentCompany(@Parameter(description = "Student ID is required!", required = true) @RequestParam("studentId") Long studentId) {
		if(studentCompanyService.getStudentCompany(studentId).isEmpty()) {
			throw new ResourseNotFoundException("Not found");
		}else {
			return new ResultRespon(0,"Success",this.studentCompanyService.getStudentCompany(studentId));
		}
	}

	// Add Student at Company
	// DOC for add Student at Company
	@Operation(summary = "Add Student at Company", description = "Add Student at Company")
	@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = StudentCompany.class))),
	responseCode = "200", description = "Add Student at Company success")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "401", description = "Authorization Required"),
			@ApiResponse(responseCode = "403", description = "Forbidden"),
			@ApiResponse(responseCode = "500", description = "Internal Error Server")
	})
	@PostMapping(value = "/addStudentCompany", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = "application/json")
	public ResultRespon addStudentCompany(@Parameter(description = "Student ID is required!", required = true) @RequestParam("studentId") Long studentId,@Parameter(description = "Company ID is required!", required = true) @RequestParam("companyId") Long [] companyId) {
		List<StudentCompany> studentCompanies = new ArrayList<StudentCompany>();
		Student student = this.studentService.findById(studentId).orElseThrow(()-> new ResourseNotFoundException("Not found student"));
		for(int i=0; i< companyId.length; i++) {
			StudentCompany studentCompany = new StudentCompany();
			studentCompany.setStudent(student);
			studentCompany.setCompany(this.companyService.findById(companyId[i])
					.orElseThrow(()-> new ResourseNotFoundException("Not found company id")));
			studentCompanies.add(this.studentCompanyService.save(studentCompany));
		}
		return new ResultRespon(0, "Add Company of Student success", studentCompanies);
	}

	// Update Student at Company with StudentID and CompanyID
	// DOC for add Student at Company
	@Operation(summary = "Update Student at Company", description = "Update Student at Company with StudentID and CompanyID")
	@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = StudentCompany.class))),
	responseCode = "200", description = "Update Student at Company success")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "401", description = "Authorization Required"),
			@ApiResponse(responseCode = "403", description = "Forbidden"),
			@ApiResponse(responseCode = "500", description = "Internal Error Server")
	})
	@PutMapping(value = "/updateStudentCompany", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = "application/json")
	public ResultRespon updateStudentCompany(
			@Parameter(description = "Student ID is required!", required = true) @RequestParam("studentId") Long studentId, 
			@Parameter(description = "Company ID is required!", required = true) @RequestParam("companyId") Long [] companyId, 
			@Parameter(description = "New Company ID is required!", required = true) @RequestParam("newCompanyId") Long [] newCompanyId) {
		List<StudentCompany> studentCompanies = new ArrayList<StudentCompany>();

		Student student = this.studentService.findById(studentId).orElseThrow(()-> new ResourseNotFoundException("Not found student"));

		for(int i=0; i< companyId.length; i++) {
			this.studentCompanyService.delete(new EmbemdedStudentCompanyId(studentId, companyId[i]));
		}

		for(int j=0; j <= newCompanyId.length - 1; j++) {
			StudentCompany studentCompany = new StudentCompany();
			studentCompany.setStudent(student);
			studentCompany.setCompany(this.companyService.findById(newCompanyId[j]).orElseThrow(()-> new ResourseNotFoundException("Not found company")));
			studentCompanies.add(this.studentCompanyService.save(studentCompany));
		}
		return new ResultRespon(0, "Update Company of Student success", studentCompanies);
	}

	// Delete Student at Company with StudentID and CompanyID
	// DOC for add Student at Company
	@Operation(summary = "Delete Student at Company", description = "Delete Student at Company with StudentID and CompanyID")
	@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = StudentCompany.class))),
	responseCode = "200", description = "Delete Student at Company success")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "401", description = "Authorization Required"),
			@ApiResponse(responseCode = "403", description = "Forbidden"),
			@ApiResponse(responseCode = "500", description = "Internal Error Server")
	})
	@DeleteMapping(value = "/deleteStudentCompany", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = "application/json")
	public ResultRespon deleteStudentCompany(
			@Parameter(description = "Student ID is required!", required = true) @RequestParam("studentId") Long studentId,
			@Parameter(description = "Company ID is required!", required = true) @RequestParam("companyId") Long companyId) {
		this.studentCompanyService.findById(new EmbemdedStudentCompanyId(studentId, companyId))
		.orElseThrow(()-> new ResourseNotFoundException("Not found StudentCompany"));
		try {
			this.studentCompanyService.delete(new EmbemdedStudentCompanyId(studentId, companyId));
			return new ResultRespon(0,"Delete StudentCompany success");
		} catch (Exception e) {
			throw new ResourseNotFoundException("StudentCompany can not be delete!");
		}
	}
}
