package com.helper;

public class SocketService {

	public class ChatSupplier {
		/**
		 * socket interface
		 */
		public static final String prefix = "/chat-socket";
		public static final String appPrefix = "/chat";

		/**
		 * socket services
		 */
		public static final String messageService = appPrefix + "/message";
		public static final String roomService = appPrefix + "/room";
	}

	public class NotificationSupplier {
		/**
		 * socket interface
		 */
		public static final String prefix = "/notification-socket";
		public static final String appPrefix = "/notification";

		/**
		 * socket services
		 */
		public static final String messageService = appPrefix + "/message";
	}
}
