package com.chat.Models;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;

public class Conversation {
  // properties

  private UUID id;
  private String sender;
  private String receiver;
  private String unixTime;

  // getter, setter

  public Conversation() {}

  public Conversation(String sender, String receiver) {
    this.sender = sender;
    this.receiver = receiver;
  }

  public Conversation(UUID id, String sender, String receiver) {
    this.id = id;
    this.sender = sender;
    this.receiver = receiver;
  }

  public String getSender() {
    return this.sender;
  }

  public void setSender(String sender) {
    this.sender = sender;
  }

  public String getReceiver() {
    return this.receiver;
  }

  public void setReceiver(String receiver) {
    this.receiver = receiver;
  }

  public UUID getId() {
    return this.id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getUnixTime() {
    return this.unixTime;
  }

  public void setUnixTime(String unixTime) {
    this.unixTime = unixTime;
  }
}
