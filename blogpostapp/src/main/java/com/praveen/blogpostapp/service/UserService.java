package com.praveen.blogpostapp.service;

import java.util.List;

import com.praveen.blogpostapp.entity.UserInfo;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {
    
	public List<UserInfo> findAll();
	
	public void save(UserInfo user);
	
}
