package com.ISC.project.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ISC.project.model.IntakeSubject;
import com.ISC.project.model.EmbemdedIntakeSubjectId;

public interface IntakeSubjectRepo extends JpaRepository<IntakeSubject, EmbemdedIntakeSubjectId>{

}
