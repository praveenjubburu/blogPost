package com.praveen.blogpostapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.praveen.blogpostapp.entity.UserInfo;

import java.util.Optional;
public interface UserRepository extends JpaRepository<UserInfo, Integer> {

    public UserInfo findByEmail(String email);
      
}
