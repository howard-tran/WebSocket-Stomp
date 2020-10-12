package com.chat.Services;

import com.chat.LogManager.LogUtils;
import com.chat.Models.User;
import com.chat.PropertyManager.DatabaseSupplier;
import com.chat.Repository.IUserDAO;
import com.google.gson.Gson;
import com.mongodb.client.FindIterable;
import com.mongodb.connection.Stream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  private IUserDAO userDao;

  @Autowired
  UserService(
    @Qualifier(DatabaseSupplier.MongoDB.Chat.User) IUserDAO userImpl
  ) {
    this.userDao = userImpl;
  }

  public Optional<Boolean> AddUser(User user) {
    try {
      user.setId(UUID.randomUUID());

      userDao.InsertUser(user);

      return Optional.of(true);
      //
    } catch (Exception e) {
      RuntimeException exception = new RuntimeException(e);

      LogUtils.LogError("[ERROR]", exception);
      return Optional.empty();
    }
  }

  public Optional<Boolean> CheckAvailableUserName(String userName) {
    try {
      FindIterable<Document> cursor = userDao.GetUser(userName);

      return Optional.of((cursor.first() == null));
      //
    } catch (Exception e) {
      RuntimeException exception = new RuntimeException(e);

      LogUtils.LogError("[ERROR]", exception);
      return Optional.empty();
    }
  }

  public Optional<User> GetUser(String userName) {
    try {
      FindIterable<Document> cursor = userDao.GetUser(userName);
      User user = new Gson().fromJson(cursor.first().toJson(), User.class);

      return Optional.of(user);
      //
    } catch (Exception e) {
      RuntimeException exception = new RuntimeException(e);

      LogUtils.LogError("[ERROR]", exception);
      return Optional.empty();
    }
  }

  public Optional<List<String>> FindUser(String searchKey) {
    try {
      FindIterable<Document> cursor = userDao.GetUserMatch(searchKey);
      List<String> res = new ArrayList<>();

      for (Document doc : cursor) {
        res.add((new Gson().fromJson(doc.toJson(), User.class)).getUserName());
      }
      return Optional.of(res);
      //
    } catch (Exception e) {
      RuntimeException exception = new RuntimeException(e);

      LogUtils.LogError("[ERROR]", exception);
      return Optional.empty();
    }
  }
}
