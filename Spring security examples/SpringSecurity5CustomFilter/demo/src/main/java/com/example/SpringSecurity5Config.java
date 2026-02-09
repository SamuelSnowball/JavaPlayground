package com.example;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
class SpringSecurity5Config extends WebSecurityConfigurerAdapter {

    // private final CustomFilter customFilter;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/").permitAll() // everyone can access / 
                .anyRequest().authenticated() // any other requests must be authenticated
                .and()
                .formLogin()
                .and()
                .httpBasic();
        
        
        // A different method to include a filter in the chain, in this demo it's configured within SecurityFilterChain
        // http.addFilterBefore(customFilter, BasicAuthenticationFilter.class);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("Admin")
                .password("password")
                .roles("ADMIN")
                .build();
        auth.inMemoryAuthentication().withUser(user);
    }
}