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
import com.ISC.project.model.Intake;

import com.ISC.project.payload.ResultRespon;
import com.ISC.project.service.IntakeService;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class IntakeController {
	@Autowired
	private IntakeService intakeService;

	// get all intake
	@GetMapping("/listIntake")
	public ResultRespon listIntake() {
		return new ResultRespon(0, "Success", this.intakeService.listAllIntake());
	}

	// get one intake
	@GetMapping("/getIntake")
	public ResultRespon getIntake(@RequestParam("id") long id) {
		List<Intake> intake = new ArrayList<>();
		intake.add(intakeService.findById(id)
				.orElseThrow(() -> new ResourseNotFoundException("not found intake with id: " + id)));
		return new ResultRespon(0, "Success", intake);
	}

	// post intake
	@PostMapping("/newIntake")
	public ResultRespon addIntake(@RequestBody Intake intake) {
		List<Intake> intakeList = new ArrayList<>();
		intake.setCreatedDate(LocalDateTime.now());
		if (this.intakeService.checkCodeIntake(intake.getCodeIntake()).isEmpty()) {
			intakeList.add(intakeService.save(intake));
			return new ResultRespon(0, "Success", intakeList);
		} else {
			throw new ResourseNotFoundException("Duplicate intake Code");
		}
	}

	// update intake
	@PutMapping("/editIntake")
	public ResultRespon editIntake(@RequestBody Intake intake, @RequestParam("id") long id) {
		List<Intake> intakeList = new ArrayList<>();
		Intake oldIntake = intakeService.findById(id)
				.orElseThrow(() -> new ResourseNotFoundException("not found intake with id: " + id));
		if (this.intakeService.checkCodeIntake(intake.getCodeIntake()).isEmpty()) {
			oldIntake.setUpdatedBy(intake.getUpdatedBy());
			oldIntake.setUpdatedDate(LocalDateTime.now());
			oldIntake.setCodeIntake(intake.getCodeIntake());
			oldIntake.setNameIntake(intake.getNameIntake());
			oldIntake.setStartDay(intake.getStartDay());
			oldIntake.setEndDay(intake.getEndDay());
			oldIntake.setStatusIntake(intake.getStatusIntake());
			intakeList.add(oldIntake);
			this.intakeService.save(intakeList.get(0));
			return new ResultRespon(0, "Success", intakeList);
		} else {
			throw new ResourseNotFoundException("Duplicate intake code");
		}
	}

	// delete intake
	@DeleteMapping("/deleteIntake")
	public ResultRespon deleteIntake(@RequestParam("id") long id) {
		intakeService.findById(id).orElseThrow(() -> new ResourseNotFoundException("not found intake with id: " + id));
		this.intakeService.delete(id);
		return new ResultRespon(0, "Success");
	}

	// Pagination
	@GetMapping(value = "/intake/pagination")
	public ResultRespon paginationIntake(
			@RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
			@RequestParam(name = "size", required = false, defaultValue = "1") Integer size,
			@RequestParam(name = "sort", required = false, defaultValue = "ASC") String sort) {
		Sort sortable = null;
		if (sort.equals("ASC")) {
			sortable = Sort.by("nameIntake").ascending();
		}
		if (sort.equals("DESC")) {
			sortable = Sort.by("nameIntake").descending();
		}
		Pageable pageable = PageRequest.of(page, size, sortable);
		Page<Intake> intake = intakeService.findIntake(pageable);
		List<Page<Intake>> intakes = new ArrayList<Page<Intake>>();
		intakes.add(intake);
		return new ResultRespon(0, "Success", intakes);
	}

	// Search intake by keyword
	@GetMapping(value = "/searchIntake")
	public ResultRespon searchIntake(@RequestParam("keyWord") String keyWord) {
		if (this.intakeService.searchIntake(keyWord).isEmpty()) {
			throw new ResourceNotFoundException("Not found intake by keyword " + keyWord);
		} else {
			return new ResultRespon(0, "Success", this.intakeService.searchIntake(keyWord));
		}
	}
}
