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
    user.setId(UUID.randomUUID());

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
  public List<User> FindUser(String SearchKey) throws Exception {
    HashMap<String, String> database = PropUtils.GetMongoDBChat();

    MongoClient client = MongoClients.create(database.get("connection"));
    MongoDatabase dtb = client.getDatabase(database.get("database"));

    String search = String.format("/.*%s.*/", SearchKey);

    List<User> result = new ArrayList<>();
    FindIterable<Document> cursor = dtb
      .getCollection("user")
      .find(new Document("username", search))
      .limit(15);

    for (Document doc : cursor) {
      result.add(new Gson().fromJson(doc.toJson(), User.class));
    }
    return result;
  }

  @Override
  public User GetUser(String UserName) throws Exception {
    HashMap<String, String> database = PropUtils.GetMongoDBChat();

    MongoClient client = MongoClients.create(database.get("connection"));
    MongoDatabase dtb = client.getDatabase(database.get("database"));

    Document filter = new Document("username", UserName);
    FindIterable<Document> cursor = dtb.getCollection("user").find(filter);

    User result = new Gson().fromJson(cursor.first().toJson(), User.class);

    return result;
  }
}
