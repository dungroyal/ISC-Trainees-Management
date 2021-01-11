package com.ISC.project.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import com.ISC.project.dao.UserRepository;

import com.ISC.project.model.User;



@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	UserRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

		return UserDetailsImpl.build(user);
	}
	
	public Optional<User> findById(long id){
		return userRepository.findById(id);
	} 
	public User save(User us) {
		return userRepository.save(us);
	}
	
	public List<User> listAllUser(){
		return userRepository.findAll();
	}
	
	public User get(long id) {
		return userRepository.findById(id).get();
	}
	
	public List<String> checkUserName(@RequestParam("newUserName") String newUserName){
		return this.userRepository.checkUsername(newUserName);
	}
	
	public List<String> checkEmail(@RequestParam("newEmail") String newEmail){
		return this.userRepository.checkEmail(newEmail);
	}
	
	public String getUserNameById(@RequestParam("id") long id) {
		return this.userRepository.getUserNameById(id);
	}
	
	public String getEmailById(@RequestParam("id") long id) {
		return this.userRepository.getEmailById(id);
	}
	
	public void delete(long id) {
		userRepository.deleteById(id);
	}
	public Page<User> findUser(Pageable pageable){
		return this.userRepository.findUser(pageable);
	}
	
	public Page<User> searchUser(String keyWord,Pageable pageable){
		return this.userRepository.searchUser(keyWord,pageable);
	}
}
