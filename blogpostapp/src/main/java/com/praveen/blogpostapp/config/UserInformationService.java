package com.praveen.blogpostapp.config;

import com.praveen.blogpostapp.dao.UserRepository;
import com.praveen.blogpostapp.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserInformationService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserInfo userInfo = userRepository.findByEmail(email);
        System.err.println(userInfo);
        if(userInfo == null) {
            throw new UsernameNotFoundException("Invalid email or password.");
        }
        if(userInfo.getEmail().equals("praveenjubburu143@gmail.com")) {
            return  User.withUsername(userInfo.getName()).password(userInfo.getPassword()).roles("ADMIN").build();
        }
        else {
            return User.withUsername(userInfo.getName()).password(userInfo.getPassword()).roles("AUTHOR").build();
        }

    }

}