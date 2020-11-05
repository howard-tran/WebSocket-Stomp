package com.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
  private MongoUserService mongoUserService; 

  @Autowired
  public WebSecurityConfig(MongoUserService mongoUserService) {
    this.mongoUserService = mongoUserService;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
      .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
      .and()
      .authorizeRequests()
      .antMatchers("/", "/signup")
      .permitAll()
      .and()
      .formLogin()
      .loginPage("/login").permitAll()
      .and()
      .logout()
      .and()
      .httpBasic();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(this.mongoUserService); 
  }
}
