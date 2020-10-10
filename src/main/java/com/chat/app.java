package com.chat;

import java.util.ArrayList;
import java.util.List;

import javax.swing.Spring;

import com.chat.LogManager.LogUtils;

import org.apache.log4j.PropertyConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class app {
  public static void main(String args) {
    PropertyConfigurator.configure(LogUtils.class.getResource("Log4J.properties").getFile());
    SpringApplication.run(app.class, args);
  }
}