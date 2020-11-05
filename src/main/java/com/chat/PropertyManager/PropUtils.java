package com.chat.PropertyManager;

import com.chat.LogManager.LogUtils;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

public class PropUtils {

  public static Optional<String> GetProperty(String property) throws Exception {
    Properties propGet = new Properties();

    InputStream inputStream =
      PropUtils.class.getResourceAsStream("project.properties");

    propGet.load(inputStream);

    if (propGet.containsKey(property)) {
      return Optional.of(propGet.getProperty(property));
    }
    return Optional.empty();
  }

  public static HashMap<String, String> GetMongoDBChat() throws Exception {
    HashMap<String, String> res = new HashMap<String, String>();
    res.put("connection", GetProperty("develop-mongodb-chat-connection").get());
    res.put("database", "chat");

    return res;
  }
}
