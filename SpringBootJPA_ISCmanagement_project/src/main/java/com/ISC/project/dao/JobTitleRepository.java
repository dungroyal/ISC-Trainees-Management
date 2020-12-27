package com.ISC.project.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestParam;

import com.ISC.project.model.JobTitle;

public interface JobTitleRepository extends JpaRepository<JobTitle, Long>{
	@Query("select nameJob from JobTitle where nameJob = :newName")
	public List<String> checkName(@RequestParam("newName") String newName);
	
	@Query("select nameJob from JobTitle where id = ?1")
	public String getNameById(@RequestParam("id") long id);
	
	@Query("select job from JobTitle job where concat(job.nameJob) like %?1%")
	public List<JobTitle> searchJob(String keyWord);
	
	@Query("select clz from Clazz clz")
	public Page<JobTitle> findJob(Pageable pageable);

}
