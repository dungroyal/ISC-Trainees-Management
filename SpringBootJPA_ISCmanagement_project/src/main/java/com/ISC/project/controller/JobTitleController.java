package com.ISC.project.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
import com.ISC.project.model.Company;
import com.ISC.project.model.JobTitle;
import com.ISC.project.payload.ResultRespon;
import com.ISC.project.service.JobTitleService;

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
@RequestMapping("/api/jobTitle")
@Tag(name = "JobTitle", description = "CRUD for JobTitle")
public class JobTitleController {
	@Autowired
	private JobTitleService jobTitleService;
	
	
	// get all jobTitle
			// Doc for getAll jobTitle
			@Operation(summary = "Get all jobTitles", description = "Show all jobTitles under the databse")
			@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = JobTitle.class))), responseCode = "200", description = "Get all jobTitles success")
			@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
					@ApiResponse(responseCode = "404", description = "Not found"),
					@ApiResponse(responseCode = "401", description = "Authorization Required"),
					@ApiResponse(responseCode = "403", description = "Forbidden"),
					@ApiResponse(responseCode = "500", description = "Internal Error Server") })
			@GetMapping(value = "/listJobTitle")
			public ResultRespon listjobTitle() {
				return new ResultRespon(0, "Success", this.jobTitleService.listAllJobTitle());
			}
			// get one jobTitle
			// DOC for getOne jobTitle
			@Operation(summary = "Get one jobTitles", description = "Show one jobTitle under the database")
			@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = JobTitle.class))), responseCode = "200", description = "Get one jobTitle success")
			@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
					@ApiResponse(responseCode = "404", description = "Not found"),
					@ApiResponse(responseCode = "401", description = "Authorization Required"),
					@ApiResponse(responseCode = "403", description = "Forbidden"),
					@ApiResponse(responseCode = "500", description = "Internal Error Server") })
			@GetMapping(value = "/getJobTitle")
			public ResultRespon getjobTitle(
					@Parameter(required = true, description = "jobTitle Id")
					@RequestParam("id") long id) {
				List<JobTitle> job = new ArrayList<>();
				job.add(jobTitleService.findById(id)
						.orElseThrow(() -> new ResourseNotFoundException("not found jobTitle with id: " + id)));
				return new ResultRespon(0, "Success", job);
			}
			
			// post jobTitle
			// DOC for add new jobTitle
			@Operation(summary = "Add new jobTitle", description = "Add new jobTitle from the database")
			@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = JobTitle.class))), responseCode = "200", description = "Add jobTitles success")
			@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
					@ApiResponse(responseCode = "404", description = "Not found"),
					@ApiResponse(responseCode = "401", description = "Authorization Required"),
					@ApiResponse(responseCode = "403", description = "Forbidden"),
					@ApiResponse(responseCode = "500", description = "Internal Error Server") })
			@PostMapping(value = "/newJobTitle", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = "application/json")
			public ResultRespon addjobTitle(
					@Parameter(required = true)
					@RequestBody JobTitle jobTitle) {
				List<JobTitle> job = new ArrayList<JobTitle>();
				job.add(jobTitle);
				job.get(0).setCreatedDate(LocalDateTime.now());
				if (this.jobTitleService.checkName(jobTitle.getNameJob()).isEmpty()) {
					this.jobTitleService.save(job.get(0));
					return new ResultRespon(0, "Add jobTitle success", job);
				} else {
					throw new ResourseNotFoundException("Duplicate jobTitle Name");
				}
			}
			
			// update jobTitle
			// DOC for update jobTitle
			@Operation(summary = "Update jobTitle", description = "Update jobTitle from the database")
			@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = JobTitle.class))), responseCode = "200", description = "Update jobTitle success")
			@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
					@ApiResponse(responseCode = "404", description = "Not found"),
					@ApiResponse(responseCode = "401", description = "Authorization Required"),
					@ApiResponse(responseCode = "403", description = "Forbidden"),
					@ApiResponse(responseCode = "500", description = "Internal Error Server") })
			@PutMapping(value = "/editJobTitle", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = "application/json")
			public ResultRespon editjobTitle(
					@Parameter(required = true)
					@RequestBody JobTitle jobTitle,
					@Parameter(required = true, description = "jobTitle ID")
					@RequestParam("id") long id) {
				List<JobTitle> job = new ArrayList<JobTitle>();
				JobTitle oldjobTitle = jobTitleService.findById(id)
						.orElseThrow(() -> new ResourseNotFoundException("not found jobTitle with id: " + id));

				if (this.jobTitleService.getNameById(id).equals(jobTitle.getNameJob())) {
					oldjobTitle.setJobStatus(jobTitle.getJobStatus());
					oldjobTitle.setCreatedBy(jobTitle.getCreatedBy());
					oldjobTitle.setUpdatedBy(jobTitle.getUpdatedBy());
					oldjobTitle.setUpdatedDate(LocalDateTime.now());
					job.add(oldjobTitle);
					this.jobTitleService.save(job.get(0));
					return new ResultRespon(0, "Update jobTitle success", job);
				} else {
					if (this.jobTitleService.checkName(jobTitle.getNameJob()).isEmpty()) {
						oldjobTitle.setNameJob(jobTitle.getNameJob());
						oldjobTitle.setJobStatus(jobTitle.getJobStatus());
						oldjobTitle.setCreatedBy(jobTitle.getCreatedBy());
						oldjobTitle.setUpdatedBy(jobTitle.getUpdatedBy());
						oldjobTitle.setUpdatedDate(LocalDateTime.now());
						job.add(oldjobTitle);
						this.jobTitleService.save(job.get(0));
						return new ResultRespon(0, "Update jobTitle success", job);
					} else {
						throw new ResourseNotFoundException("Duplicate jobTitle name");
					}
				}
			}
			
			// delete jobTitle
			// DOC for delete jobTitle
			@Operation(summary = "Delete jobTitle", description = "Delete jobTitle from the database")
			@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = JobTitle.class))), responseCode = "200", description = "Delete jobTitle success")
			@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
					@ApiResponse(responseCode = "404", description = "Not found"),
					@ApiResponse(responseCode = "401", description = "Authorization Required"),
					@ApiResponse(responseCode = "403", description = "Forbidden"),
					@ApiResponse(responseCode = "500", description = "Internal Error Server") })
			@DeleteMapping(value = "/deleteJobTitle")
			public ResultRespon deletejobTitle(@RequestParam("id") Long id) {
				JobTitle jobTitle = this.jobTitleService.findById(id)
						.orElseThrow(() -> new ResourseNotFoundException("Not Found job title"));
				try {
					this.jobTitleService.delete(jobTitle.getId());
				} catch (Exception e) {
					throw new ResourseNotFoundException("job title can not be delete");
				}
				return new ResultRespon(0, "Delete jobTitle Success");
			}
			
			// search by name
			// DOC for search jobTitle
//			@Operation(summary = "Search jobTitle", description = "Search jobTitle from the database")
//			@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = JobTitle.class))), responseCode = "200", description = "Search jobTitle success")
//			@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
//					@ApiResponse(responseCode = "404", description = "Not found"),
//					@ApiResponse(responseCode = "401", description = "Authorization Required"),
//					@ApiResponse(responseCode = "403", description = "Forbidden"),
//					@ApiResponse(responseCode = "500", description = "Internal Error Server") })
//			@GetMapping(value = "/searchJobTitle")
//			public ResultRespon searchjobTitle(@RequestParam("keyWord") String keyWord) {
//				if (this.jobTitleService.searchJob(keyWord).isEmpty()) {
//					throw new ResourseNotFoundException("Not found jobTitle by keyword " + keyWord);
//				} else {
//					return new ResultRespon(0, "Success", this.jobTitleService.searchJob(keyWord));
//				}
//			}
			
			// Pagination
			// DOC for pagination jobTitle
			@Operation(summary = "pagination jobTitle", description = "pagination jobTitle from the database")
			@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = JobTitle.class))), responseCode = "200", description = "pagination jobTitle success")
			@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
					@ApiResponse(responseCode = "404", description = "Not found"),
					@ApiResponse(responseCode = "401", description = "Authorization Required"),
					@ApiResponse(responseCode = "403", description = "Forbidden"),
					@ApiResponse(responseCode = "500", description = "Internal Error Server") })
			@GetMapping(value = "/pagination")
			public ResultRespon paginationjobTitle(
					@RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
					@RequestParam(name = "size", required = false, defaultValue = "1") Integer size,
					@RequestParam(name = "sort", required = false, defaultValue = "1") String sort,
					@RequestParam(name = "key", required = false, defaultValue = "") String key) {
				Sort sortable = null;
				if (sort.equals("ASC")) {
					sortable = Sort.by("nameJob").ascending();
				}
				if (sort.equals("DESC")) {
					sortable = Sort.by("nameJob").descending();
				}
				Pageable pageableSearch = PageRequest.of(page, size, sortable);
				Pageable pageable = PageRequest.of(page, size, sortable);
				List<Page<JobTitle>> jobTitles = new ArrayList<Page<JobTitle>>();
				if(!key.equals("")) {
					Page<JobTitle> jobSea = jobTitleService.searchJob(key, pageableSearch);
					jobTitles.add(jobSea);
				}else {
					Page<JobTitle> job = jobTitleService.findJob(pageable);
					jobTitles.add(job);
				}
				
				return new ResultRespon(0, "Success", jobTitles);
			}
}
