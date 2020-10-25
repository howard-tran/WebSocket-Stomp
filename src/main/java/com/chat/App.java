package com.chat;

import com.chat.LogManager.LogUtils;
import com.chat.Models.LoginSession;
import com.chat.Models.User;
import com.chat.Repository.LoginSessionDAO;
import java.io.Console;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.TimerTask;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import javax.swing.Spring;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App {
  public static final String API = "/api";
  public static final String MESSAGE_SERVICE_PREFIX = "/service";
  public static final String MESSAGE_SERVICE_SOCKET = "/socket-service";

  public static void main(String[] args) {
    PropertyConfigurator.configure(LogUtils.class.getResource("Log4J.properties").getFile());

    SpringApplication.run(App.class, args);

    LogUtils.LogInfo(String.format("\n==========\n%s\n==========\n", "App is started"), null);
  }
}
