package com.chat.DAO;

import com.chat.Models.Conversation;

public interface IConversationDAO {
  void InsertConversation(Conversation conversation) throws Exception;
}
