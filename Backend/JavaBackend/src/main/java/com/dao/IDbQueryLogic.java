package com.dao;

import com.google.gson.Gson;
import com.helper.IFunction2;
import com.model.MongoIdModel;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.util.HashMap;
import org.bson.Document;

public interface IDbQueryLogic {
	public default Object run(HashMap<String, String> dtb, String collectionName,
			IFunction2<MongoCollection<Document>, Object> func) throws Exception {
		//
		MongoClient client = MongoClientIns.GetMongoClient();
		MongoDatabase database = client.getDatabase(dtb.get("database"));
		MongoCollection<Document> collection = database.getCollection(collectionName);

		return func.run(collection);
	}

	public default Object parseWithId(Document doc, Class classOf) {
		Object obj = new Gson().fromJson(doc.toJson(), classOf);
		((MongoIdModel) obj).set_id(doc.getObjectId("_id"));
		return obj;
	}
}
