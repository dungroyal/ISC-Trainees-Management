package com.ISC.project.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import com.ISC.project.dao.ClazzRepository;
import com.ISC.project.model.Clazz;

@Service
@Transactional
public class ClazzService {
	@Autowired
	private ClazzRepository clazzRepository;
	
	public Optional<Clazz> findById(long id){
		return clazzRepository.findById(id);
	}
	
	public Clazz save(Clazz clazz) {
		return clazzRepository.save(clazz);
	}
	
	public List<Clazz> listAllClazz(){
		return clazzRepository.findAll();
	}
	
	public Clazz get(long id) {
		return clazzRepository.findById(id).get();
	}
	
	public void delete(long id) {
		clazzRepository.deleteById(id);
	}
	
	public List<String> checkName(@RequestParam("newName") String newName){
		return this.clazzRepository.checkName(newName);
	}
	
	public String getNameById(@RequestParam("id") long id) {
		return this.clazzRepository.getNameById(id);
	}
//	public List<Clazz> searchCll(String key){
//		return this.clazzRepository.searchCll(key);
//	}
	
	public Page<Clazz> searchClazz(String keyWord, Pageable pageable){
		return this.clazzRepository.searchClz(keyWord,pageable);
	}
	
	public Page<Clazz> findClz(Pageable pageable){
		return this.clazzRepository.findClz(pageable);
	}
	
}
