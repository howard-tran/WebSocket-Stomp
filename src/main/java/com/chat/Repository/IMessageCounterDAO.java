package com.chat.Repository;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import org.bson.Document;

public interface IMessageCounterDAO {
  FindIterable<Document> GetCurrentIndex() throws Exception;

  AggregateIterable<Document> GetNextIndex() throws Exception;

  void IncreaseIndex() throws Exception;
}
