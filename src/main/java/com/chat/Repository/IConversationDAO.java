package com.chat.Repository;

import com.chat.Models.Conversation;
import com.chat.Models.User;
import com.mongodb.client.FindIterable;
import java.util.List;
import org.bson.Document;

public interface IConversationDAO {
  void InsertConversation(Conversation conversation) throws Exception;

  FindIterable<Document> GetUserConversation(User user) throws Exception;

  FindIterable<Document> GetConversation(String sender, String receiver)
    throws Exception;
}
