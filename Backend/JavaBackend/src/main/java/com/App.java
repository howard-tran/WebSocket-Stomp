package com;

import com.helper.LogUtils;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App {

  public static void main(String[] args) {
    SpringApplication.run(App.class, args);

    LogUtils.LogInfo("\n========== Service Restarted ==========\n", null);
  }
}
