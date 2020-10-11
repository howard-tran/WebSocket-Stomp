package com.chat.DAO;

import com.chat.Models.Conversation;
import com.chat.Models.User;
import java.util.List;

public interface IConversationDAO {
  void InsertConversation(Conversation conversation) throws Exception;

  List<Conversation> GetConversation(User user) throws Exception;
}
