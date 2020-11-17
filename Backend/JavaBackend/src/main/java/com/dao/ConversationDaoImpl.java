package com.dao;

import com.google.gson.Gson;
import com.helper.DatabaseSupplier;
import com.helper.PropertyHelper;
import com.model.Conversation;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.bson.Document;
import org.springframework.stereotype.Repository;

@Repository(DatabaseSupplier.MongoDB.BetStore.Conversation)
public class ConversationDaoImpl implements IConversationDao {

  @Override
  public List<Conversation> getAllConversation() throws Exception {
    HashMap<String, String> dtb = PropertyHelper.GetMongoDBChat();

    MongoClient client = MongoClientIns.GetMongoClient();
    MongoDatabase database = client.getDatabase(dtb.get("database"));

    FindIterable<Document> cursor = database.getCollection("Conversation").find();

    List<Conversation> res = new ArrayList();
    for (Document doc : cursor) {
      res.add(new Gson().fromJson(doc.toJson(), Conversation.class));
    }
    return res;
  }

  @Override
  public List<Conversation> getConversation(String senderId) throws Exception {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<Conversation> getConversationById(String _id) throws Exception {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void insertConversation(Conversation data) throws Exception {
    HashMap<String, String> dtb = PropertyHelper.GetMongoDBChat();

    MongoClient client = MongoClientIns.GetMongoClient();
    MongoDatabase database = client.getDatabase(dtb.get("database"));

    String json = new Gson().toJson(data);
    database.getCollection("Conversation").insertOne(new Document().parse(json));
  }

  @Override
  public void deleteConversation(String _id) throws Exception {
    HashMap<String, String> dtb = PropertyHelper.GetMongoDBChat();

    MongoClient client = MongoClientIns.GetMongoClient();
    MongoDatabase database = client.getDatabase(dtb.get("database"));

    database.getCollection("Conversation").deleteOne(new Document("_id", _id));
  }
}
