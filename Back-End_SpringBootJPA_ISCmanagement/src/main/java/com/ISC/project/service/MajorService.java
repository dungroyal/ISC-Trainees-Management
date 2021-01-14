package com.ISC.project.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.ISC.project.dao.MajorRepository;
import com.ISC.project.model.Major;

@Service
@Transactional
public class MajorService {
	@Autowired
	private MajorRepository majorRepository;
	
	public Optional<Major> findById(long id){
		return majorRepository.findById(id);
	}
	public Major save(Major major) {
		return majorRepository.save(major);
	}
	
	public List<Major> listAllMajor(){
		return majorRepository.findAll();
	}
	
	public Major get(long id) {
		return majorRepository.findById(id).get();
	}
	
	public void delete(long id) {
		majorRepository.deleteById(id);
	}
	
	public List<String> checkCodeMajor(@RequestParam("newCodeMajor")String newCodeMajor){
		return this.majorRepository.checkCodeMajor(newCodeMajor);
	}
	
	
	public Page<Major> findMajor(Pageable pageable){
		return this.majorRepository.findMajor(pageable);
	}
	
	public Page<Major> searchMajor(String keyWord, Pageable pageable){
		return this.majorRepository.searchMajor(keyWord, pageable);
	}
}
