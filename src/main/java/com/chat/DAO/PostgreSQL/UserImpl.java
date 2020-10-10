package com.chat.DAO.PostgreSQL;

import java.util.List;
import java.util.UUID;

import com.chat.DAO.UserDAO;
import com.chat.Models.User;

public class UserImpl implements UserDAO {
  @Override
  public void InsertUser(User user) throws Exception {
    // TODO Auto-generated method stub

  }

  @Override
  public void InsertManyUser(List<User> userList) throws Exception {
    // TODO Auto-generated method stub

  }

  @Override
  public void DeleteUser(UUID id) throws Exception {
    // TODO Auto-generated method stub

  }

  @Override
  public List<User> FindUser(String SearchKey) throws Exception {
    return null;

  }

  @Override
  public User GetUser(String UserName) throws Exception {
    return null;

  }
}
