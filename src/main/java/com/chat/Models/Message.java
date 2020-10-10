package com.chat.Models;

import java.util.UUID;

public class Message {
  // properties

  private UUID id;
  private String Sender;
  private String Receiver;
  private String Content;

  // getter, setter

  public Message() {
    
  }

  public Message(UUID id, String Sender, String Receiver, String Content) {
    this.id = id;
    this.Sender = Sender;
    this.Receiver = Receiver;
    this.Content = Content;
  }

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
