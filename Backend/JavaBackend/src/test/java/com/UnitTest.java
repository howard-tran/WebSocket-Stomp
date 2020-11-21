package com;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.dao.ConversationDaoImpl;
import com.dao.IConversationDao;
import com.dao.IMessageDao;
import com.helper.DatabaseSupplier;
import com.helper.PropertyHelper;
import com.helper.helperTest;
import com.model.Conversation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UnitTest extends testCasePrint {
  private IConversationDao conversationDao;
  private IMessageDao messageDao; 

  @Autowired
  public void setConversationDao(
    @Qualifier(DatabaseSupplier.MongoDB.BetStore.Conversation) IConversationDao conversationDao,
    @Qualifier(DatabaseSupplier.MongoDB.BetStore.Message) IMessageDao messageDao
  ) {
    this.conversationDao = conversationDao;
    this.messageDao = messageDao; 
  }

  // ========== test conversationDao ==========

  @Test
  public void testCase1() throws Exception {
    this.run(
      () -> {
        HashMap<String, String> hash = PropertyHelper.GetMongoDBChat();

        System.out.println(hash.get("connection"));
        System.out.println(hash.get("database"));
        System.out.println(hash.get("host"));
        System.out.println(hash.get("port"));

        return null; 
      }
    );
  }

  @Test
  public void testCase2() throws Exception {
    this.run(
      () -> {
        System.out.println(DatabaseSupplier.MongoDB.BetStore.Conversation);
        System.out.println(DatabaseSupplier.MongoDB.BetStore.Message);

        return null; 
      }
    );    
  }

  @Test
  public void testCase3() throws Exception {
    this.run(
      () -> {
        assertTrue(this.conversationDao != null);
        return null; 
      }
    );
  }

  /**
   * insert conversation
   * @throws Exception
   */
  @Test
  public void testCase4() throws Exception {
    this.run(
      () -> {
        List<Conversation> list = new ArrayList<>();
        list.add(new Conversation("abcd", "abcd1"));
        list.add(new Conversation("abd", "abcd1"));

        for (int i = 0; i < list.size(); i++) {
          this.conversationDao.insertConversation(list.get(i));
        }

        return null; 
      }
    );
  }

  /**
   * get conversation + check mapping + check delete
   * @throws Exception
   */
  @Test
  public void testCase5() throws Exception {
    this.run(
      () -> {
        List<Conversation> list = this.conversationDao.getAllConversation();

        for (int i = 0; i < list.size(); i++) {
          System.out.println(list.get(i).toString());
    
          this.conversationDao.deleteConversation(list.get(i).get_id().toString());
        }
        return null; 
      }
    );
  }

  // ========== test messageDao ==========
  
  @Test
  public void testCase6() throws Exception {
    this.run(
      () -> {
        
        return null; 
      }
    );
  }
}
