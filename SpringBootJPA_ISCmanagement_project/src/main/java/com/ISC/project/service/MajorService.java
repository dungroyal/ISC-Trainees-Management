package com.ISC.project.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	
	
}
