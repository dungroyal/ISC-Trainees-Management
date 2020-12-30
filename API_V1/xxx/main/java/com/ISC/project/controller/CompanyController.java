package com.ISC.project.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.ISC.project.service.CompanyService;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class CompanyController {
	@Autowired
	private CompanyService companyService;
	
	//get all company
	@GetMapping("/listCompany")
	public List<Company> listCompany(){
		return companyService.listAllCompany();
	}
	
	//post company
	@PostMapping("/newCompany")
	public Company addCompany(@RequestBody Company company) {
		company.setCreatedDate(LocalDateTime.now());
		return companyService.save(company);
	}
	
	//update company
	@PutMapping("/editCompany")
	public Company editCompany(@RequestBody Company company,@RequestParam("id") long id) {
		Company oldCompany = companyService.findById(id).orElseThrow(()->new ResourseNotFoundException("not found company with id: " + id));
		oldCompany.setNameCom(company.getNameCom());
		oldCompany.setAddresCom(company.getAddresCom());
		oldCompany.setContactPerson(company.getContactPerson());
		oldCompany.setNoteCom(company.getNoteCom());
		oldCompany.setStatusCom(company.getStatusCom());
		oldCompany.setWebsiteCom(company.getWebsiteCom());
		oldCompany.setUpdatedDate(LocalDateTime.now());
		return companyService.save(oldCompany);
	}
	
	//delete company
	@DeleteMapping("/deleteCompany")
	public void deleteCompany(@RequestParam("id") long id) {
		companyService.findById(id).orElseThrow(()->new ResourseNotFoundException("not found company with id: " + id));
		companyService.delete(id);
	}
}
