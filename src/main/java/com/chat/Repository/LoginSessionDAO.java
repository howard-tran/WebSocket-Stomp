package com.chat.Repository;

import com.chat.Models.LoginSession;
import java.util.ArrayList;
import java.util.List;

public class LoginSessionDAO {
  private static List<LoginSession> sessions = new ArrayList<>();

  public void AddSession(LoginSession session) {
    LoginSessionDAO.sessions.add(session);
  }

  public List<LoginSession> GetSessions() {
    return LoginSessionDAO.sessions;
  }

  public void RemoveSession(int index) {
    LoginSessionDAO.sessions.remove(index);
  }

  public Boolean CheckUserName(String userName) {
    for (int i = 0; i < LoginSessionDAO.sessions.size(); i++) {
      if (LoginSessionDAO.sessions.get(i).getUserName().equals(userName)) {
        return false;
      }
    }
    return true;
  }
}
