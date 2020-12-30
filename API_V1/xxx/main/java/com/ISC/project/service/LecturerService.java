package com.ISC.project.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ISC.project.dao.LecturerRepository;
import com.ISC.project.model.Lecturer;


@Service
@Transactional
public class LecturerService {
	@Autowired
	private LecturerRepository lecturerRepository;
	public Optional<Lecturer> findById(long id){
		return lecturerRepository.findById(id);
	}
	public Lecturer save(Lecturer lecture) {
		return lecturerRepository.save(lecture);
	}
	
	public List<Lecturer> listAllLecture(){
		return lecturerRepository.findAll();
	}
	
	public Lecturer get(long id) {
		return lecturerRepository.findById(id).get();
	}
	
	public void delete(long id) {
		lecturerRepository.deleteById(id);
	}
}
