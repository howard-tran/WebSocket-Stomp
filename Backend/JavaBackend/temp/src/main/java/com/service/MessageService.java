package com.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.dao.IConversationDao;
import com.dao.IMessageDao;
import com.helper.DatabaseSupplier;
import com.helper.VoidObject;
import com.model.Conversation;
import com.model.Message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class MessageService implements LogService {
  private IMessageDao messageDao;
  private IConversationDao conversationDao;

  @Autowired
  MessageService(@Qualifier(DatabaseSupplier.MongoDB.BetStore.Message) IMessageDao messageDao,
      @Qualifier(DatabaseSupplier.MongoDB.BetStore.Conversation) IConversationDao conversationDao) {
    this.messageDao = messageDao;
    this.conversationDao = conversationDao;
  }

  private Boolean checkConversation(String senderId, String receiverId) throws Exception {
    return this.conversationDao.getConversation(senderId, receiverId).size() > 0;
  }

  public Optional<Optional<String>> addMessage(Message message) {
    return this.run(() -> {
      if (checkConversation(message.getSenderId(), message.getReceiverId())) {
        return Optional.of(this.messageDao.insertMessage(message));
      }
      return Optional.empty();
    });
  }

  private Optional<VoidObject> deleteMessage(String _id) {
    return this.run(() -> {
      this.messageDao.deleteMessage(_id);

      return VoidObject.create();
    });
  }

  public Optional<VoidObject> deleteConversationMessage(Conversation conversation) {
    return this.run(() -> {
      var listMessage = this.getMessage(conversation).get();

      for (int i = 0; i < listMessage.size(); i++) {
        this.deleteMessage(listMessage.get(i).get_id().toString()); 
      }
      return VoidObject.create();
    });
  }

  private Optional<List<Message>> getMessage(Conversation conversation) {
    return this.run(() -> {
      return this.messageDao.getMessageByConversation(conversation); 
    }); 
  }

  /**
   * get 15 message each
   * 
   * @param conversation
   * @return
   */
  public Optional<List<Message>> getMessage(Conversation conversation, int index) {
    return this.run(() -> {
      var list = this.messageDao.getMessageByConversation(conversation);

      if (index < list.size()) {

        if (index + 15 <= list.size()) {
          return list.subList(index, index + 15);
        } else
          return list.subList(index, list.size());
      }
      return new ArrayList<Message>();
    });
  }
}
