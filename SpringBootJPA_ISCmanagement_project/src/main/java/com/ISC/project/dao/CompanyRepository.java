package com.ISC.project.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ISC.project.model.Company;

public interface CompanyRepository extends JpaRepository<Company, Integer>{

}
