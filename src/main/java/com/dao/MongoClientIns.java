package com.dao;

import com.helper.PropertyHelper;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;


import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class MongoClientIns {
  private static MongoClient _mongoClientIns;

  public static MongoClient GetMongoClient() throws Exception {
    HashMap<String, String> dtb = PropertyHelper.getMongoDBChat();

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
