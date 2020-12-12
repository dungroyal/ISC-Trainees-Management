package com.ISC.project.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
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

@RestController
@RequestMapping("/api")
public class StudentIntakeController {
	@Autowired
	private StudentService studentService;
	@Autowired
	private IntakeService intakeService;
	@Autowired
	private IntakeStudentService intakeStudentService;

	//Get all 
	@GetMapping(value = "/allStudentIntake")
	public ResultRespon allStudentIntake() {
		return new ResultRespon(0, "Success", this.intakeStudentService.listAllCourseStudent());
	}

	//Get One
	@GetMapping(value = "/getIntakeOfStu") 
	public ResultRespon getIntakeOfStu(@RequestParam("studentId") Long studentId) {
		return new ResultRespon(0, "Success", this.intakeStudentService.listIntakeOfStu(studentId));
	}

	//Post IntakeStudent
	@PostMapping(value = "/postIntakeOfStu")
	public ResultRespon addIntakeOfStu(@RequestParam("studentId") Long studentId, @RequestParam("intakeId") Long [] intakeId) {
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
	@PutMapping(value = "/updateIntakeOfStu")
	public ResultRespon updateIntakeOfStu(@RequestParam("newIntakeId") List<Long> newIntakeId, @RequestParam("studentId") Long studentId
			, @RequestParam("intakeId") List<Long> intakeId) {
		this.intakeStudentService.updateIntakeOfStuArray(newIntakeId, studentId, intakeId);
		return new ResultRespon(0, "Update Intake Of Student Success", this.intakeStudentService.listIntakeOfStu(studentId));
	}

	//Update intake of student with array intakeId
	@PutMapping(value = "/updateIntakeOfStuArray")
	public ResultRespon updateIntakeOfStuArray(@RequestParam("studentId") Long studentId, @RequestParam("intakeId") Long [] intakeId,
			@RequestParam("newIntakeId") Long [] newIntakeId) {
		
		List<IntakeStudent> intakeStudents = new ArrayList<IntakeStudent>();
		
		//Tim stu 
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
//			return new ResultRespon(0, "Success", intakeStudents);
		}
//		intakeStudents.forEach(e -> System.out.println(e));
		return new ResultRespon(0, "Success", intakeStudents);
	}
	
	//Delete intake student
	@DeleteMapping(value = "/deleteIntakeOfStu") 
	public ResultRespon deleteIntakeOfStu(@RequestParam("studentId") Long studentId,
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
