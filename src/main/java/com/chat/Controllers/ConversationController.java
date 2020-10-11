package com.chat.Controllers;

import com.chat.App;
import com.chat.Models.Conversation;
import com.chat.Models.User;
import com.chat.Services.ConversationService;
import com.jayway.jsonpath.Option;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(App.API + "/conversation")
@RestController
public class ConversationController {
  private ConversationService conversationService;

  @Autowired
  ConversationController(ConversationService conversationnService) {
    this.conversationService = conversationnService;
  }

  @PostMapping("/add")
  public Response<Object> AddConversation(
    @RequestBody Conversation conversation
  ) {
    Optional<Object> res = conversationService.AddConversation(conversation);

    if (res.isEmpty()) {
      return new Response<Object>("", ErrorType.INTERNAL_SERVER_ERROR);
    } else {
      return new Response<Object>(res.get(), ErrorType.OK);
    }
  }

  @GetMapping("/get")
  public Response<Object> GetConversation(
    @RequestBody User user,
    @RequestParam(name = "index", required = true) int index
  ) {
    Optional<List<Conversation>> res = conversationService.GetConversation(
      user,
      index
    );

    if (res.isEmpty()) {
      return new Response<Object>("", ErrorType.INTERNAL_SERVER_ERROR);
    } else {
      return new Response<Object>(res.get(), ErrorType.OK);
    }
  }
}
