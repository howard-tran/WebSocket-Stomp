package com.chat.Models;

public class LoginSession {
  private String userName;
  private String passWord;
  private String unixTime;

  public LoginSession(String userName, String passWord) {
    this.userName = userName;
    this.passWord = passWord;
  }

  public LoginSession(String userName, String passWord, String unixTime) {
    this.userName = userName;
    this.passWord = passWord;
    this.unixTime = unixTime;
  }

  public String getUserName() {
    return this.userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getPassWord() {
    return this.passWord;
  }

  public void setPassWord(String passWord) {
    this.passWord = passWord;
  }

  public String getUnixTime() {
    return this.unixTime;
  }

  public void setUnixTime(String unixTime) {
    this.unixTime = unixTime;
  }
}
