package com;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.dao.ConversationDaoImpl;
import com.dao.IConversationDao;
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
public class UnitTest {
  private IConversationDao conversationDao;

  @Autowired
  public void setConversationDao(
    @Qualifier(DatabaseSupplier.MongoDB.BetStore.Conversation) IConversationDao conversationDao
  ) {
    this.conversationDao = conversationDao;
  }

  @Test
  public void testCase1() throws Exception {
    System.out.println("\n============== test case 1 ==============\n");
    HashMap<String, String> hash = PropertyHelper.GetMongoDBChat();

    System.out.println(hash.get("connection"));
    System.out.println(hash.get("database"));
    System.out.println(hash.get("host"));
    System.out.println(hash.get("port"));
  }

  @Test
  public void testCase2() throws Exception {
    System.out.println("\n============== test case 2 ==============\n");
    System.out.println(DatabaseSupplier.MongoDB.BetStore.Conversation);
    System.out.println(DatabaseSupplier.MongoDB.BetStore.Message);
  }

  @Test
  public void testCase3() throws Exception {
    System.out.println("\n============== test case 3 ==============\n");

    assertTrue(this.conversationDao != null);
  }

  /**
   * insert conversation
   * @throws Exception
   */
  @Test
  public void testCase4() throws Exception {
    System.out.println("\n============== test case 4 ==============\n");

    List<Conversation> list = new ArrayList<>();
    list.add(new Conversation("abcd", "abcd1"));
    list.add(new Conversation("abd", "abcd1"));

    for (int i = 0; i < list.size(); i++) {
      this.conversationDao.insertConversation(list.get(i));
    }
  }

  /**
   * get conversation + check mapping + check delete
   * @throws Exception
   */
  @Test
  public void testCase5() throws Exception {
    System.out.println("\n============== test case 5 ==============\n");

    List<Conversation> list = this.conversationDao.getAllConversation();

    for (int i = 0; i < list.size(); i++) {
      System.out.println(list.get(i).toString());

      this.conversationDao.deleteConversation(list.get(i).get_id().toString());
    }
  }
}
