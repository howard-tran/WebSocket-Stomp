package com.chat.DAO;

import com.chat.Models.User;
import java.util.List;
import java.util.UUID;

public interface IUserDAO {
  void InsertUser(User user) throws Exception;

  void InsertManyUser(List<User> userList) throws Exception;

  void DeleteUser(UUID id) throws Exception;

  User GetUser(String userName) throws Exception;

  List<User> FindUser(String searchKey) throws Exception;
}
