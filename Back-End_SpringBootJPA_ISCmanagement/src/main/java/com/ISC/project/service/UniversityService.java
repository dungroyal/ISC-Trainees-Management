package com.ISC.project.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.ISC.project.dao.UniversityRepository;
import com.ISC.project.model.University;

@Service
@Transactional
public class UniversityService {
	@Autowired
	private UniversityRepository universityRepository;

	public Optional<University> findById(long id) {
		return universityRepository.findById(id);
	}

	public University save(University university) {
		return universityRepository.save(university);
	}

	public List<University> listAlluniversity() {
		return universityRepository.findAll();
	}

	public University getOne(long id) {
		return universityRepository.findById(id).get();
	}

	public void delete(long id) {
		universityRepository.deleteById(id);
	}
	
	public List<String> checkNameUni(@RequestParam("newNameUni") String newNameUni){
		return this.universityRepository.checkNameUni(newNameUni);
	}
	
	public Page<University> searchUni(String keyWord, Pageable pageable){
		return this.universityRepository.searchUni(keyWord, pageable);
	}
	
	public Page<University> findUni(Pageable pageable){
		return this.universityRepository.findUni(pageable);
	}
	
	public String getNameById(@RequestParam("id") long id) {
		return this.universityRepository.getNameById(id);
	}
}
