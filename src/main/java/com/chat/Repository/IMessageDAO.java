package com.chat.Repository;

import java.util.List;

import com.chat.Models.Conversation;
import com.chat.Models.Message;
import com.mongodb.client.FindIterable;
import org.bson.Document;

public interface IMessageDAO {
  void InsertMessage(Message message) throws Exception;

  List<Message> GetMessage(Conversation conversation) throws Exception;
}
