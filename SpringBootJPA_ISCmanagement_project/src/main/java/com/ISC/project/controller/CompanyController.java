package com.ISC.project.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
import com.ISC.project.model.Student;
import com.ISC.project.model.University;
import com.ISC.project.payload.ResultRespon;
import com.ISC.project.service.CompanyService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin
@RestController
@RequestMapping("/api")
@Tag(name = "Company", description = "CRUD for Company")
public class CompanyController {
	@Autowired
	private CompanyService companyService;

	// get all company
	// Doc for getAll company
	@Operation(summary = "Get all companies", description = "Show all companies under the databse")
	@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = Company.class))), responseCode = "200", description = "Get all companies success")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "401", description = "Authorization Required"),
			@ApiResponse(responseCode = "403", description = "Forbidden"),
			@ApiResponse(responseCode = "500", description = "Internal Error Server") })
	@GetMapping("/listCompany")
	public ResultRespon listCompany() {
		return new ResultRespon(0, "Success", this.companyService.listAllCompany());
	}

	// get one company
	// DOC for getOne company
	@Operation(summary = "Get one companies", description = "Show one company under the database")
	@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = Company.class))), responseCode = "200", description = "Get one companies success")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "401", description = "Authorization Required"),
			@ApiResponse(responseCode = "403", description = "Forbidden"),
			@ApiResponse(responseCode = "500", description = "Internal Error Server") })
	@GetMapping("/getCompany")
	public ResultRespon getCompany(@RequestParam("id") long id) {
		List<Company> compa = new ArrayList<>();
		compa.add(companyService.findById(id)
				.orElseThrow(() -> new ResourseNotFoundException("not found company with id: " + id)));
		return new ResultRespon(0, "Success", compa);
	}

	// post company
	// DOC for add new company
	@Operation(summary = "Add new company", description = "Add new company from the database")
	@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = Company.class))), responseCode = "200", description = "Add companies success")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "401", description = "Authorization Required"),
			@ApiResponse(responseCode = "403", description = "Forbidden"),
			@ApiResponse(responseCode = "500", description = "Internal Error Server") })
	@PostMapping(value = "/newCompany")
	public ResultRespon addCompany(@RequestBody Company company) {
		List<Company> compa = new ArrayList<Company>();
		compa.add(company);
		compa.get(0).setCreatedDate(LocalDateTime.now());
		if (this.companyService.checkNameCom(company.getNameCom()).isEmpty()) {
			this.companyService.save(compa.get(0));
			return new ResultRespon(0, "Add company success", compa);
		} else {
			throw new ResourseNotFoundException("Duplicate Company Name");
		}
	}

	// update company
	// DOC for update company
	@Operation(summary = "Update company", description = "Update company from the database")
	@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = Company.class))), responseCode = "200", description = "Update company success")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "401", description = "Authorization Required"),
			@ApiResponse(responseCode = "403", description = "Forbidden"),
			@ApiResponse(responseCode = "500", description = "Internal Error Server") })
	@PutMapping(value = "/editCompany")
	public ResultRespon editCompany(@RequestBody Company company, @RequestParam("id") long id) {
		List<Company> compa = new ArrayList<Company>();
		Company oldcompany = companyService.findById(id)
				.orElseThrow(() -> new ResourseNotFoundException("not found company with id: " + id));

		if (this.companyService.getNameById(id).equals(company.getNameCom())) {
			oldcompany.setAddresCom(company.getAddresCom());
			oldcompany.setContactPerson(company.getContactPerson());
			oldcompany.setNoteCom(company.getNoteCom());
			oldcompany.setStatusCom(company.getStatusCom());
			oldcompany.setWebsiteCom(company.getWebsiteCom());
			oldcompany.setUpdatedBy(company.getUpdatedBy());
			oldcompany.setUpdatedDate(LocalDateTime.now());
			compa.add(oldcompany);
			this.companyService.save(compa.get(0));
			return new ResultRespon(0, "Update company success", compa);
		} else {
			if (this.companyService.checkNameCom(company.getNameCom()).isEmpty()) {
				oldcompany.setNameCom(company.getNameCom());
				oldcompany.setAddresCom(company.getAddresCom());
				oldcompany.setContactPerson(company.getContactPerson());
				oldcompany.setNoteCom(company.getNoteCom());
				oldcompany.setStatusCom(company.getStatusCom());
				oldcompany.setWebsiteCom(company.getWebsiteCom());
				oldcompany.setUpdatedBy(company.getUpdatedBy());
				oldcompany.setUpdatedDate(LocalDateTime.now());
				compa.add(oldcompany);
				this.companyService.save(compa.get(0));
				return new ResultRespon(0, "Update company success", compa);
			} else {
				throw new ResourseNotFoundException("Duplicate company name");
			}
		}

	}

	// delete company
	// DOC for delete company
	@Operation(summary = "Delete company", description = "Delete company from the database")
	@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = Company.class))), responseCode = "200", description = "Delete company success")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "401", description = "Authorization Required"),
			@ApiResponse(responseCode = "403", description = "Forbidden"),
			@ApiResponse(responseCode = "500", description = "Internal Error Server") })
	@DeleteMapping(value = "/deleteCompany")
	public ResultRespon deleteCompany(@RequestParam("id") Long id) {
		// companyService.findById(id).orElseThrow(() -> new
		// ResourseNotFoundException("not found company with id: "+ id));
		// if(this.companyService.findById(id).isEmpty()) {
		// throw new ResourseNotFoundException("Nothing to delete");
		// }else {
		// this.companyService.delete(id);
		// return new ResultRespon(0,"Delete company id = " + id + " success");
		// }
		Company company = this.companyService.findById(id)
				.orElseThrow(() -> new ResourseNotFoundException("Not Found Company"));
		try {
			this.companyService.delete(company.getId());
		} catch (Exception e) {
			throw new ResourseNotFoundException("Company can not be delete");
		}
		return new ResultRespon(0, "Delete company Success");
	}

	// search by name
	// DOC for search company
	@Operation(summary = "Search company", description = "Search company from the database")
	@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = Company.class))), responseCode = "200", description = "Search company success")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "401", description = "Authorization Required"),
			@ApiResponse(responseCode = "403", description = "Forbidden"),
			@ApiResponse(responseCode = "500", description = "Internal Error Server") })
	@GetMapping(value = "/searchCompany")
	public ResultRespon searchCompany(@RequestParam("keyWord") String keyWord) {
		if (this.companyService.searchCompany(keyWord).isEmpty()) {
			throw new ResourseNotFoundException("Not found company by keyword " + keyWord);
		} else {
			return new ResultRespon(0, "Success", this.companyService.searchCompany(keyWord));
		}
	}

	// Pagination
	// DOC for pagination company
	@Operation(summary = "pagination company", description = "pagination company from the database")
	@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = Company.class))), responseCode = "200", description = "pagination company success")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "404", description = "Not found"),
			@ApiResponse(responseCode = "401", description = "Authorization Required"),
			@ApiResponse(responseCode = "403", description = "Forbidden"),
			@ApiResponse(responseCode = "500", description = "Internal Error Server") })
	@GetMapping("/company/pagination")
	public ResultRespon paginationUniversity(
			@RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
			@RequestParam(name = "size", required = false, defaultValue = "1") Integer size,
			@RequestParam(name = "sort", required = false, defaultValue = "1") String sort) {
		Sort sortable = null;
		if (sort.equals("ASC")) {
			sortable = Sort.by("nameCom").ascending();
		}
		if (sort.equals("DESC")) {
			sortable = Sort.by("nameCom").descending();
		}
		Pageable pageable = PageRequest.of(page, size, sortable);
		Page<Company> compa = companyService.findCompa(pageable);
		List<Page<Company>> companies = new ArrayList<Page<Company>>();
		companies.add(compa);
		return new ResultRespon(0, "Success", companies);
	}
}
