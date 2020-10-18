package com.chat.Repository.MySQL;

import com.chat.Models.Conversation;
import com.chat.Models.User;
import com.chat.Repository.IConversationDAO;
import com.mongodb.client.FindIterable;
import java.util.List;
import org.bson.Document;

public class ConversationImpl implements IConversationDAO {

  @Override
  public void InsertConversation(Conversation conversation) {
    // TODO Auto-generated method stub

  }

  @Override
  public FindIterable<Document> GetUserConversation(User user) throws Exception {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public FindIterable<Document> GetConversation(String sender, String receiver) throws Exception {
    // TODO Auto-generated method stub
    return null;
  }
}
