package com.chat.Repository.MongoDB;

import com.chat.Models.Conversation;
import com.chat.Models.Message;
import com.chat.PropertyManager.DatabaseSupplier;
import com.chat.PropertyManager.PropUtils;
import com.chat.Repository.IMessageDAO;
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

@Repository(DatabaseSupplier.MongoDB.Chat.Message)
public class MessageImpl implements IMessageDAO {

  @Override
  public void InsertMessage(Message message) throws Exception {
    HashMap<String, String> database = PropUtils.GetMongoDBChat();

    MongoClient client = MongoClients.create(database.get("connection"));
    MongoDatabase dtb = client.getDatabase(database.get("database"));

    String objJson = new Gson().toJson(message);
    dtb.getCollection("message").insertOne(new Document().parse(objJson));
  }

  @Override
  public FindIterable<Document> GetMessage(Conversation conversation)
    throws Exception {
    HashMap<String, String> database = PropUtils.GetMongoDBChat();

    MongoClient client = MongoClients.create(database.get("connection"));
    MongoDatabase dtb = client.getDatabase(database.get("database"));

    // message from me
    Document condition1 = Document.parse(
      String.format(
        "{\"$and\": [{\"sender\": \"%s\"}, {\"receiver\": \"%s\"}]}",
        conversation.getSender(),
        conversation.getReceiver()
      )
    );
    // message from other
    Document condition2 = Document.parse(
      String.format(
        "{\"$and\": [{\"sender\": \"%s\"}, {\"receiver\": \"%s\"}]}",
        conversation.getReceiver(),
        conversation.getSender()
      )
    );
    // merge 2 condition
    Document filter = Document.parse(
      String.format("{\"$or\": [%s, %s]}", condition1.toJson(), condition2.toJson())
    );
    System.out.println(filter.toJson());
    return dtb
      .getCollection("message")
      .find(filter)
      .sort(new Document("unixTime", -1));
  }
}
