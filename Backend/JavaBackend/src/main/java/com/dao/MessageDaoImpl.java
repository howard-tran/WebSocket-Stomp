package com.dao;

import com.google.gson.Gson;
import com.helper.DatabaseSupplier;
import com.helper.PropertyHelper;
import com.model.Conversation;
import com.model.Message;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

@Repository(DatabaseSupplier.MongoDB.BetStore.Message)
public class MessageDaoImpl implements IMessageDao {

  @Override
  public List<Message> getAllMessage() throws Exception {
    return (ArrayList<Message>) this.run(
        PropertyHelper.GetMongoDBChat(),
        "Message",
        collection -> {
          List<Message> res = new ArrayList<>();
          for (Document doc : collection.find()) {
            res.add((Message) this.parseWithId(doc, Message.class));
          }
          return res;
        }
      );
  }

  @Override
  public List<Message> getMessage(String senderId, String receiverId) throws Exception {
    return (ArrayList<Message>) this.run(
        PropertyHelper.GetMongoDBChat(),
        "Message",
        collection -> {
          Document filter = Document.parse(
            String.format(
              "{$and: [{senderId: \"%s\"}, {receiverId: \"%s\"}]}",
              senderId,
              receiverId
            )
          );
          List<Message> res = new ArrayList<>();
          for (Document doc : collection.find(filter)) {
            res.add((Message) this.parseWithId(doc, Message.class));
          }
          return res;
        }
      );
  }

  private Document createFilterConversation(String a, String b) {
    return Document.parse(
      String.format("{$and: [{senderId: \"%s\"}, {receiverId: \"%s\"}]}", a, b)
    );
  }

  @Override
  public List<Message> getMessageByConversation(Conversation conversation) throws Exception {
    return (ArrayList<Message>) this.run(
        PropertyHelper.GetMongoDBChat(),
        "Message",
        collection -> {
          Document con1 =
            this.createFilterConversation(conversation.getSenderId(), conversation.getReceiverId());
          Document con2 =
            this.createFilterConversation(conversation.getReceiverId(), conversation.getSenderId());
          Document filter = Document.parse(
            String.format("{$or: [%s, %s]}", con1.toJson(), con2.toJson())
          );

          List<Message> res = new ArrayList<>();
          for (Document doc : collection.find(filter).sort(new Document("_id", 1))) {
            res.add((Message) this.parseWithId(doc, Message.class));
          }
          return res;
        }
      );
  }

  @Override
  public List<Message> getMessageById(String _id) throws Exception {
    return (ArrayList<Message>) this.run(
        PropertyHelper.GetMongoDBChat(),
        "Message",
        collection -> {
          List<Message> res = new ArrayList<>();
          for (Document doc : collection.find(new Document("_id", new ObjectId(_id)))) {
            res.add((Message) this.parseWithId(doc, Message.class));
          }
          return res;
        }
      );
  }

  @Override
  public void insertMessage(Message data) throws Exception {
    this.run(
        PropertyHelper.GetMongoDBChat(),
        "Message",
        collection -> {
          String json = new Gson().toJson(data); 
          collection.insertOne(Document.parse(json));

          return null;
        }
      );
  }
}
