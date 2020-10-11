package com.chat.DAO.MongoDB;

import com.chat.DAO.IUserDAO;
import com.chat.LogManager.LogUtils;
import com.chat.Models.User;
import com.chat.PropertyManager.DatabaseSupplier;
import com.chat.PropertyManager.PropUtils;
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

@Repository(DatabaseSupplier.MongoDB.Chat.User)
public class UserImpl implements IUserDAO {

  @Override
  public void InsertUser(User user) throws Exception {
    HashMap<String, String> database = PropUtils.GetMongoDBChat();

    LogUtils.LogInfo(database.get("connection"), null);

    MongoClient client = MongoClients.create(database.get("connection"));
    MongoDatabase dtb = client.getDatabase(database.get("database"));

    String objJson = new Gson().toJson(user);
    dtb.getCollection("user").insertOne(new Document().parse(objJson));
  }

  @Override
  public void InsertManyUser(List<User> userList) throws Exception {
    for (int i = 0; i < userList.size(); i++) {
      User t = userList.get(i);
      t.setId(UUID.randomUUID());

      userList.set(i, t);
    }

    HashMap<String, String> database = PropUtils.GetMongoDBChat();

    MongoClient client = MongoClients.create(database.get("connection"));
    MongoDatabase dtb = client.getDatabase(database.get("database"));

    List<Document> listObjJson = new ArrayList<>();
    for (int i = 0; i < userList.size(); i++) {
      String objJson = new Gson().toJson(userList.get(i));

      listObjJson.add(new Document().parse(objJson));
    }
    dtb.getCollection("user").insertMany(listObjJson);
  }

  @Override
  public void DeleteUser(UUID id) throws Exception {
    HashMap<String, String> database = PropUtils.GetMongoDBChat();

    MongoClient client = MongoClients.create(database.get("connection"));
    MongoDatabase dtb = client.getDatabase(database.get("database"));

    dtb.getCollection("user").deleteOne(new Document("id", id.toString()));
  }

  @Override
  public List<User> FindUser(String searchKey) throws Exception {
    HashMap<String, String> database = PropUtils.GetMongoDBChat();

    MongoClient client = MongoClients.create(database.get("connection"));
    MongoDatabase dtb = client.getDatabase(database.get("database"));

    List<User> result = new ArrayList<>();
    Document filter = Document.parse(
      String.format("{userName: {$regex: /^%s/, $options: 'i'}}", searchKey)
    );
    FindIterable<Document> cursor = dtb
      .getCollection("user")
      .find(filter)
      .limit(15);

    for (Document doc : cursor) {
      result.add(new Gson().fromJson(doc.toJson(), User.class));
    }
    return result;
  }

  @Override
  public User GetUser(String userName) throws Exception {
    HashMap<String, String> database = PropUtils.GetMongoDBChat();

    MongoClient client = MongoClients.create(database.get("connection"));
    MongoDatabase dtb = client.getDatabase(database.get("database"));

    Document filter = new Document("userName", userName);
    FindIterable<Document> cursor = dtb.getCollection("user").find(filter);

    User result = new Gson().fromJson(cursor.first().toJson(), User.class);

    return result;
  }
}
