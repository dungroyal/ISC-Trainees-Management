package com.ISC.project.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ISC.project.dao.SubjectRepository;
import com.ISC.project.model.Subject;

@Service
@Transactional
public class SubjectService {
	@Autowired
	private SubjectRepository subjectRepository;
	
	public Subject save(Subject subject) {
		return subjectRepository.save(subject);
	}
	
	public List<Subject> listAllSubject(){
		return subjectRepository.findAll();
	}
	
	public Subject get(long id) {
		return subjectRepository.findById(id).get();
	}
	
	public void delete(long id) {
		subjectRepository.deleteById(id);
	}
}
