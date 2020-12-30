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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.ISC.project.exception.ResourseNotFoundException;
import com.ISC.project.model.Lecturer;
import com.ISC.project.model.StatusAc;
import com.ISC.project.payload.ResultRespon;
import com.ISC.project.service.FileStorageService;
import com.ISC.project.service.LecturerService;
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

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping(value = "/api/lecturer")
@Tag(name = "Lecturer", description = "CRUD for Lecturer")
public class LecturerController {
	@Autowired
	private LecturerService lecturerService;
	@Autowired
	private FileStorageService fileStorageService;

	// get all lecturer
	// DOC for getAll lecturers
	@Operation(summary = "Get all lecturers", description = "Show all lecturers under the database")
	@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = Lecturer.class))), responseCode = "200", description = "Get all lecturers success")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "401", description = "Authorization Required"),
			@ApiResponse(responseCode = "403", description = "Forbidden"),
			@ApiResponse(responseCode = "500", description = "Internal Error Server") })
	@GetMapping(value = "/listLecturer")
	public ResultRespon listLecturer() {
		return new ResultRespon(0, "Success", this.lecturerService.listAllLecture());
	}

	// get one lecturer
	// DOC for getOne lecturer
	@Operation(summary = "Get one lecturer", description = "Show one lecturer under the database")
	@ApiResponse(responseCode = "200", description = "Get one lecturer success", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Lecturer.class)), mediaType = "application/json"))
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "401", description = "Authorization Required"),
			@ApiResponse(responseCode = "403", description = "Forbidden"),
			@ApiResponse(responseCode = "500", description = "Internal Error Server") })
	@GetMapping(value = "/getLecturer")
	public ResultRespon getLecturer(
			@Parameter(description = "The lecturer's id is required", required = true) @RequestParam("id") long id) {
		List<Lecturer> lecturer = new ArrayList<>();
		lecturer.add(
				lecturerService.findById(id).orElseThrow(() -> new ResourseNotFoundException("Not found lecturer")));
		return new ResultRespon(0, "Success", lecturer);
	}

	// post lecturer
	// DOC for add new lecturer
	@Operation(summary = "Add new lecturer", description = "Add new lecturer from the database")
	@ApiResponse(responseCode = "200", description = "Add lecturer success", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Lecturer.class)), mediaType = "multipart/form-data"))
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "401", description = "Authorization Required"),
			@ApiResponse(responseCode = "403", description = "Forbidden"),
			@ApiResponse(responseCode = "500", description = "Internal Error Server") })
	@PostMapping(value = "/newLecturer", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE}, produces = "application/json")
	public ResultRespon addLecturer(@RequestParam("createdBy") String createdBy,
			@RequestParam("updatedBy") String updatedBy,
			@Parameter(description = "Lecturer Code is required!", required = true) @RequestParam("codeLec") String codeLec,
			@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName,
			@RequestParam("addressLec") String addressLec, @RequestParam("phoneLec") String phoneLec,
			@Parameter(description = "Lecturer Email is required!", required = true) @RequestParam("emailLec") String emailLec,
			@RequestParam("degree") String degree,
			@Parameter(description = "Upload Image Lecturer", schema = @Schema(type = "file"), content = @Content(mediaType = "multipart/form-data"))
	@RequestParam("image") MultipartFile image,
	@RequestParam("statusLec") StatusAc statusLec, @RequestParam("noteLec") String noteLec)
			throws JsonMappingException, JsonProcessingException {

		// Save lecturer
		List<Lecturer> lecturers = new ArrayList<Lecturer>();

		// Getting URL image
		String fileName = this.fileStorageService.storeFile(image, codeLec);
		//		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/")
		//				.path(fileName).toUriString();

		// createDate = LocalDateTime.now()
		lecturers.add(new Lecturer(createdBy, updatedBy, codeLec, firstName, lastName, addressLec, phoneLec, emailLec,
				degree, fileName, statusLec, noteLec));
		lecturers.get(0).setCreatedDate(LocalDateTime.now());

		// check EXISTS of code Lecturer
		if (this.lecturerService.checkCodeLecturer(codeLec).isEmpty()
				&& this.lecturerService.checkEmailLecturer(emailLec).isEmpty()) {
			this.lecturerService.save(lecturers.get(0));
			return new ResultRespon(0, "Add Lecturer Success", lecturers);
		} else {
			if (!this.lecturerService.checkCodeLecturer(codeLec).isEmpty()) {
				throw new ResourseNotFoundException("Duplicate Lecturer Code");
			} else {
				throw new ResourseNotFoundException("Duplicate Lecturer Email");
			}
		}
	}

	// update lecturer
	// DOC for update Student
	@Operation(summary = "Update lecturer", description = "Update  lecturer with new image from the database")
	@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = Lecturer.class))), responseCode = "200", description = "Update lecturer success")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "401", description = "Authorization Required"),
			@ApiResponse(responseCode = "403", description = "Forbidden"),
			@ApiResponse(responseCode = "500", description = "Internal Error Server") })
	@PutMapping(value = "/editLecturerImg", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE}, produces = "application/json")
	public ResultRespon editLecturerNewImage(@RequestParam("id") Long id, @RequestParam("createdBy") String createdBy,
			@RequestParam("updatedBy") String updatedBy,
			@Parameter(description = "Lecturer Code is required!", required = true) @RequestParam("codeLec") String codeLec,
			@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName,
			@RequestParam("addressLec") String addressLec, @RequestParam("phoneLec") String phoneLec,
			@Parameter(description = "Student Email is required!", required = true) @RequestParam("emailLec") String emailLec,
			@RequestParam("degree") String degree,
			@Parameter(description = "Upload Image Lecturer", schema = @Schema(type = "file"), content = @Content(mediaType = "multipart/form-data")) @RequestParam("image") MultipartFile image,
			@RequestParam("statusLec") StatusAc statusLec, @RequestParam("noteLec") String noteLec)
					throws JsonMappingException, JsonProcessingException {

		Lecturer updateLecturer = this.lecturerService.findById(id)
				.orElseThrow(() -> new ResourseNotFoundException("Not Found Lecturer"));
		List<Lecturer> lecturers = new ArrayList<Lecturer>();

		// Getting URL image
		String fileName = this.fileStorageService.storeFile(image, codeLec);
		//		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/")
		//				.path(fileName).toUriString();

		updateLecturer.setCreatedBy(createdBy);
		updateLecturer.setUpdatedBy(updatedBy);
		updateLecturer.setCodeLec(codeLec);
		updateLecturer.setFirstName(firstName);
		updateLecturer.setLastName(lastName);
		updateLecturer.setAddressLec(addressLec);
		updateLecturer.setPhoneLec(phoneLec);
		updateLecturer.setEmailLec(emailLec);
		updateLecturer.setDegree(degree);
		updateLecturer.setImage(fileName);
		updateLecturer.setStatusLec(statusLec);
		updateLecturer.setNoteLec(noteLec);

		lecturers.add(updateLecturer);
		lecturers.get(0).setUpdatedDate(LocalDateTime.now());

		if (!this.lecturerService.checkCodeLecturer(codeLec).isEmpty()
				&& !this.lecturerService.checkEmailLecturer(emailLec).isEmpty()) {
			this.lecturerService.save(lecturers.get(0));
			return new ResultRespon(0, "Update Lecturer Success", lecturers);
		} else if (this.lecturerService.checkCodeLecturer(codeLec).isEmpty()
				&& !this.lecturerService.checkEmailLecturer(emailLec).isEmpty()) {
			if (!this.lecturerService.checkCodeLecturerUpdate(codeLec).contains(codeLec)) {
				this.lecturerService.save(lecturers.get(0));
				return new ResultRespon(0, "Update Lecturer Success", lecturers);
			}
			throw new ResourseNotFoundException("Duplicate Lecturer code");
		} else {
			for (int i = 0; i < this.lecturerService.checkEmailLecturer(emailLec).size(); i++) {
				if (emailLec.toUpperCase()
						.compareTo(this.lecturerService.checkEmailLecturer(emailLec).get(i).toUpperCase()) != 0) {
					this.lecturerService.save(lecturers.get(0));
					return new ResultRespon(0, "Update Lecturer Success", lecturers);
				}
			}
			throw new ResourseNotFoundException("Duplicate Lecturer email");
		}
	}

	// Update Lecturer NO Image
	// DOC for update Lecturer NO IMAGE
	@Operation(summary = "Update Lecturer", description = "Update new lecturer no image from the database")
	@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = Lecturer.class))), responseCode = "200", description = "Update lecturer success")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "401", description = "Authorization Required"),
			@ApiResponse(responseCode = "403", description = "Forbidden"),
			@ApiResponse(responseCode = "500", description = "Internal Error Server") })
	@PutMapping(value = "/editLecturerNotImg", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE}, produces = "application/json")
	public ResultRespon editLecturerNewImage(@RequestParam("id") Long id, @RequestParam("createdBy") String createdBy,
			@RequestParam("updatedBy") String updatedBy,
			@Parameter(description = "Lecturer Code is required!", required = true) @RequestParam("codeLec") String codeLec,
			@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName,
			@RequestParam("addressLec") String addressLec, @RequestParam("phoneLec") String phoneLec,
			@Parameter(description = "Student Email is required!", required = true) @RequestParam("emailLec") String emailLec,
			@RequestParam("degree") String degree, @RequestParam("statusLec") StatusAc statusLec,
			@RequestParam("noteLec") String noteLec) throws JsonMappingException, JsonProcessingException {
		Lecturer updateLecturer = this.lecturerService.findById(id)
				.orElseThrow(() -> new ResourseNotFoundException("Not Found Lecturer"));
		List<Lecturer> lecturers = new ArrayList<Lecturer>();

		updateLecturer.setCreatedBy(createdBy);
		updateLecturer.setUpdatedBy(updatedBy);
		updateLecturer.setCodeLec(codeLec);
		updateLecturer.setFirstName(firstName);
		updateLecturer.setLastName(lastName);
		updateLecturer.setAddressLec(addressLec);
		updateLecturer.setPhoneLec(phoneLec);
		updateLecturer.setEmailLec(emailLec);
		updateLecturer.setDegree(degree);
		updateLecturer.setImage(updateLecturer.getImage());
		updateLecturer.setStatusLec(statusLec);
		updateLecturer.setNoteLec(noteLec);

		lecturers.add(updateLecturer);
		lecturers.get(0).setUpdatedDate(LocalDateTime.now());
		System.out.println(this.lecturerService.checkCodeLecturerUpdate(codeLec));

		if (!this.lecturerService.checkCodeLecturer(codeLec).isEmpty()
				&& !this.lecturerService.checkEmailLecturer(emailLec).isEmpty()) {
			this.lecturerService.save(lecturers.get(0));
			return new ResultRespon(0, "Update Lecturer Success", lecturers);
		} else if (this.lecturerService.checkCodeLecturer(codeLec).isEmpty()
				&& !this.lecturerService.checkEmailLecturer(emailLec).isEmpty()) {
			if (!this.lecturerService.checkCodeLecturerUpdate(codeLec).contains(codeLec)) {
				this.lecturerService.save(lecturers.get(0));
				return new ResultRespon(0, "Update Lecturer Success", lecturers);
			}
			throw new ResourseNotFoundException("Duplicate Lecturer code");
		} else {
			for (int i = 0; i < this.lecturerService.checkEmailLecturer(emailLec).size(); i++) {
				if (emailLec.toUpperCase()
						.compareTo(this.lecturerService.checkEmailLecturer(emailLec).get(i).toUpperCase()) != 0) {
					this.lecturerService.save(lecturers.get(0));
					return new ResultRespon(0, "Update Lecturer Success", lecturers);
				}
			}
			throw new ResourseNotFoundException("Duplicate Lecturer email");
		}
	}

	// delete lecturer
	@Operation(summary = "Delete lecturer", description = "Delete from the database")
	@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = Lecturer.class))), responseCode = "200", description = "Delete lecturer success")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "401", description = "Authorization Required"),
			@ApiResponse(responseCode = "403", description = "Forbidden"),
			@ApiResponse(responseCode = "500", description = "Internal Error Server") })
	@DeleteMapping(value = "/deleteLecturer")
	public ResultRespon deleteLecturer(
			@Parameter(description = "The lecturer's id is required", required = true) @RequestParam("id") long id) {
		Lecturer lecturer = this.lecturerService.findById(id)
				.orElseThrow(() -> new ResourseNotFoundException("Not Found Lecturer"));
		try {
			this.lecturerService.delete(lecturer.getId());
		} catch (Exception e) {
			throw new ResourseNotFoundException("Lecturer can not be delete");
		}
		return new ResultRespon(0, "Delete Lecturer Success");
	}

	// Pagination
	@Operation(summary = "Pagination lecturer", description = "Pagination lecturer")
	@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = Lecturer.class))), responseCode = "200", description = "Success")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "401", description = "Authorization Required"),
			@ApiResponse(responseCode = "403", description = "Forbidden"),
			@ApiResponse(responseCode = "500", description = "Internal Error Server") })
	@GetMapping(value = "/pagination")
	public ResultRespon paginationLecturer(
			@Parameter(description = "Number of page", required = false) 
			@RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
			@Parameter(description = "Items in page", required = false) 
			@RequestParam(name = "size", required = false, defaultValue = "5") Integer size,
			@Parameter(description = "Sort by filed of Intems", required = false) 
			@RequestParam(name = "sort", required = false, defaultValue = "ASC") String sort,
			@Parameter(description = "Search Lecturer", required = false)
			@RequestParam(name = "search", defaultValue = "", required = false) String keyword) {
		Sort sortable = null;
		if (sort.equals("ASC")) {
			sortable = Sort.by("lastName").ascending();
		}
		if (sort.equals("DESC")) {
			sortable = Sort.by("lastName").descending();
		}
		List<Page<Lecturer>> lecturers = new ArrayList<Page<Lecturer>>();
		Pageable pageable = PageRequest.of(page, size, sortable);
		Pageable pageableSearch = PageRequest.of(page, size, sortable);
		if(!keyword.equals("")) {
			Page<Lecturer> lecturerSearch = lecturerService.searchLecturer(keyword, pageableSearch);
			lecturers.add(lecturerSearch);
		}else {
			Page<Lecturer> lecturer = lecturerService.findLecturer(pageable);	
			lecturers.add(lecturer);
		}
		return new ResultRespon(0, "Success", lecturers);
	}

	// Search lecturer by keyword
	//	@Operation(summary = "Search lecturer", description = "Search lecturer")
	//	@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = Lecturer.class))), responseCode = "200", description = "Success")
	//	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
	//			@ApiResponse(responseCode = "404", description = "Not found"),
	//			@ApiResponse(responseCode = "401", description = "Authorization Required"),
	//			@ApiResponse(responseCode = "403", description = "Forbidden"),
	//			@ApiResponse(responseCode = "500", description = "Internal Error Server") })
	//	@GetMapping(value = "/searchLecturer")
	//	public ResultRespon searchLecturer(
	//			@Parameter(description = "Enter the keywords you want to search", required = false) @RequestParam("keyWord") String keyWord) {
	//		if (this.lecturerService.searchLecturer(keyWord).isEmpty()) {
	//			throw new ResourceNotFoundException("Not found lecturer by keyword " + keyWord);
	//		} else {
	//			System.out.println(this.lecturerService.searchLecturer(keyWord).toString());
	//			return new ResultRespon(0, "Success", this.lecturerService.searchLecturer(keyWord));
	//		}
	//	}
}