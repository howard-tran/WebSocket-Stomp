package com.chat.Services;

import com.chat.DAO.IUserDAO;
import com.chat.LogManager.LogUtils;
import com.chat.Models.User;
import com.chat.PropertyManager.DatabaseSupplier;
import com.mongodb.connection.Stream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
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

  public void AddUser(User user) {
    try {
      userDao.InsertUser(user);
    } catch (Exception e) {
      RuntimeException exception = new RuntimeException(e);

      LogUtils.LogError("[ERROR]", exception);
      throw new RuntimeException(exception);
    }
  }

  public User GetUser(String userName) {
    try {
      return userDao.GetUser(userName);
    } catch (Exception e) {
      RuntimeException exception = new RuntimeException(e);

      LogUtils.LogError("[ERROR]", exception);
      throw new RuntimeException(exception);
    }
  }

  public List<String> FindUser(String searchKey) {
    List<String> userNameList = new ArrayList();
    try {
      return (userDao.FindUser(searchKey)).stream()
        .map(user -> user.getUserName())
        .collect(Collectors.toList());
    } catch (Exception e) {
      RuntimeException exception = new RuntimeException(e);

      LogUtils.LogError("[ERROR]", exception);
      throw new RuntimeException(exception);
    }
  }
}
