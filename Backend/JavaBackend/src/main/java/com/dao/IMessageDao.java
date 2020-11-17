package com.dao;

import com.model.Conversation;
import com.model.Message;
import java.util.List;

public interface IMessageDao {
  public List<Message> getAllMessage() throws Exception;

  public void insertMessage(Message data) throws Exception;

  public List<Message> getMessageById(String _id) throws Exception;

  public List<Message> getMessage(String senderId, String receiverId) throws Exception;

  public List<Message> getMessageByConversation(Conversation conversation) throws Exception;
}
