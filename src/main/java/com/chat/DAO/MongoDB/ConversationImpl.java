package com.chat.DAO.MongoDB;

import java.util.Optional;
import java.util.UUID;

import com.chat.DAO.ConversationDAO;
import com.chat.Models.Conversation;
import com.chat.PropertyManager.PropUtils;
import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;

public class ConversationImpl implements ConversationDAO {
  @Override
  public void InsertConversation(Conversation conversation) throws Exception {

    conversation.setId(UUID.randomUUID());

    Optional<String> connectionString = PropUtils.GetProperty("mongodb-connection");

    MongoClient client = new MongoClient(connectionString.get());
    MongoDatabase dtb = client.getDatabase(PropUtils.GetProperty("mongodb-db").get());

    String objJson = new Gson().toJson(conversation);
    dtb.getCollection("conversation").insertOne(new Document().parse(objJson));

  }
}
