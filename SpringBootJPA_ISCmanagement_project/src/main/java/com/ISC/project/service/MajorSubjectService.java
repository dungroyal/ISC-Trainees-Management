package com.ISC.project.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ISC.project.dao.MajorSubjectRepo;
import com.ISC.project.model.EmbemdedMajorSubjectId;
import com.ISC.project.model.MajorSubject;

@Service
@Transactional
public class MajorSubjectService {
	@Autowired
	private MajorSubjectRepo majorSubjectRepo;
	
	public Optional<MajorSubject> findById(EmbemdedMajorSubjectId id){
		return majorSubjectRepo.findById(id);
	}
	public MajorSubject save(MajorSubject majorSubject) {
		return majorSubjectRepo.save(majorSubject);
	}
	
	public List<MajorSubject> listAllmajorSubject(){
		return majorSubjectRepo.findAll();
	}
	
	public MajorSubject get(EmbemdedMajorSubjectId id) {
		return majorSubjectRepo.findById(id).get();
	}
	
	public void delete(EmbemdedMajorSubjectId id) {
		majorSubjectRepo.deleteById(id);
	}
}
