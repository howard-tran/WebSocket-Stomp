package com.model;

public class Conversation {
  private String _id;
  private String senderId;
  private String receiverId;

  public Conversation(String senderId, String receiverId) {
    this.senderId = senderId;
    this.receiverId = receiverId;
  }

  public Conversation(String _id, String senderId, String receiverId) {
    this._id = _id;
    this.senderId = senderId;
    this.receiverId = receiverId;
  }

  public String get_id() {
    return this._id;
  }

  public void set_id(String _id) {
    this._id = _id;
  }

  public String getSenderId() {
    return this.senderId;
  }

  public void setSenderId(String senderId) {
    this.senderId = senderId;
  }

  public String getReceiverId() {
    return this.receiverId;
  }

  public void setReceiverId(String receiverId) {
    this.receiverId = receiverId;
  }
}
