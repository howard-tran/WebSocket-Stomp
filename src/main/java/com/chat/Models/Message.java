package com.chat.Models;

import java.util.UUID;

public class Message {

  // properties

  private UUID id;
  private String Sender;
  private String Receiver;
  private String Content;

  // getter, setter

  public UUID getId() {
    return this.id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

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

  public String getContent() {
    return this.Content;
  }

  public void setContent(String Content) {
    this.Content = Content;
  }

}
