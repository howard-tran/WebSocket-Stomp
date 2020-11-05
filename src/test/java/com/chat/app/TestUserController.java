package com.chat.app;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.chat.App;
import com.chat.Models.User;
import com.chat.PropertyManager.DatabaseSupplier;
import com.chat.Repository.IUserDAO;
import com.chat.Repository.MongoDB.UserImpl;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TestUserController {
  @Autowired
  private MockMvc mockMvc;

  @Test
  void SignUpUser() throws Exception {
    int testSize = 1500;

    List<User> listUser = new ArrayList<>();

    for (int i = 0; i < testSize; i++) {
      listUser.add(new User("##test##**admin" + String.valueOf(i), "123**"));
    }
    for (int i = 0; i < testSize; i++) {
      mockMvc
        .perform(
          MockMvcRequestBuilders
            .post(App.API + "/user/add")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(new Gson().toJson(listUser.get(i)))
        )
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.data").exists())
        .andExpect(MockMvcResultMatchers.jsonPath("$.data").value(true));
    }
  }
}
