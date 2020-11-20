package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App {

  /**
   * api service
   */
  public static final String api = "/api"; 

  /**
   * number of supported socket
   */
  public static final String[] socket = {
    "chat-socket"
  }; 

  public static void main(String[] args) {
    SpringApplication.run(App.class, args);
  }
}
