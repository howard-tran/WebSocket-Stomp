package com.chat.PropertyManager;

import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;

import com.chat.LogManager.LogUtils;

public class PropUtils {
  public static Optional<String> GetProperty(String property) {
    Properties propGet = new Properties();

    InputStream inputStream = PropUtils.class.getResourceAsStream("project.properties");

    try {
      propGet.load(inputStream);
    } catch (Exception e) {
      LogUtils.AddLog("[ERROR]", e);
      throw new RuntimeException(e);
    }

    if (propGet.containsKey(property)) {
      return Optional.of(propGet.getProperty(property));
    }
    return Optional.empty();
  }
}
