package com.ISC.project.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.ISC.project.dao.JobTitleRepository;
import com.ISC.project.model.JobTitle;
@Service
@Transactional
public class JobTitleService {
	@Autowired
	private JobTitleRepository JobTitleRepository;
	
	public Optional<JobTitle> findById(long id){
		return JobTitleRepository.findById(id);
	}
	
	public JobTitle save(JobTitle JobTitle) {
		return JobTitleRepository.save(JobTitle);
	}
	
	public List<JobTitle> listAllJobTitle(){
		return JobTitleRepository.findAll();
	}
	
	public JobTitle get(long id) {
		return JobTitleRepository.findById(id).get();
	}
	
	public void delete(long id) {
		JobTitleRepository.deleteById(id);
	}
	
	public List<String> checkName(@RequestParam("newName") String newName){
		return this.JobTitleRepository.checkName(newName);
	}
	
	public String getNameById(@RequestParam("id") long id) {
		return this.JobTitleRepository.getNameById(id);
	}
	
	public Page<JobTitle> searchJob(String keyWord, Pageable pageable){
		return this.JobTitleRepository.searchJob(keyWord, pageable);
	}
	
	public Page<JobTitle> findJob(Pageable pageable){
		return this.JobTitleRepository.findJob(pageable);
	}
}
