package com.ISC.project.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestParam;

import com.ISC.project.model.Student;

public interface StudentRepository extends JpaRepository<Student, Long>{
	//Check Student Code
	@Query("select codeStu from Student where codeStu = :newCodeStu")
	public List<String> checkCodeStudent(@RequestParam("newCodeStu") String newCodeStu);
	
	//Check email Code
	@Query("select emailStu from Student where emailStu = :newEmailStu")
	public List<String> checkEmailStudent(@RequestParam("newEmailStu") String newEmailStu);
	
	//Check Student Code For Update
	@Query("select codeStu from Student where codeStu not in (:updateCodeStu)")
	public List<String> checkCodeStudentUpdate(@RequestParam("updateCodeStu") String updateCodeStu);
	
	//Check Student Email For Update
	@Query("select emailStu from Student where emailStu not in (:updateEmailStu)")
	public List<String> checkEmailStudentUpdate(@RequestParam("updateEmailStu") String updateEmailStu);
	
	//Pagination
	@Query("select stu from Student stu")
	public Page<Student> findStu(Pageable pageable);
	
	//Search Student
	@Query("select stu from Student stu where concat(stu.lastName, stu.firstName, stu.codeStu, stu.addressStu, stu.phoneStu, "
			+ " stu.emailStu, stu.gpa) like %?1%")
	public List<Student> searchStudent( String keyWord);
}
