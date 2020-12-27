package com.ISC.project.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestParam;

import com.ISC.project.model.Clazz;

public interface ClazzRepository extends JpaRepository<Clazz, Long>{
	@Query("select nameClazz from Clazz where nameClazz = :newName")
	public List<String> checkName(@RequestParam("newName") String newName);
	
	@Query("select nameClazz from Clazz where id = ?1")
	public String getNameById(@RequestParam("id") long id);
	
	@Query("select clz from Clazz clz where concat(clz.nameClazz,clz.numOfStu,clz.pointGra) like %?1%")
	public List<Clazz> searchClz(String keyWord);
	
	@Query("select clz from Clazz clz")
	public Page<Clazz> findClz(Pageable pageable);
}
