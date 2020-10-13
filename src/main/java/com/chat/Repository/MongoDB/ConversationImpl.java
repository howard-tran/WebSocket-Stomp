package com.chat.Repository.MongoDB;

import com.chat.Models.Conversation;
import com.chat.Models.User;
import com.chat.PropertyManager.DatabaseSupplier;
import com.chat.PropertyManager.PropUtils;
import com.chat.Repository.IConversationDAO;
import com.google.gson.Gson;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.bson.Document;
import org.springframework.stereotype.Repository;
import org.yaml.snakeyaml.events.DocumentEndEvent;

@Repository(DatabaseSupplier.MongoDB.Chat.Conversation)
public class ConversationImpl implements IConversationDAO {

  @Override
  public void InsertConversation(Conversation conversation) throws Exception {
    conversation.setId(UUID.randomUUID());

    HashMap<String, String> database = PropUtils.GetMongoDBChat();

    MongoClient client = MongoClients.create(database.get("connection"));
    MongoDatabase dtb = client.getDatabase(database.get("database"));

    String objJson = new Gson().toJson(conversation);
    dtb.getCollection("conversation").insertOne(new Document().parse(objJson));
  }

  @Override
  public FindIterable<Document> GetConversation(String sender, String receiver)
    throws Exception {
    HashMap<String, String> database = PropUtils.GetMongoDBChat();

    MongoClient client = MongoClients.create(database.get("connection"));
    MongoDatabase dtb = client.getDatabase(database.get("database"));

    Document condition1 = new Document("sender", sender);
    Document condition2 = new Document("receiver", receiver);
    Document filter = Document.parse(
      String.format(
        "{$and: [%s, %s]}",
        condition1.toJson(),
        condition2.toJson()
      )
    );

    return dtb.getCollection("conversation").find(filter);
  }

  @Override
  public FindIterable<Document> GetUserConversation(User user)
    throws Exception {
    HashMap<String, String> database = PropUtils.GetMongoDBChat();

    MongoClient client = MongoClients.create(database.get("connection"));
    MongoDatabase dtb = client.getDatabase(database.get("database"));

    Document filter = new Document("sender", user.getUserName());
    return dtb
      .getCollection("conversation")
      .find(filter)
      .sort(new Document("unixTime", "-1"));
  }
}