package com.ISC.project.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestParam;

import com.ISC.project.model.Company;

public interface CompanyRepository extends JpaRepository<Company, Long>{
	@Query("select nameCom from Company where nameCom = :newNameCom")
	public List<String > checkNameCom(@RequestParam("newNameCom") String newNameCom);
}
