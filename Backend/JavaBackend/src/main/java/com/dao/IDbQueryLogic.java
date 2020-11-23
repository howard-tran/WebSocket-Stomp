package com.dao;

import com.google.gson.Gson;
import com.helper.IFunction2;
import com.model.MongoIdModel;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import java.nio.channels.ConnectionPendingException;
import java.util.HashMap;
import org.bson.Document;

public interface IDbQueryLogic {
  public default <T> T run(HashMap<String, String> dtb, String collectionName,
      IFunction2<MongoCollection<Document>, T> func) throws Exception {
    //
    MongoClient client = MongoClientIns.GetMongoClient();
    MongoDatabase database = null; 

    try {
      database = client.getDatabase(dtb.get("database"));
    } catch (Exception e) {
      throw new ConnectionPendingException(); 
    }
		MongoCollection<Document> collection = database.getCollection(collectionName);

		return (T)func.run(collection);
	}

	public default <T> T parseWithId(Document doc, Class classOf) {
		Object obj = new Gson().fromJson(doc.toJson(), classOf);
		((MongoIdModel) obj).set_id(doc.getObjectId("_id"));
		return (T)obj;
	}
}
