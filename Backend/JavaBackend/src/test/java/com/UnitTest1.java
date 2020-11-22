package com;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.dao.ConversationDaoImpl;
import com.dao.IConversationDao;
import com.dao.IMessageDao;
import com.helper.DatabaseSupplier;
import com.helper.PropertyHelper;
import com.helper.helperTest;
import com.model.Conversation;
import com.model.Message;
import com.model.MessageContentType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UnitTest1 extends testCasePrint {
	private IConversationDao conversationDao;
	private IMessageDao messageDao;

	@Autowired
	public void setConversationDao(
			@Qualifier(DatabaseSupplier.MongoDB.BetStore.Conversation) IConversationDao conversationDao,
			@Qualifier(DatabaseSupplier.MongoDB.BetStore.Message) IMessageDao messageDao) {
		this.conversationDao = conversationDao;
		this.messageDao = messageDao;
	}
	// ========== test conversationDao ==========

	@Test
	public void testCase1() throws Exception {
		this.run(() -> {
			HashMap<String, String> hash = PropertyHelper.GetMongoDBChat();

			System.out.println(hash.get("connection"));
			System.out.println(hash.get("database"));
			System.out.println(hash.get("host"));
			System.out.println(hash.get("port"));

			return null;
		});
	}

	@Test
	public void testCase2() throws Exception {
		this.run(() -> {
			System.out.println(DatabaseSupplier.MongoDB.BetStore.Conversation);
			System.out.println(DatabaseSupplier.MongoDB.BetStore.Message);

			return null;
		});
	}

	@Test
	public void testCase3() throws Exception {
		this.run(() -> {
			assertTrue(this.conversationDao != null);
			return null;
		});
	}

	/**
	 * insert conversation
	 *
	 * @throws Exception
	 */
	@Test
	public void testCase4() throws Exception {
		this.run(() -> {
			List<Conversation> list = new ArrayList<>();
			list.add(new Conversation("abcd", "abcd1"));
			list.add(new Conversation("abd", "abcd1"));

			for (int i = 0; i < list.size(); i++) {
				String _id = this.conversationDao.insertConversation(list.get(i));
				this.conversationDao.deleteConversation(_id);
			}

			return null;
		}, "insert + delete conversation");
	}

	@Test
	public void testCase5() throws Exception {
		this.run(() -> {
			List<Conversation> list = new ArrayList<>();
			list.add(new Conversation("abcd", "abcd1"));
			list.add(new Conversation("abcd", "abcd2"));
			list.add(new Conversation("abd", "abcd1"));
			list.add(new Conversation("abd", "abcd2"));
			list.add(new Conversation("abd", "abcd3"));

			for (int i = 0; i < list.size(); i++) {
				String _id = this.conversationDao.insertConversation(list.get(i));
				list.get(i).set_id(new ObjectId(_id));
			}

			System.out.println("conversation of abcd");
			List<Conversation> con1 = this.conversationDao.getConversation("abcd");
			for (int i = 0; i < con1.size(); i++) {
				System.out.println(con1.get(i).toString());
			}

			System.out.println("====================");

			System.out.println("conversation of abc");
			List<Conversation> con2 = this.conversationDao.getConversation("abc");
			for (int i = 0; i < con2.size(); i++) {
				System.out.println(con2.get(i).toString());
			}

			for (int i = 0; i < list.size(); i++) {
				this.conversationDao.deleteConversation(list.get(i).get_id().toString());
			}
			return null;
		}, "get conversation by sender");
	}

	// ========== test messageDao ==========

	@Test
	public void testCase6() throws Exception {
		this.run(() -> {
			assertTrue(this.messageDao != null);
			return null;
		});
	}

	@Test
	public void testCase7() throws Exception {
		this.run(() -> {
			List<Message> list = new ArrayList<>();
			list.add(new Message("abcd", "abcd1", "messagetest1", "", MessageContentType.CONTENT_NONE));
			list.add(new Message("abcd", "abcd1", "messagetest2", "", MessageContentType.CONTENT_NONE));
			list.add(new Message("abcd", "abcd1", "messagetest3", "", MessageContentType.CONTENT_NONE));
			list.add(new Message("abd", "abcd1", "messagetest1", "", MessageContentType.CONTENT_NONE));

			for (int i = 0; i < list.size(); i++) {
				String _id = this.messageDao.insertMessage(list.get(i));
				list.get(i).set_id(new ObjectId(_id));
			}
			for (int i = 0; i < list.size(); i++) {
				this.messageDao.deleteMessage(list.get(i).get_id().toString());
			}
			return null;
		}, "insert + delete message");
	}

	@Test
	public void testCase8() throws Exception {
		this.run(() -> {
			List<Message> list = new ArrayList<>();
			list.add(new Message("abcd", "abcd1", "messagetest1", "", MessageContentType.CONTENT_NONE));
			list.add(new Message("abcd", "abcd1", "messagetest2", "", MessageContentType.CONTENT_NONE));
			list.add(new Message("abcd", "abcd1", "messagetest3", "", MessageContentType.CONTENT_NONE));
			list.add(new Message("abcd1", "abcd", "messagetestBonus", "", MessageContentType.CONTENT_NONE));
			list.add(new Message("abd", "abcd1", "messagetest1", "", MessageContentType.CONTENT_NONE));
			for (int i = 0; i < list.size(); i++) {
				String _id = this.messageDao.insertMessage(list.get(i));
				list.get(i).set_id(new ObjectId(_id));
			}

			System.out.println("message of abcd -> abcd1");

			List<Message> mes = this.messageDao.getMessage("abcd", "abcd1");
			for (int i = 0; i < mes.size(); i++) {
				System.out.println(mes.get(i).toString());
			}

			System.out.println("====================");

			System.out.println("message of conversation(abcd, abcd1)");
			List<Message> mes1 = this.messageDao.getMessageByConversation(new Conversation("abcd", "abcd1"));
			for (int i = 0; i < mes1.size(); i++) {
				System.out.println(mes1.get(i).toString());
			}

			for (int i = 0; i < list.size(); i++) {
				this.messageDao.deleteMessage(list.get(i).get_id().toString());
			}
			return null;
		}, "get message by conversation");
  }
}
