package com.dao;

import com.helper.PropertyHelper;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.connection.ClusterSettings;

import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

public class MongoClientIns {
  private static MongoClient _mongoClientIns;

  public static MongoClient GetMongoClient() throws Exception {
    HashMap<String, String> dtb = PropertyHelper.GetMongoDBChat();

    if (MongoClientIns._mongoClientIns == null) {

      MongoClientSettings settings = MongoClientSettings.builder()
        .applyConnectionString(new ConnectionString(dtb.get("connection")))
        .applyToClusterSettings(cluster -> {
          cluster.serverSelectionTimeout(500, TimeUnit.MILLISECONDS);
        }).build();
      MongoClientIns._mongoClientIns = MongoClients.create(settings);
    }
    return MongoClientIns._mongoClientIns;
  }
}
