package com.controller;

import java.util.Optional;

import com.App;
import com.helper.Tuple2;
import com.model.Conversation;
import com.service.ConversationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(App.service + "/conversation")
public class ConversationController  {
  private ConversationService conversationService; 

  // @Autowired
  // private SimpMessagingTemplate simpleMessageTemplate;

  @Autowired
  ConversationController(ConversationService conversationService) {
    this.conversationService = conversationService;
  }

  @PostMapping("/add")
  private Response<Object> addConversation(@RequestBody Conversation conversation) {
    var res = this.conversationService.addConversation(conversation);

    if (res.isEmpty()) {
      return ResponseHandler.error();
    } else {
      return ResponseHandler.ok(res.get());
    }
  }

  @PostMapping("/delete")
  private Response<Object> deleteConversation(@RequestBody Conversation conversation) {
    var res = this.conversationService.deleteConversation(conversation);

    if (res.isEmpty()) {
      return ResponseHandler.error(); 
    } else {
      return ResponseHandler.ok();
    }
  }

  
}