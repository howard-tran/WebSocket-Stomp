package com.service;

import com.dao.IConversationDao;
import com.helper.DatabaseSupplier;
import com.model.Conversation;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ConversationService implements LogService {
  private IConversationDao conversationDao;

  @Autowired
  ConversationService(
    @Qualifier(DatabaseSupplier.MongoDB.BetStore.Conversation) IConversationDao conversationDao
  ) {
    this.conversationDao = conversationDao;
  }

  Optional<Boolean> addConversation(Conversation conversation) {
    return (Optional<Boolean>) this.run(
        () -> {
          if (
            this.conversationDao.getConversation(
              conversation.getSenderId(), conversation.getReceiverId()
            ).size() > 0
          ) return Optional.of(false); 
          else {
            this.conversationDao.insertConversation(conversation);
          }
          return Optional.of(true);
        }
      );
  }
}
