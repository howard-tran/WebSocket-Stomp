package com.chat.DAO.MySQL;

import com.chat.DAO.IUserDAO;
import com.chat.Models.User;
import java.util.List;
import java.util.UUID;

public class UserImpl implements IUserDAO {

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
