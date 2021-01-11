package com.ISC.project.dao;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import com.ISC.project.model.User;



@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);
	
	@Query("select username from User where username = :newUsername")
	public List<String > checkUsername(@RequestParam("newUsername") String newUsername);
	
	@Query("select email from User where email = :newEmail")
	public List<String > checkEmail(@RequestParam("newEmail") String newEmail);
	
	@Query("select username from User where id = ?1")
	public String getUserNameById(@RequestParam("id") long id);
	
	@Query("select email from User where id = ?1")
	public String getEmailById(@RequestParam("id") long id);
	
	//Pagination
	@Query("select user from User user")
	public Page<User> findUser(Pageable pageable);
		
	//Search User
	@Query("select user from User user where concat(user.username, user.password, user.email, user.firstName, user.lastName) like %?1%")
	public Page<User> searchUser(String keyWord,Pageable pageable);
}
