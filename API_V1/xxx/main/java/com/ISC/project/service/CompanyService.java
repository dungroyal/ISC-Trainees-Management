package com.ISC.project.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ISC.project.dao.CompanyRepository;
import com.ISC.project.model.Company;

@Service
@Transactional
public class CompanyService {
	@Autowired
	private CompanyRepository companyRepository;
	
	public Optional<Company> findById(long id){
		return companyRepository.findById(id);
	}
	public Company save(Company company) {
		return companyRepository.save(company);
	}
	
	public List<Company> listAllCompany(){
		return companyRepository.findAll();
	}
	
	public Company get(long id) {
		return companyRepository.findById(id).get();
	}
	
	public void delete(long id) {
		companyRepository.deleteById(id);
	}
	
}
