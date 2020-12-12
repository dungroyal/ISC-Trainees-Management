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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ISC.project.exception.ResourseNotFoundException;
import com.ISC.project.model.StatusAc;
import com.ISC.project.model.Student;
import com.ISC.project.model.TypeStudent;
import com.ISC.project.model.University;
import com.ISC.project.payload.ResultRespon;
import com.ISC.project.service.FileStorageService;
import com.ISC.project.service.StudentService;
import com.ISC.project.service.UniversityService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

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
@Tag(name = "Student", description = "CRUD for Student")
public class StudentController {
	@Autowired
	private StudentService studentService;
	@Autowired
	private FileStorageService fileStorageService;
	@Autowired
	private UniversityService univerSerive;
	
	//Get All Student
	// DOC for getAll students
	@Operation(summary = "Get all Students", description = "Show all students under the database")
	@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = Student.class))),
	responseCode = "200", description = "Get all Students success")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "401", description = "Authorization Required"),
			@ApiResponse(responseCode = "403", description = "Forbidden"),
			@ApiResponse(responseCode = "500", description = "Internal Error Server")
	})
	@GetMapping(value = "/allStudent")
	public ResultRespon allStudent() {
		return new ResultRespon(0, "Success", this.studentService.listAllStudent());
	}
	
	//Get One Student
	// DOC for getOne  Student
	@Operation(summary = "Get one Students", description = "Show one student under the database")
	@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = Student.class))),
	responseCode = "200", description = "Get one Students success")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "401", description = "Authorization Required"),
			@ApiResponse(responseCode = "403", description = "Forbidden"),
			@ApiResponse(responseCode = "500", description = "Internal Error Server")
	})
	@GetMapping(value = "/oneStudent")
	public ResultRespon getStudent(
			// DOC for id of student
			@Parameter(description = "The student's id is required", required = true)
			@RequestParam("id") Long id) {
		List<Student> students = new ArrayList<>();
		students.add(this.studentService.findById(id).orElseThrow(() -> new ResourseNotFoundException("Not Found Student")));
		return new ResultRespon(0,"Success",students);
	}

	//Add new Student
	//DOC for add new Student
	@Operation(summary = "Add new Student", description = "Add new student from the database")
	@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = Student.class))),
	responseCode = "200", description = "Add Students success")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "401", description = "Authorization Required"),
			@ApiResponse(responseCode = "403", description = "Forbidden"),
			@ApiResponse(responseCode = "500", description = "Internal Error Server")
	})
	@PostMapping(value = "/newStudent", consumes = "multipart/form-data")
	public ResultRespon addStudent (
			@RequestParam("firstName") String firstName,
			@RequestParam("lastName") String lastName,
			@Parameter(description = "Student ID number is required!", required = true)
			@RequestParam("code") String code,
			@RequestParam("address") String address,
			@RequestParam("phoneNumber") String phoneNumber,
			@RequestParam("email") String email,
			@RequestParam("typeStudent") TypeStudent typeStudent,
			@Parameter(description = "GPA of the student during the course of study", required = true)
			@RequestParam("GPA") Double GPA,
			@RequestParam("workingStatus") StatusAc workingStatus,
			@RequestParam("note") String note,
			@RequestParam("image") MultipartFile image,
			@RequestParam("createdBy") String createdBy,
			@RequestParam("updatedBy") String updatedBy,
			@RequestParam("univerId") Long univerId) throws JsonMappingException, JsonProcessingException {

		//Get University
		University university = this.univerSerive.findById(univerId).orElseThrow(
				() -> new ResourseNotFoundException("Not found University"));
		//Save Student
		List<Student> students = new ArrayList<Student>();
		//Get URL image
		String fileName = this.fileStorageService.storeFile(image) + " " + code;
		String fileDowloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("/downloadFile/")
				.path(fileName)
				.toUriString();
		//		createdDate = LocalDateTime.now();
		students.add(new Student(createdBy, updatedBy,code, firstName, lastName, address, phoneNumber, 
				email, typeStudent, GPA, workingStatus, fileName, note, university));
		students.get(0).setCreatedDate(LocalDateTime.now());
		// Check EXISTS of code Student
		if(this.studentService.checkCodeStu(code).isEmpty() && this.studentService.checkEmailStu(email).isEmpty()) {
			this.studentService.save(students.get(0));
			return new ResultRespon(0, "Add Student Success", students);
		} else {
			if(!this.studentService.checkCodeStu(code).isEmpty()) {
				throw new ResourseNotFoundException("Duplicate Student Code");
			} else {
				throw new ResourseNotFoundException("Duplicate Email Student");
			}
		}
	}

	//Update Student with new Image
	@PutMapping(value = "/updateStudentImg", consumes = "multipart/form-data")
	public ResultRespon upateStudentNewImage (
			@RequestParam("id") Long id,
			@RequestParam("firstName") String firstName,
			@RequestParam("lastName") String lastName,
			@RequestParam("code") String code,
			@RequestParam("address") String address,
			@RequestParam("phoneNumber") String phoneNumber,
			@RequestParam("email") String email,
			@RequestParam("typeStudent") TypeStudent typeStudent,
			@RequestParam("GPA") Double GPA,
			@RequestParam("workingStatus") StatusAc workingStatus,
			@RequestParam("note") String note,
			@RequestParam("image") MultipartFile image,
			@RequestParam("createdBy") String createdBy,
			@RequestParam("updatedBy") String updatedBy,
			@RequestParam("univerId") Long univerId) throws JsonMappingException, JsonProcessingException {

		//Get University
		University university = this.univerSerive.findById(univerId).orElseThrow(
				() -> new ResourseNotFoundException("Not found University"));
		//Get Student
		Student updateStudent = this.studentService.findById(id).orElseThrow(
				() -> new ResourseNotFoundException("Not Found Student"));

		//Save Student
		List<Student> students = new ArrayList<Student>();
		//Get URL image
		String fileName = this.fileStorageService.storeFile(image) + " " + code;
		String fileDowloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("/downloadFile/")
				.path(fileName)
				.toUriString();
		//Update Student
		updateStudent.setFirstName(firstName);
		updateStudent.setLastName(lastName);
		updateStudent.setCodeStu(code);
		updateStudent.setAddressStu(address);
		updateStudent.setPhoneStu(phoneNumber);
		updateStudent.setEmailStu(email);
		updateStudent.setTypeStu(typeStudent);
		updateStudent.setGpa(GPA);
		updateStudent.setWorkingStatus(workingStatus);
		updateStudent.setNoteStu(note);
		updateStudent.setImage(fileName);
		updateStudent.setUniversity(university);
		updateStudent.setCreatedBy(updateStudent.getCreatedBy());
		updateStudent.setUpdatedBy(updatedBy);
		updateStudent.setCreatedDate(updateStudent.getCreatedDate());

		students.add(updateStudent);
		students.get(0).setUpdatedDate(LocalDateTime.now());
		// Check EXISTS of code Student
//		System.out.println(this.studentService.checkCodeStuUpdate(code));

		if(!this.studentService.checkCodeStu(code).isEmpty() && !this.studentService.checkEmailStu(email).isEmpty()) {
			this.studentService.save(students.get(0));
			return new ResultRespon(0, "Update Student Success", students);
		} else if(this.studentService.checkCodeStu(code).isEmpty() && !this.studentService.checkEmailStu(email).isEmpty()) {
			if (!this.studentService.checkCodeStuUpdate(code).contains(code)) {
				this.studentService.save(students.get(0));
				return new ResultRespon(0, "Update Student Success", students);
			}
			throw new ResourseNotFoundException("Duplicate Student Code 1");
		} else {
			for(int i = 0; i < this.studentService.checkEmailStuUpdate(email).size(); i++) {
				if(email.toUpperCase().compareTo(this.studentService.checkEmailStuUpdate(email).get(i).toUpperCase()) != 0) {
					this.studentService.save(students.get(0));
					return new ResultRespon(0, "Update Student Success", students);
				}
			}
			throw new ResourseNotFoundException("Duplicate Student Email 1");
		}
	}

	//Update Student NO Image
	@PutMapping(value = "/updateStudentNotImg")
	public ResultRespon upateStudentNewImage (
			@RequestParam("id") Long id,
			@RequestParam("firstName") String firstName,
			@RequestParam("lastName") String lastName,
			@RequestParam("code") String code,
			@RequestParam("address") String address,
			@RequestParam("phoneNumber") String phoneNumber,
			@RequestParam("email") String email,
			@RequestParam("typeStudent") TypeStudent typeStudent,
			@RequestParam("GPA") Double GPA,
			@RequestParam("workingStatus") StatusAc workingStatus,
			@RequestParam("note") String note,
			@RequestParam("createdBy") String createdBy,
			@RequestParam("updatedBy") String updatedBy,
			@RequestParam("univerId") Long univerId) throws JsonMappingException, JsonProcessingException {

		//Get University
		University university = this.univerSerive.findById(univerId).orElseThrow(
				() -> new ResourseNotFoundException("Not found University"));
		//Get Student
		Student updateStudent = this.studentService.findById(id).orElseThrow(
				() -> new ResourseNotFoundException("Not Found Student"));

		//Save Student
		List<Student> students = new ArrayList<Student>();

		//Update Student
		updateStudent.setFirstName(firstName);
		updateStudent.setLastName(lastName);
		updateStudent.setCodeStu(code);
		updateStudent.setAddressStu(address);
		updateStudent.setPhoneStu(phoneNumber);
		updateStudent.setEmailStu(email);
		updateStudent.setTypeStu(typeStudent);
		updateStudent.setGpa(GPA);
		updateStudent.setWorkingStatus(workingStatus);
		updateStudent.setNoteStu(note);
		updateStudent.setImage(updateStudent.getImage());
		updateStudent.setUniversity(university);
		updateStudent.setCreatedBy(updateStudent.getCreatedBy());
		updateStudent.setUpdatedBy(updatedBy);
		updateStudent.setCreatedDate(updateStudent.getCreatedDate());

		students.add(updateStudent);
		students.get(0).setUpdatedDate(LocalDateTime.now());
		// Check EXISTS of code Student
		System.out.println(this.studentService.checkCodeStuUpdate(code));

		if(!this.studentService.checkCodeStu(code).isEmpty() && !this.studentService.checkEmailStu(email).isEmpty()) {
			this.studentService.save(students.get(0));
			return new ResultRespon(0, "Update Student Success", students);
		} else if(this.studentService.checkCodeStu(code).isEmpty() && !this.studentService.checkEmailStu(email).isEmpty()) {
			if (!this.studentService.checkCodeStuUpdate(code).contains(code)) {
				this.studentService.save(students.get(0));
				return new ResultRespon(0, "Update Student Success", students);
			}
			throw new ResourseNotFoundException("Duplicate Student Code 1");
		} else {
			for(int i = 0; i < this.studentService.checkEmailStuUpdate(email).size(); i++) {
				if(email.toUpperCase().compareTo(this.studentService.checkEmailStuUpdate(email).get(i).toUpperCase()) != 0) {
					this.studentService.save(students.get(0));
					return new ResultRespon(0, "Update Student Success", students);
				}
			}
			throw new ResourseNotFoundException("Duplicate Student Email 1");
		}
	}
	
	//Delete Student
	@DeleteMapping("/deleteStudent")
	public ResultRespon deleteStudent(@RequestParam("id") Long id) {
		Student student = this.studentService.findById(id)
				.orElseThrow(() -> new ResourseNotFoundException("Not Found Student"));
		try {
			this.studentService.deleteById(student.getId());
		}catch (Exception e) {
			throw new ResourseNotFoundException("Student can not be delete");
		}
		return new ResultRespon(0, "Delete Student Success");
	}
	//Pagination
	@GetMapping( value = "/student/pagination")
	public ResultRespon paginationStudent(@RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
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
		Page<Student> stu = studentService.findStudent(pageable);
		List<Page<Student>> students = new ArrayList<>();
		students.add(stu);
		return new ResultRespon(0, "Success", students) ;
	}
	
	//Search Student
	@GetMapping(value = "/searchStudent")
	public ResultRespon searchStudent(@RequestParam("keyWord") String keyWord) {
		if(this.studentService.searchStudent(keyWord).isEmpty()) {
			throw new ResourseNotFoundException("Not Found Student");
		} else {
			
			System.out.println(this.studentService.searchStudent(keyWord).toString());
			return new ResultRespon(0, "Search Success", this.studentService.searchStudent(keyWord));
		}
	}
}
