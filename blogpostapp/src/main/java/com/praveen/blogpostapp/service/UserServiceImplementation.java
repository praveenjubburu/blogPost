package com.praveen.blogpostapp.service;

import java.util.List;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.praveen.blogpostapp.dao.UserRepository;
import com.praveen.blogpostapp.entity.UserInfo;

@Service
@Transactional
public class UserServiceImplementation implements UserService {

	@Autowired
	UserRepository userRepository;


	public UserServiceImplementation(){

	}
	
	public UserServiceImplementation(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	@Override
	public List<UserInfo> findAll() {

		System.out.println("You are here on find all");
		 return userRepository.findAll();
	}

	@Override
	public void save(UserInfo user) {
		
		userRepository.save(user);
		
	}

}
