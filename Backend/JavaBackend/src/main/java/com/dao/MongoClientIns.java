package com.dao;

import com.helper.PropertyHelper;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import java.util.Arrays;
import java.util.HashMap;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

public class MongoClientIns {
	private static MongoClient _mongoClientIns;

	public static MongoClient GetMongoClient() throws Exception {
		HashMap<String, String> dtb = PropertyHelper.GetMongoDBChat();

		if (MongoClientIns._mongoClientIns == null) {
			MongoClientIns._mongoClientIns = MongoClients.create(dtb.get("connection"));
		}
		return MongoClientIns._mongoClientIns;
	}
}
