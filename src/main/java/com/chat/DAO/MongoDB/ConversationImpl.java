package com.chat.DAO.MongoDB;

import com.chat.DAO.IConversationDAO;
import com.chat.Models.Conversation;
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

@Repository(DatabaseSupplier.MongoDB.Chat.Conversation)
public class ConversationImpl implements IConversationDAO {

  @Override
  public void InsertConversation(Conversation conversation) throws Exception {
    conversation.setId(UUID.randomUUID());

    HashMap<String, String> database = PropUtils.GetMongoDBChat();

    MongoClient client = new MongoClient(database.get("connection"));
    MongoDatabase dtb = client.getDatabase(database.get("database"));

    String objJson = new Gson().toJson(conversation);
    dtb.getCollection("conversation").insertOne(new Document().parse(objJson));
  }
}
