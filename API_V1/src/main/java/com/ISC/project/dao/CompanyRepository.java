package com.ISC.project.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestParam;

import com.ISC.project.model.Company;

public interface CompanyRepository extends JpaRepository<Company, Long>{
	@Query("select nameCom from Company where nameCom = :newNameCom")
	public List<String > checkNameCom(@RequestParam("newNameCom") String newNameCom);
	
	@Query("select nameCom from Company where id = ?1")
	public String getNameById(@RequestParam("id") long id);
	
	@Query("select com from Company com where concat(com.nameCom,com.addresCom,com.contactPerson,com.websiteCom,com.statusCom,com.noteCom) like %?1%")
	public List<Company> searchCom(String keyWord);
	
	@Query("select compa from Company compa")
	public Page<Company> findCompa(Pageable pageable);
}
