package com.ISC.project.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ISC.project.model.Course;

public interface CourseRepository extends JpaRepository<Course, Long>{

}
