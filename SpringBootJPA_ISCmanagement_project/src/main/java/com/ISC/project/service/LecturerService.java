package com.ISC.project.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

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
	
	public List<String> checkCodeLecturer(@RequestParam("codeLec") String newCodeLec){
		return this.lecturerRepository.checkCodeLecturer(newCodeLec);
	}
	
	public List<String> checkEmailLecturer(@RequestParam("emailLec") String newEmailLec){
		return this.lecturerRepository.checkEmailLecturer(newEmailLec);
	}
	
	public List<String> checkCodeLecturerUpdate(@RequestParam("updateCodeLec") String updateCodeLec){
		return this.lecturerRepository.checkCodeLecturerUpdate(updateCodeLec);
	}
	
	public Page<Lecturer> findLecturer(Pageable pageable){
		return this.lecturerRepository.findLecturer(pageable);
	}
	
	public List<Lecturer> searchLecturer(String keyWord){
		return this.lecturerRepository.searchLecturer(keyWord);
	}
}
