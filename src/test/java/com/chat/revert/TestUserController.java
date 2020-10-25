package com.chat.revert;

import com.chat.Repository.IUserDAO;

import java.util.ArrayList;
import java.util.List;

import com.chat.Models.User;
import com.chat.PropertyManager.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TestUserController {
  private IUserDAO userDao; 

  @Autowired
  void setUserDao(@Qualifier(DatabaseSupplier.MongoDB.Chat.User) IUserDAO userDao) {
    this.userDao = userDao; 
  }

  @Test
  void revertChange() throws Exception {
    int testSize = 1500; 

    List<User> listUser = new ArrayList<>();
    
    for (int i = 0; i < testSize; i++) {
      listUser.add(new User("##test##**admin" + String.valueOf(i), "123**")); 
    }
    for (int i = 0; i < testSize; i++) {
      User u = this.userDao.GetUser(listUser.get(i).getUserName()).get(0); 
      
      this.userDao.DeleteUser(u.getId());
    }
  }
}
