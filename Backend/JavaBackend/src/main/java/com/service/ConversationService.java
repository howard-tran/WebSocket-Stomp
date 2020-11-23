package com.service;

import com.dao.IConversationDao;
import com.helper.DatabaseSupplier;
import com.helper.Tuple2;
import com.helper.VoidObject;
import com.model.Conversation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ConversationService implements LogService {
  private IConversationDao conversationDao;

  @Autowired
  ConversationService(@Qualifier(DatabaseSupplier.MongoDB.BetStore.Conversation) IConversationDao conversationDao) {
    this.conversationDao = conversationDao;
  }

  public Optional<Tuple2<String, String>> addConversation(Conversation conversation) {
    return this.run(() -> {
      if (this.conversationDao.getConversation(conversation.getSenderId(), conversation.getReceiverId()).size() > 0)
        return new Tuple2<String, String>(null, null);
      else {
        var conversationId1 = this.conversationDao.insertConversation(conversation);
        var conversationId2 = this.conversationDao.insertConversation(conversation.reverse());

        return new Tuple2<String, String>(conversationId1, conversationId2);
      }
    });
  }

  /**
   * Get 15 conversation each time
   * 
   * @param senderId
   * @param index
   * @return
   */
  public Optional<List<Conversation>> getConversation(String senderId, int index) {
    return this.run(() -> {
      var list = this.conversationDao.getConversation(senderId);

      if (index < list.size()) {
        if (index + 15 <= list.size()) {
          return list.subList(index, index + 15);
        } else
          return list.subList(index, list.size());
      }
      return new ArrayList<Conversation>();
    });
  }

  public Optional<VoidObject> deleteConversation(String _id) {
    return this.run(() -> {
      this.conversationDao.deleteConversation(_id);

      return VoidObject.create();
    });
  }

  public Optional<VoidObject> deleteConversation(Conversation conversation) {
    return this.run(() -> {
      var con1 = this.conversationDao.getConversation(conversation.getSenderId(), conversation.getReceiverId()).get(0);
      var con2 = this.conversationDao.getConversation(conversation.getReceiverId(), conversation.getSenderId()).get(0);

      this.conversationDao.deleteConversation(con1.get_id().toString());
      this.conversationDao.deleteConversation(con2.get_id().toString());

      return VoidObject.create();
    });
  }
}
