package com.chat.Repository.MySQL;

import com.chat.Models.User;
import com.chat.Repository.IUserDAO;
import com.mongodb.client.FindIterable;
import java.util.List;
import java.util.UUID;
import org.bson.Document;

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
  public FindIterable<Document> GetUserMatch(String SearchKey)
    throws Exception {
    return null;
  }

  @Override
  public FindIterable<Document> GetUser(String UserName) throws Exception {
    return null;
  }
}
