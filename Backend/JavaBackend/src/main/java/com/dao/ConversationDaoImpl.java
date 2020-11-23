package com.dao;

import com.google.gson.Gson;
import com.helper.DatabaseSupplier;
import com.helper.IFunction;
import com.helper.IFunction2;
import com.helper.PropertyHelper;
import com.model.Conversation;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

@Repository(DatabaseSupplier.MongoDB.BetStore.Conversation)
public class ConversationDaoImpl implements IConversationDao {

  @Override
  public List<Conversation> getAllConversation() throws Exception {
    return this.run(PropertyHelper.getMongoDBChat(), "Conversation", collection -> {
      var res = new ArrayList<Conversation>();

      for (var doc : collection.find()) {
        res.add(this.parseWithId(doc, Conversation.class));
      }
      return res;
    });
  }

  @Override
  public List<Conversation> getConversation(String senderId) throws Exception {
    return this.run(PropertyHelper.getMongoDBChat(), "Conversation", collection -> {
      var filter = new Document("senderId", senderId);
      var res = new ArrayList<Conversation>();

      for (var doc : collection.find(filter)) {
        res.add(this.parseWithId(doc, Conversation.class));
      }
      return res;
    });
  }

  @Override
  public List<Conversation> getConversation(String senderId, String receiverId) throws Exception {
    return (ArrayList<Conversation>) this.run(PropertyHelper.getMongoDBChat(), "Conversation", collection -> {
      var con1 = new Document("senderId", senderId);
      var con2 = new Document("receiverId", receiverId);
      var filter = Document.parse(String.format("{$and: [%s, %s]}", con1.toJson(), con2.toJson()));

      var res = new ArrayList<Conversation>();
      for (var doc : collection.find(filter).sort(new Document("_id", -1))) {
        res.add(this.parseWithId(doc, Conversation.class));
      }
      return res;
    });
  }

  @Override
  public List<Conversation> getConversationById(String _id) throws Exception {
    return null;
  }

  @Override
  public String insertConversation(Conversation data) throws Exception {
    return (String) this.run(PropertyHelper.getMongoDBChat(), "Conversation", collection -> {
      var json = new Gson().toJson(data);
      var doc = Document.parse(json);

      collection.insertOne(doc);

      return doc.get("_id").toString();
    });
  }

  @Override
  public void deleteConversation(String _id) throws Exception {
    this.run(PropertyHelper.getMongoDBChat(), "Conversation", collection -> {
      collection.deleteOne(new Document("_id", new ObjectId(_id)));

      return null;
    });
  }
}
