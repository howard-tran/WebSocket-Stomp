package com.chat.Repository.MySQL;

import com.chat.Repository.IMessageCounterDAO;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import org.bson.Document;

public class MessageCounterImpl implements IMessageCounterDAO {

  @Override
  public FindIterable<Document> GetCurrentIndex() throws Exception {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public AggregateIterable<Document> GetNextIndex() throws Exception {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void IncreaseIndex() throws Exception {
    // TODO Auto-generated method stub

  }
}
