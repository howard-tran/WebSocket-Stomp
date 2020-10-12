package com.chat.Repository.MySQL;

import com.chat.Models.Conversation;
import com.chat.Models.Message;
import com.chat.Repository.IMessageDAO;
import com.mongodb.client.FindIterable;
import java.util.List;
import org.bson.Document;

public class MessageImpl implements IMessageDAO {

  @Override
  public void InsertMessage(Message message) {
    // TODO Auto-generated method stub
  }

  @Override
  public FindIterable<Document> GetMessage(Conversation conversation)
    throws Exception {
    // TODO Auto-generated method stub
    return null;
  }
}
