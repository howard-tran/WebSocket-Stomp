package com.chat;

import java.util.List;
import java.util.Optional;

import com.chat.Models.User;
import com.chat.Services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class MongoUserService implements UserDetailsService {
  private UserService userService;
  private PasswordEncoder passwordEncoder;

  @Autowired
  public MongoUserService(UserService userService, PasswordEncoder passwordEncoder) {
    this.userService = userService;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<User> user = this.userService.GetUser(username); 

    if (user.isEmpty()) {
      throw new UsernameNotFoundException("user not found"); 
    }
    
    return org.springframework.security.core.userdetails.User
      .builder()
      .username(user.get().getUserName())
      .password(this.passwordEncoder.encode(user.get().getPassWord()))
      .build(); 
  }
}
