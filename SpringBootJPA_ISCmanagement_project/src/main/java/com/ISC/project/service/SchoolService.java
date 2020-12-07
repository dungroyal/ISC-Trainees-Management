package com.ISC.project.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ISC.project.dao.SchoolRepository;
import com.ISC.project.model.School;

@Service
@Transactional
public class SchoolService {
	@Autowired
	private SchoolRepository schoolRepository;

	public Optional<School> findById(long id) {
		return schoolRepository.findById(id);
	}

	public School save(School school) {
		return schoolRepository.save(school);
	}

	public List<School> listAllSchool() {
		return schoolRepository.findAll();
	}

	public School get(long id) {
		return schoolRepository.findById(id).get();
	}

	public void delete(long id) {
		schoolRepository.deleteById(id);
	}
}
