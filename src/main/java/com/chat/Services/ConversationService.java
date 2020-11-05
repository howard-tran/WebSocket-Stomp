package com.chat.Services;

import com.chat.LogManager.LogUtils;
import com.chat.Models.Conversation;
import com.chat.Models.User;
import com.chat.PropertyManager.DatabaseSupplier;
import com.chat.Repository.IConversationDAO;
import com.google.gson.Gson;
import com.mongodb.client.FindIterable;
import java.io.Console;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.ZoneIdEditor;
import org.springframework.stereotype.Service;

@Service
public class ConversationService {
  private IConversationDAO conversationDao;

  @Autowired
  ConversationService(
    @Qualifier(
      DatabaseSupplier.MongoDB.Chat.Conversation
    ) IConversationDAO conversationDao
  ) {
    this.conversationDao = conversationDao;
  }

  public Optional<Boolean> AddConversation(Conversation conversation) {
    try {
      ZoneId zoneId = ZoneId.systemDefault();

      conversation.setId(UUID.randomUUID());
      conversation.setUnixTime(
        String.valueOf(LocalDateTime.now().atZone(zoneId).toEpochSecond())
      );
      conversationDao.InsertConversation(conversation);

      return Optional.of(true);
      //
    } catch (Exception e) {
      RuntimeException exception = new RuntimeException(e);

      LogUtils.LogError("[ERROR]", exception);

      return Optional.empty();
    }
  }

  public Optional<Boolean> CheckAvailableConversation(
    Conversation conversation
  ) {
    try {
      UserService userServiceIns = UserService.GetInstance();

      if (
        userServiceIns.CheckAvailableUserName(conversation.getSender()).get() ||
        userServiceIns
          .CheckAvailableUserName(conversation.getReceiver())
          .get() ||
        conversation.getSender().equals(conversation.getReceiver())
      ) {
        return Optional.of(false);
      }

      List<Conversation> list = conversationDao.GetConversation(
        conversation.getSender(),
        conversation.getReceiver()
      );

      return Optional.of((list.size() == 0));
      //x
    } catch (Exception e) {
      RuntimeException exception = new RuntimeException(e);

      LogUtils.LogError("[ERROR]", exception);

      return Optional.empty();
    }
  }

  /**
   * Get 15 messages at a time
   * @param {User} conversation
   * @param {int} index (index of a collection, collection start at 0)
   * @return List<Conversation>
   */
  public Optional<List<Conversation>> GetConversation(User user, int index) {
    final int conversationCountEach = 15;
    try {
      List<Conversation> conversations = conversationDao.GetUserConversation(
        user
      );

      if (index >= conversations.size()) {
        return Optional.of(new ArrayList<Conversation>());
        //
      } else {
        if (index + conversationCountEach >= conversations.size()) {
          return Optional.of(
            conversations.subList(index, conversations.size())
          );
          //
        } else {
          return Optional.of(
            conversations.subList(index, index + conversationCountEach)
          );
        }
      }
      //
    } catch (Exception e) {
      RuntimeException exception = new RuntimeException(e);

      LogUtils.LogError("[ERROR]", exception);

      return Optional.empty();
    }
  }
}
