package com.chat.Models;

import java.util.UUID;

public class Conversation {

  // properties

  private UUID id;
  private String Sender;
  private String Receiver;

  // getter, setter

  public String getSender() {
    return this.Sender;
  }

  public void setSender(String Sender) {
    this.Sender = Sender;
  }

  public String getReceiver() {
    return this.Receiver;
  }

  public void setReceiver(String Receiver) {
    this.Receiver = Receiver;
  }

  public UUID getId() {
    return this.id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

}
