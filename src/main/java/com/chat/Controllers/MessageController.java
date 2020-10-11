package com.chat.Controllers;

import com.chat.App;
import com.chat.Models.Message;
import com.chat.Services.MessageService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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
    Optional<Object> res = this.messageService.AddMessage(message);

    if (res.isEmpty()) {
      simpMessagingTemplate.convertAndSend(
        String.format("/room/%s", message.getSender()),
        new Response<Object>("", ErrorType.INTERNAL_SERVER_ERROR)
      );
    } else {
      simpMessagingTemplate.convertAndSend(
        String.format("/room/%s", message.getReceiver()),
        message
      );
      simpMessagingTemplate.convertAndSend(
        String.format("/room/%s", message.getSender()),
        new Response<Object>("", ErrorType.OK)
      );
    }
  }
}
