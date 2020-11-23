package com;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.model.Conversation;
import com.service.ConversationService;
import com.service.MessageService;
import com.helper.Tuple2;
import com.model.Message;
import com.model.MessageContentType;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UnitTest2 extends testCasePrint {
  @Autowired
  private MessageService messageService;

  @Autowired
	private ConversationService conversationService;

  @Test
  public void testCase1() throws Exception {
    this.run(() -> {
      List<Integer> list = new ArrayList<>(); 
      list.add(12);
      list.add(1);
      list.add(5);
      list.add(8);
      list.add(9);
      list.add(2);

      List<Integer> sub = list.subList(0, 5); 
      sub.forEach(num -> {
        System.out.print(num + " ");
      });

      return null;
    }, "test java subList");
  }

  @Test 
  public void testCase2() throws Exception {
    this.run(() -> {
      List<Conversation> list = new ArrayList<>(); 
      for (int i = 0; i < 100; i++) {
        list.add(new Conversation("abcd", 
          UUID.randomUUID().toString().substring(0, 4)));
        
        this.conversationService.addConversation(list.get(i)).get();
      }

      List<Conversation> con = this.conversationService.getConversation("abcd", 0).get(); 

      var count = new Object() {
        int data = 0;
      };
      
      con.forEach(x -> {
        System.out.println(x.toString());
        count.data++;
      });
      System.out.println(count.data);

      for (int i = 0; i < list.size(); i++) {
        this.conversationService.deleteConversation(list.get(i));
      }
      return null; 
    },
    "test service get conversation with index");
  }

  @Test
  public void testCase3() throws Exception {
    this.run(() -> {
      Conversation con = new Conversation("abcd1234", "abcd123");

      Tuple2<String, String> tuple = this.conversationService.addConversation(con).get();

      if (this.conversationService.deleteConversation(con).isEmpty()) {
        this.logErrorToTerminal("delete conversation is empty");
      }

      List<Message> mList = new ArrayList<>();
      for (int i = 0; i < 100; i++) {
        mList.add(new Message("abcd1234", "abcd123", UUID.randomUUID().toString(), "", 
          MessageContentType.CONTENT_NONE));
        
        this.messageService.addMessage(mList.get(i)); 
      }

      List<Message> messGet = this.messageService.getMessage(con, 0).get();
      int count = 0;
      for (int i = 0; i < messGet.size(); i++) {
        System.out.println(messGet.get(i).toString());
        count++;
      }
      System.out.println(count);

      return null;
    }, 
    "test service add + delete conversation",
    "test service add + get message from deleted conversation"
    );
  }

  @Test
  public void testCase4() throws Exception {
    this.run(() -> {
      Conversation con = new Conversation("abcd1234", "abcd123");

      Tuple2<String, String> tuple = this.conversationService.addConversation(con).get();

      List<Message> mList = new ArrayList<>();
      for (int i = 0; i < 50; i++) {
        mList.add(new Message("abcd1234", "abcd123", UUID.randomUUID().toString(), "", 
          MessageContentType.CONTENT_NONE));
        
        String messId = this.messageService.addMessage(mList.get(i)).get().get(); 
        mList.get(i).set_id(new ObjectId(messId));
      }
      for (int i = 50; i < 60; i++) {
        mList.add(new Message("abcd123", "abcd1234", UUID.randomUUID().toString(), "", 
          MessageContentType.CONTENT_NONE));
        
        String messId = this.messageService.addMessage(mList.get(i)).get().get(); 
        mList.get(i).set_id(new ObjectId(messId));
      }

      List<Message> messGet = this.messageService.getMessage(con, 0).get();
      int count = 0;
      for (int i = 0; i < messGet.size(); i++) {
        System.out.println(messGet.get(i).toString());
        count++;
      }
      System.out.println(count);

      this.messageService.deleteConversationMessage(con);
      this.conversationService.deleteConversation(con);

      return null;
    }, 
    "test service get message with index + delete conversation message"
    );
  }

  @Test
  public void testCase5() throws Exception {
    this.run(() -> {
      var t = Optional.of(Optional.empty()); 
      
      if (t.isEmpty()) {
        this.logErrorToTerminal("optional libs error");
      }
      return null;
    }, 
    "optional libs test");
  }
}
