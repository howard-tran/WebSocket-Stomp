package com.chat.Repository.MongoDB;

import com.chat.PropertyManager.DatabaseSupplier;
import com.chat.PropertyManager.PropUtils;
import com.chat.Repository.IMessageCounterDAO;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.bson.Document;
import org.springframework.stereotype.Repository;

@Repository(DatabaseSupplier.MongoDB.Chat.MessageCounter)
public class MessageCounterImpl implements IMessageCounterDAO {

  @Override
  public FindIterable<Document> GetCurrentIndex() throws Exception {
    HashMap<String, String> database = PropUtils.GetMongoDBChat();

    MongoClient client = MongoClientIns.GetMongoClient();
    MongoDatabase dtb = client.getDatabase(database.get("database"));

    return dtb.getCollection("messageCounter").find().limit(1);
  }

  @Override
  public AggregateIterable<Document> GetNextIndex() throws Exception {
    HashMap<String, String> database = PropUtils.GetMongoDBChat();

    MongoClient client = MongoClientIns.GetMongoClient();
    MongoDatabase dtb = client.getDatabase(database.get("database"));

    Document query = Document.parse(
      String.format("{ $project: { seq: { $add: [ \"$seq\", 1 ] } } }")
    );

    List<Document> listAggregate = new ArrayList<>();
    listAggregate.add(query);

    return dtb.getCollection("messageCounter").aggregate(listAggregate);
  }

  @Override
  public void IncreaseIndex() throws Exception {
    // TODO Auto-generated method stub
    HashMap<String, String> database = PropUtils.GetMongoDBChat();

    MongoClient client = MongoClientIns.GetMongoClient();
    MongoDatabase dtb = client.getDatabase(database.get("database"));

    Document updateQuery = Document.parse(String.format("{$inc: { seq: 1 }}"));
    dtb.getCollection("messageCounter").updateOne(new Document(), updateQuery);
  }
}
