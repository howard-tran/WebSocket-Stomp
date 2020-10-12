package com.chat.Repository;

import com.chat.Models.User;
import com.mongodb.client.FindIterable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.bson.Document;

public interface IUserDAO {
  void InsertUser(User user) throws Exception;

  void InsertManyUser(List<User> userList) throws Exception;

  void DeleteUser(UUID id) throws Exception;

  FindIterable<Document> GetUser(String userName) throws Exception;

  FindIterable<Document> GetUserMatch(String searchKey) throws Exception;
}
