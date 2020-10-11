package com.chat.DAO;

import com.chat.Models.Conversation;
import com.chat.Models.Message;
import java.util.List;

public interface IMessageDAO {
  void InsertMessage(Message message) throws Exception;

  List<Message> GetMessage(Conversation conversation) throws Exception;
}
