package com.chat;

import com.chat.LogManager.LogUtils;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App {
  public static final String API = "/api";
  public static final String MESSAGE_SERVICE_PREFIX = "/service";
  public static final String MESSAGE_SERVICE_SOCKET = "/socket-service";

  public static void main(String[] args) {
    SpringApplication.run(App.class, args);

    LogUtils.LogInfo(String.format("\n==========\n%s\n==========\n", "App is started"), null);
  }
}
