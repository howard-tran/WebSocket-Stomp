package com.chat.LogManager;

import org.apache.log4j.Logger;

public class LogUtils {
  private static Logger logger;

  public static void LogError(Object message, Exception error) {
    if (logger == null) {
      logger = Logger.getLogger(LogUtils.class);
    }
    logger.error(message, error);
  }

  public static void LogInfo(Object message, Exception error) {
    if (logger == null) {
      logger = Logger.getLogger(LogUtils.class);
    }
    logger.info(message, error);
  }
}
