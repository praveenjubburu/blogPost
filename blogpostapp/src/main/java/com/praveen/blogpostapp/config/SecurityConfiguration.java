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

    private static final String[]  allUsers = {"/registration","/regcompleted","/displaydata","/pagingSearchController","/filterController","/searchController","/sortByAsc/**","/sortByDesc/**","/showpost/**","/pagingForSearch",
            "/pagingFilterController","/pagingForFilter","/pagingController"};

    private static final String[] authorAndAdmin = {"/createPost","/deletepost/","/updatepost/","/updateComment/**","/deleteComment/**","/processComment"};
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
                .loginPage("/loginPage")
                .usernameParameter("email")
                .loginProcessingUrl("/performLogin")
                .defaultSuccessUrl("/displaydata")
                .permitAll();

        return httpSecurity.build();
    }

}