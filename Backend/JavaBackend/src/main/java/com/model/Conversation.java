package com.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.Expose;
import org.bson.types.ObjectId;

public class Conversation extends MongoIdModel {
	private String senderId;
	private String receiverId;

	public Conversation() {
	}

	public Conversation(String senderId, String receiverId) {
		this.senderId = senderId;
		this.receiverId = receiverId;
	}

	public Conversation(ObjectId _id, String senderId, String receiverId) {
		this._id = _id;
		this.senderId = senderId;
		this.receiverId = receiverId;
  }
  
  public Conversation reverse() {
    return new Conversation(null, this.getReceiverId(), this.getSenderId());
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

	@Override
	public String toString() {
		return ("{" + " _id='" + get_id() + "'" + ", senderId='" + getSenderId() + "'" + ", receiverId='"
				+ getReceiverId() + "'" + "}");
	}
}
