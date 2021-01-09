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
	
	public List<String> checkCodeSubject(@RequestParam("codeSub") String newCodeSub){
		return this.subjectRepository.checkCodeSubject(newCodeSub);
	}
	
	public Page<Subject> findSubject(Pageable pageable){
		return this.subjectRepository.findSubject(pageable);
	}
	
	public Page<Subject> searchSubject(String keyWord,Pageable pageable){
		return this.subjectRepository.searchSubject(keyWord,pageable);
	}
}
