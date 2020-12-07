package com.ISC.project.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ISC.project.model.Student;

public interface StudentRepository extends JpaRepository<Student, Integer>{

}
