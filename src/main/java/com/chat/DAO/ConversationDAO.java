package com.chat.DAO;

import com.chat.Models.Conversation;

public interface ConversationDAO {
  void InsertConversation(Conversation conversation) throws Exception;
}
