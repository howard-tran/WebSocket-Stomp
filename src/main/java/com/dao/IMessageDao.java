package com.dao;

import com.model.Conversation;
import com.model.Message;
import java.util.List;

public interface IMessageDao extends IDbQueryLogic {
	public List<Message> getAllMessage() throws Exception;

	public String insertMessage(Message data) throws Exception;

	public void deleteMessage(String _id) throws Exception;

	public List<Message> getMessageById(String _id) throws Exception;

	public List<Message> getMessage(String senderId, String receiverId) throws Exception;

	public List<Message> getMessageByConversation(Conversation conversation) throws Exception;
}
