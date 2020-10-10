package com.chat.DAO.MongoDB;

import com.chat.DAO.IMessageDAO;
import com.chat.Models.Message;
import com.chat.PropertyManager.DatabaseSupplier;
import com.chat.PropertyManager.PropUtils;
import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;
import org.bson.Document;
import org.springframework.stereotype.Repository;

@Repository(DatabaseSupplier.MongoDB.Chat.Message)
public class MessageImpl implements IMessageDAO {

  @Override
  public void InsertMessage(Message message) throws Exception {
    message.setId(UUID.randomUUID());

    HashMap<String, String> database = PropUtils.GetMongoDBChat();

    MongoClient client = new MongoClient(database.get("connection"));
    MongoDatabase dtb = client.getDatabase(database.get("database"));

    String objJson = new Gson().toJson(message);
    dtb.getCollection("message").insertOne(new Document().parse(objJson));
  }
}
