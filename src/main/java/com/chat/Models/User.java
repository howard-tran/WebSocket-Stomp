package com.chat.Models;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;

public class User {
  // properties

  private UUID id;
  private String UserName;
  private String PassWord;

  // getter, setter

  public User() {}

  public User(
    @JsonProperty("UserName") String UserName,
    @JsonProperty("PassWord") String PassWord
  ) {
    this.UserName = UserName;
    this.PassWord = PassWord;
  }

  public User(UUID id, String UserName, String PassWord) {
    this.id = id;
    this.UserName = UserName;
    this.PassWord = PassWord;
  }

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
