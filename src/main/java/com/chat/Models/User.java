package com.chat.Models;

import java.util.UUID;

public class User {

  // properties

  private UUID id;
  private String UserName;
  private String PassWord;

  // getter, setter

  public UUID getId() {
    return this.id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getUserName() {
    return this.UserName;
  }

  public void setUserName(String UserName) {
    this.UserName = UserName;
  }

  public String getPassWord() {
    return this.PassWord;
  }

  public void setPassWord(String PassWord) {
    this.PassWord = PassWord;
  }

}
