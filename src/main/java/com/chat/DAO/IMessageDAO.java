package com.chat.DAO;

import com.chat.Models.Message;

public interface IMessageDAO {
  void InsertMessage(Message message) throws Exception;
}
