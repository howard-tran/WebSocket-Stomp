package com.chat.LogManager;

import org.apache.log4j.Logger;

public class LogUtils {
  private static Logger logger = Logger.getLogger(LogUtils.class);

  public static void LogError(Object message, Exception error) {
    logger.error(message, error);
  }

  public static void LogInfo(Object message, Exception error) {
    logger.info(message, error);
  }
}
