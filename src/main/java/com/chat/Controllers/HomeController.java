package com.chat.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/")
@Controller
public class HomeController {

  @RequestMapping("/")
  String GetLoginForm() {
    return "home";
  }
}
