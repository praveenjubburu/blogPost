package com.praveen.blogpostapp.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private static final String[]  allUsers = {"/registration","/regcompleted","/**","/pagingSearchController","/filterController","/searchController","/sortByAsc/**","/sortByDesc/**","/showpost/**","/pagingForSearch",
            "/pagingFilterController","/pagingForFilter","/pagingController","/getPosts","/search","/filter"};

    private static final String[] authorAndAdmin = {"/createPost","/deletePost/**","/updatePost/**","/savePost/**","/saveComment/**"};
    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

        return daoAuthenticationProvider;
    }
    @Bean
    public PasswordEncoder passwordEncoder(){

        return  new BCryptPasswordEncoder();

    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers(authorAndAdmin)
                .hasAnyRole("AUTHOR","ADMIN")
                .requestMatchers(allUsers)
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .defaultSuccessUrl("/")
                .permitAll();

        return httpSecurity.build();
    }

}