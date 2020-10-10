package com.chat.DAO.MongoDB;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.chat.DAO.UserDAO;
import com.chat.Models.User;
import com.chat.PropertyManager.PropUtils;
import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;

public class UserImpl implements UserDAO {
  @Override
  public void InsertUser(User user) throws Exception {

    user.setId(UUID.randomUUID());

    Optional<String> connectionString = PropUtils.GetProperty("mongodb-connection");

    MongoClient client = new MongoClient(connectionString.get());
    MongoDatabase dtb = client.getDatabase(PropUtils.GetProperty("mongodb-db").get());

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

    Optional<String> connectionString = PropUtils.GetProperty("mongodb-connection");

    MongoClient client = new MongoClient(connectionString.get());
    MongoDatabase dtb = client.getDatabase(PropUtils.GetProperty("mongodb-db").get());

    List<Document> listObjJson = new ArrayList<>();
    for (int i = 0; i < userList.size(); i++) {
      String objJson = new Gson().toJson(userList.get(i));

      listObjJson.add(new Document().parse(objJson));
    }
    dtb.getCollection("user").insertMany(listObjJson);
  }

  @Override
  public void DeleteUser(UUID id) throws Exception {
    Optional<String> connectionString = PropUtils.GetProperty("mongodb-connection");

    MongoClient client = new MongoClient(connectionString.get());
    MongoDatabase dtb = client.getDatabase(PropUtils.GetProperty("mongodb-db").get());

    dtb.getCollection("user").deleteOne(new Document("id", id.toString()));
  }

  @Override
  public List<User> FindUser(String SearchKey) throws Exception {
    Optional<String> connectionString = PropUtils.GetProperty("mongodb-connection");

    MongoClient client = new MongoClient(connectionString.get());
    MongoDatabase dtb = client.getDatabase(PropUtils.GetProperty("mongodb-db").get());

    String search = String.format("/.*%s.*/", SearchKey);

    List<User> result = new ArrayList<>();
    FindIterable<Document> cursor = dtb.getCollection("user").find(new Document("username", search)).limit(15);

    for (Document doc : cursor) {
      result.add(new Gson().fromJson(doc.toJson(), User.class));
    }
    return result;
  }

  @Override
  public User GetUser(String UserName) throws Exception {
    Optional<String> connectionString = PropUtils.GetProperty("mongodb-connection");

    MongoClient client = new MongoClient(connectionString.get());
    MongoDatabase dtb = client.getDatabase(PropUtils.GetProperty("mongodb-db").get());

    Document filter = new Document("username", UserName);
    FindIterable<Document> cursor = dtb.getCollection("user").find(filter);

    User result = new Gson().fromJson(cursor.first().toJson(), User.class);

    return result;
  }
}
