package com.chat.Controllers;

import com.chat.App;
import com.chat.Models.Conversation;
import com.chat.Models.Message;
import com.chat.Services.MessageService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(App.API + "/message")
@RestController
public class MessageController {
  private MessageService messageService;

  @Autowired
  private SimpMessagingTemplate simpMessagingTemplate;

  @Autowired
  MessageController(MessageService messageService) {
    this.messageService = messageService;
  }

  @MessageMapping("/chat") // not affect by @RequestMapping, prefix = ApplicationDestinationPrefixes
  public void SendMessage(@Payload Message message) {
    Optional<Boolean> res = this.messageService.AddMessage(message);

    if (res.isEmpty()) {
      simpMessagingTemplate.convertAndSend(
        String.format("/room/%s", message.getSender()),
        new Response<Object>("", ErrorType.INTERNAL_SERVER_ERROR)
      );
    } else {
      simpMessagingTemplate.convertAndSend(
        String.format("/room/%s", message.getSender()),
        new Response<Object>(message, ErrorType.OK)
      );

      simpMessagingTemplate.convertAndSend(
        String.format("/room/%s", message.getReceiver()),
        new Response<Object>(message, ErrorType.OK)
      );
    }
  }

  @PostMapping("/chat/post-test")
  public Response<Object> PostMessage(@RequestBody Message message) {
    Optional<Boolean> res = this.messageService.AddMessage(message);

    if (res.isEmpty()) {
      return new Response<Object>("", ErrorType.INTERNAL_SERVER_ERROR);
    } else {
      return new Response<Object>("", ErrorType.OK);
    }
  }

  @GetMapping("/get")
  public Response<Object> GetMessage(
    @RequestParam(name = "sender", required = true) String sender,
    @RequestParam(name = "receiver", required = true) String receiver,
    @RequestParam(name = "index", required = true) int index
  ) {
    Optional<List<Message>> res = messageService.GetMessageByIndex(
      new Conversation(sender, receiver),
      index
    );

    if (res.isEmpty()) {
      return new Response<Object>("", ErrorType.INTERNAL_SERVER_ERROR);
    } else {
      return new Response<Object>(res.get(), ErrorType.OK);
    }
  }
}
