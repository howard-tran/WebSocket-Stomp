package com.chat.Repository;

import com.chat.Models.Conversation;
import com.chat.Models.Message;
import com.mongodb.client.FindIterable;
import org.bson.Document;

public interface IMessageDAO {
  void InsertMessage(Message message) throws Exception;

  FindIterable<Document> GetMessage(Conversation conversation) throws Exception;
}
