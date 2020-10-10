package com.chat.DAO;

import java.util.List;
import java.util.UUID;

import com.chat.Models.User;

public interface UserDAO {

  void InsertUser(User user) throws Exception;

  void InsertManyUser(List<User> userList) throws Exception;

  void DeleteUser(UUID id) throws Exception;

  User GetUser(String UserName) throws Exception;

  List<User> FindUser(String SearchKey) throws Exception;
}
