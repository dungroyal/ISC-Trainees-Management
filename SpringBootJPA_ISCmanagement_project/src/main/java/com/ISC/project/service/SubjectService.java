package com.ISC.project.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.ISC.project.dao.SubjectRepository;
import com.ISC.project.model.Subject;

@Service
@Transactional
public class SubjectService {
	@Autowired
	private SubjectRepository subjectRepository;
	
	public Optional<Subject> findById(long id){
		return subjectRepository.findById(id);
	}
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
	//Check Code Subject
	public List<String> checkCodeSubject(@RequestParam("newCodeSub") String newCodeSub) {
		return this.subjectRepository.checkCodeSubject(newCodeSub);
	}
	//Pagination
	public Page<Subject> findSubject(Pageable pageable) {
		return subjectRepository.findSubject(pageable);
	}
	
	//Search Subject
	public List<Subject> searchSubject(String keyWord) {
		return this.subjectRepository.searchSubject(keyWord);
	}
}
