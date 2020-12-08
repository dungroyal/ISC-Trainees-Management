package com.ISC.project.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	public University get(long id) {
		return universityRepository.findById(id).get();
	}

	public void delete(long id) {
		universityRepository.deleteById(id);
	}
}
