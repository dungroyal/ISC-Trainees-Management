package com.ISC.project.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ISC.project.model.EmbemdedMajorSubjectId;
import com.ISC.project.model.MajorSubject;

public interface MajorSubjectRepo extends JpaRepository<MajorSubject, EmbemdedMajorSubjectId>{

}
