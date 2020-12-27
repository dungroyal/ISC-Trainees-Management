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
import com.ISC.project.model.Clazz;
import com.ISC.project.payload.ResultRespon;
import com.ISC.project.service.ClazzService;

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
@RequestMapping("/api/clazz")
@Tag(name = "Clazz", description = "CRUD for Clazz")
public class ClazzController {
	@Autowired
	private ClazzService clazzService;
	
	// get all clazz
		// Doc for getAll clazz
		@Operation(summary = "Get all clazzs", description = "Show all clazzs under the databse")
		@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = Clazz.class))), responseCode = "200", description = "Get all clazzs success")
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
				@ApiResponse(responseCode = "404", description = "Not found"),
				@ApiResponse(responseCode = "401", description = "Authorization Required"),
				@ApiResponse(responseCode = "403", description = "Forbidden"),
				@ApiResponse(responseCode = "500", description = "Internal Error Server") })
		@GetMapping(value = "/listClazz")
		public ResultRespon listClazz() {
			return new ResultRespon(0, "Success", this.clazzService.listAllClazz());
		}
		// get one clazz
		// DOC for getOne clazz
		@Operation(summary = "Get one clazzs", description = "Show one clazz under the database")
		@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = Clazz.class))), responseCode = "200", description = "Get one clazz success")
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
				@ApiResponse(responseCode = "404", description = "Not found"),
				@ApiResponse(responseCode = "401", description = "Authorization Required"),
				@ApiResponse(responseCode = "403", description = "Forbidden"),
				@ApiResponse(responseCode = "500", description = "Internal Error Server") })
		@GetMapping(value = "/getClazz")
		public ResultRespon getClazz(
				@Parameter(required = true, description = "Clazz Id")
				@RequestParam("id") long id) {
			List<Clazz> clz = new ArrayList<>();
			clz.add(clazzService.findById(id)
					.orElseThrow(() -> new ResourseNotFoundException("not found clazz with id: " + id)));
			return new ResultRespon(0, "Success", clz);
		}
		
		// post clazz
		// DOC for add new clazz
		@Operation(summary = "Add new clazz", description = "Add new clazz from the database")
		@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = Clazz.class))), responseCode = "200", description = "Add clazzs success")
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
				@ApiResponse(responseCode = "404", description = "Not found"),
				@ApiResponse(responseCode = "401", description = "Authorization Required"),
				@ApiResponse(responseCode = "403", description = "Forbidden"),
				@ApiResponse(responseCode = "500", description = "Internal Error Server") })
		@PostMapping(value = "/newClazz", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = "application/json")
		public ResultRespon addClazz(
				@Parameter(required = true)
				@RequestBody Clazz clazz) {
			List<Clazz> clz = new ArrayList<Clazz>();
			clz.add(clazz);
			clz.get(0).setCreatedDate(LocalDateTime.now());
			if (this.clazzService.checkName(clazz.getNameClazz()).isEmpty()) {
				this.clazzService.save(clz.get(0));
				return new ResultRespon(0, "Add clazz success", clz);
			} else {
				throw new ResourseNotFoundException("Duplicate Clazz Name");
			}
		}
		
		// update clazz
		// DOC for update clazz
		@Operation(summary = "Update clazz", description = "Update clazz from the database")
		@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = Clazz.class))), responseCode = "200", description = "Update clazz success")
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
				@ApiResponse(responseCode = "404", description = "Not found"),
				@ApiResponse(responseCode = "401", description = "Authorization Required"),
				@ApiResponse(responseCode = "403", description = "Forbidden"),
				@ApiResponse(responseCode = "500", description = "Internal Error Server") })
		@PutMapping(value = "/editClazz", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = "application/json")
		public ResultRespon editClazz(
				@Parameter(required = true)
				@RequestBody Clazz clazz,
				@Parameter(required = true, description = "Clazz ID")
				@RequestParam("id") long id) {
			List<Clazz> clz = new ArrayList<Clazz>();
			Clazz oldclazz = clazzService.findById(id)
					.orElseThrow(() -> new ResourseNotFoundException("not found clazz with id: " + id));

			if (this.clazzService.getNameById(id).equals(clazz.getNameClazz())) {
				oldclazz.setNumOfStu(clazz.getNumOfStu());
				oldclazz.setPointGra(clazz.getPointGra());
				oldclazz.setCreatedBy(clazz.getCreatedBy());
				oldclazz.setUpdatedBy(clazz.getUpdatedBy());
				oldclazz.setUpdatedDate(LocalDateTime.now());
				clz.add(oldclazz);
				this.clazzService.save(clz.get(0));
				return new ResultRespon(0, "Update clazz success", clz);
			} else {
				if (this.clazzService.checkName(clazz.getNameClazz()).isEmpty()) {
					oldclazz.setNameClazz(clazz.getNameClazz());
					oldclazz.setNumOfStu(clazz.getNumOfStu());
					oldclazz.setPointGra(clazz.getPointGra());
					oldclazz.setCreatedBy(clazz.getCreatedBy());
					oldclazz.setUpdatedBy(clazz.getUpdatedBy());
					oldclazz.setUpdatedDate(LocalDateTime.now());
					clz.add(oldclazz);
					this.clazzService.save(clz.get(0));
					return new ResultRespon(0, "Update clazz success", clz);
				} else {
					throw new ResourseNotFoundException("Duplicate clazz name");
				}
			}
		}
		
		// delete clazz
		// DOC for delete clazz
		@Operation(summary = "Delete clazz", description = "Delete clazz from the database")
		@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = Clazz.class))), responseCode = "200", description = "Delete clazz success")
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
				@ApiResponse(responseCode = "404", description = "Not found"),
				@ApiResponse(responseCode = "401", description = "Authorization Required"),
				@ApiResponse(responseCode = "403", description = "Forbidden"),
				@ApiResponse(responseCode = "500", description = "Internal Error Server") })
		@DeleteMapping(value = "/deleteClazz")
		public ResultRespon deleteclazz(@RequestParam("id") Long id) {
			Clazz clazz = this.clazzService.findById(id)
					.orElseThrow(() -> new ResourseNotFoundException("Not Found clazz"));
			try {
				this.clazzService.delete(clazz.getId());
			} catch (Exception e) {
				throw new ResourseNotFoundException("clazz can not be delete");
			}
			return new ResultRespon(0, "Delete clazz Success");
		}
		
		// search by name
		// DOC for search clazz
		@Operation(summary = "Search clazz", description = "Search clazz from the database")
		@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = Clazz.class))), responseCode = "200", description = "Search clazz success")
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
				@ApiResponse(responseCode = "404", description = "Not found"),
				@ApiResponse(responseCode = "401", description = "Authorization Required"),
				@ApiResponse(responseCode = "403", description = "Forbidden"),
				@ApiResponse(responseCode = "500", description = "Internal Error Server") })
		@GetMapping(value = "/searchClazz")
		public ResultRespon searchClazz(@RequestParam("keyWord") String keyWord) {
			if (this.clazzService.searchClz(keyWord).isEmpty()) {
				throw new ResourseNotFoundException("Not found clazz by keyword " + keyWord);
			} else {
				return new ResultRespon(0, "Success", this.clazzService.searchClz(keyWord));
			}
		}
		
		// Pagination
		// DOC for pagination clazz
		@Operation(summary = "pagination clazz", description = "pagination clazz from the database")
		@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = Clazz.class))), responseCode = "200", description = "pagination clazz success")
		@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
				@ApiResponse(responseCode = "404", description = "Not found"),
				@ApiResponse(responseCode = "401", description = "Authorization Required"),
				@ApiResponse(responseCode = "403", description = "Forbidden"),
				@ApiResponse(responseCode = "500", description = "Internal Error Server") })
		@GetMapping(value = "/pagination", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = "application/json")
		public ResultRespon paginationClazz(
				@RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
				@RequestParam(name = "size", required = false, defaultValue = "1") Integer size,
				@RequestParam(name = "sort", required = false, defaultValue = "1") String sort) {
			Sort sortable = null;
			if (sort.equals("ASC")) {
				sortable = Sort.by("name").ascending();
			}
			if (sort.equals("DESC")) {
				sortable = Sort.by("name").descending();
			}
			Pageable pageable = PageRequest.of(page, size, sortable);
			Page<Clazz> clz = clazzService.findClz(pageable);
			List<Page<Clazz>> clazzs = new ArrayList<Page<Clazz>>();
			clazzs.add(clz);
			return new ResultRespon(0, "Success", clazzs);
		}
}
