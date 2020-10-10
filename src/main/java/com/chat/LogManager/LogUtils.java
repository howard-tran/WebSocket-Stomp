package com.chat.LogManager;

import org.apache.log4j.Logger;

public class LogUtils {
  private static Logger logger = Logger.getLogger(LogUtils.class);

  public static void AddLog(String message, Exception error) {
    logger.error(message, error);
  }
}
