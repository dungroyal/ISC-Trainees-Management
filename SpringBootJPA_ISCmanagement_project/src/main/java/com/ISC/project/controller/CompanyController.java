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
import com.ISC.project.model.University;
import com.ISC.project.payload.ResultRespon;
import com.ISC.project.service.CompanyService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class CompanyController {
	@Autowired
	private CompanyService companyService;
	
	//get all company
	@GetMapping("/listCompany")
	public ResultRespon listCompany(){
		return new ResultRespon(0,"Success",this.companyService.listAllCompany());
	}
	
	//get one company
	@GetMapping("/getCompany") 
	public ResultRespon getCompany(@RequestParam("id") long id) {
		List<Company> compa = new ArrayList<>();
		compa.add(companyService.findById(id).orElseThrow(() -> new ResourseNotFoundException("not found company with id: " + id)));
		if(compa.isEmpty()) {
			throw new ResourseNotFoundException("Not found company by id " + id);
		}else {
			return new ResultRespon(0,"Success",compa);
		}
		
	}
	
	//post company
	@PostMapping(value = "/newCompany")
	public ResultRespon addCompany(@RequestBody Company company){
		List<Company> compa = new ArrayList<Company>();
		compa.add(company);
		compa.get(0).setCreatedDate(LocalDateTime.now());
		if(this.companyService.checkNameCom(company.getNameCom()).isEmpty()) {
			this.companyService.save(compa.get(0));
			return new ResultRespon(0,"Add company success",compa);
		}else {
			throw new ResourseNotFoundException("Duplicate Company Name");
		}
	}
	
	//update company
	@PutMapping(value="/editCompany")
	public ResultRespon editCompany(@RequestBody Company company,@RequestParam("id") long id) {
		List<Company> compa = new ArrayList<Company>();
		Company oldcompany = companyService.findById(id).orElseThrow(() -> new ResourseNotFoundException("not found company with id: "+ id));
		if(this.companyService.checkNameCom(company.getNameCom()).isEmpty()){
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
			return new ResultRespon(0,"Update company success",compa);
		}else {
			throw new ResourseNotFoundException("Duplicate company name");
		}
		
	}
	
	//delete company
	@DeleteMapping(value="/deleteCompany")
	public ResultRespon deleteCompany(@RequestParam("id") long id) {
		companyService.findById(id).orElseThrow(() -> new ResourseNotFoundException("not found company with id: "+ id));
		if(this.companyService.findById(id).isEmpty()) {
			throw new ResourseNotFoundException("Nothing to delete");
		}else {
			this.companyService.delete(id);
			return new ResultRespon(0,"Delete company id = " + id + " success");
		}
	}
	
	//search by name
	@GetMapping(value="/searchCompany")
	public ResultRespon searchCompany(@RequestParam("keyWord") String keyWord) {
		if(this.companyService.searchCompany(keyWord).isEmpty()) {
			throw new ResourseNotFoundException("Not found company by keyword " + keyWord);
		}else {
			return new ResultRespon(0,"Success",this.companyService.searchCompany(keyWord));
		}
	}
	
	//Pagination
		@GetMapping("/company/pagination")
		public ResultRespon paginationUniversity(@RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
				@RequestParam(name = "size",required = false,defaultValue = "1") Integer size,
				@RequestParam(name = "sort",required = false,defaultValue = "1") String sort) {
			Sort sortable = null;
			if(sort.equals("ASC")) {
				sortable = Sort.by("nameCom").ascending();
			}
			if(sort.equals("DESC")) {
				sortable = Sort.by("nameCom").descending();
			}
			Pageable pageable = PageRequest.of(page, size,sortable);
			Page<Company> compa = companyService.findCompa(pageable);
			List<Page<Company>> companies = new ArrayList<Page<Company>>();
			companies.add(compa);
			return new ResultRespon(0,"Success",companies);
		}
}
