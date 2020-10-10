package com.chat.DAO;

import com.chat.Models.Message;

public interface MessageDAO {
  void InsertMessage(Message message) throws Exception;
}
