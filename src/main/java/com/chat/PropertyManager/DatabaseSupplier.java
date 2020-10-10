package com.chat.PropertyManager;

import javax.xml.stream.events.Namespace;

public class DatabaseSupplier {

  public static class MongoDB {

    public static class Chat {
      private static final String prefix = "MongoDB.Chat.";

      public static final String User = prefix + "User";
      public static final String Message = prefix + "Message";
      public static final String Conversation = prefix + "Conversation";
    }
  }

  public static class PostgreSQL {

    public static class Chat {
      private static final String prefix = "PostgreSQL.Chat.";

      public static final String User = prefix + "User";
      public static final String Message = prefix + "Message";
      public static final String Conversation = prefix + "Conversation";
    }
  }
}
