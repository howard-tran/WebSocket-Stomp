package com.chat.DAO.MongoDB;

import java.util.Optional;
import java.util.UUID;

import com.chat.DAO.MessageDAO;
import com.chat.Models.Message;
import com.chat.PropertyManager.PropUtils;
import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;

public class MessageImpl implements MessageDAO {
  @Override
  public void InsertMessage(Message message) throws Exception {

    message.setId(UUID.randomUUID());

    Optional<String> connectionString = PropUtils.GetProperty("mongodb-connection");

    MongoClient client = new MongoClient(connectionString.get());
    MongoDatabase dtb = client.getDatabase(PropUtils.GetProperty("mongodb-db").get());

    String objJson = new Gson().toJson(message);
    dtb.getCollection("message").insertOne(new Document().parse(objJson));
  }
}
