package com.ISC.project.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ISC.project.model.CourseStudent;
import com.ISC.project.model.EmbemdedCourseStudentId;

public interface CourseStudentRepo extends JpaRepository<CourseStudent, EmbemdedCourseStudentId>{

}
