package com.model;

import org.bson.types.ObjectId;

public class Message extends MongoIdModel {
	private String senderId;
	private String receiverId;
	private String textContent;
	private String fileContent;
	private MessageContentType fileContentType;

	public Message() {
	}

	public Message(String senderId, String receiverId, String textContent, String fileContent,
			MessageContentType contentType) throws Exception {
		this.senderId = senderId;
		this.receiverId = receiverId;
		this.textContent = textContent;
		this.fileContent = fileContent;
		this.fileContentType = contentType;
	}

	public Message(ObjectId _id, String senderId, String receiverId, String textContent, String fileContent,
			MessageContentType contentType) {
		this._id = _id;
		this.senderId = senderId;
		this.receiverId = receiverId;
		this.textContent = textContent;
		this.fileContent = fileContent;
		this.fileContentType = contentType;
	}

	public String getSenderId() {
		return this.senderId;
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	public String getReceiverId() {
		return this.receiverId;
	}

	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}

	public String getTextContent() {
		return this.textContent;
	}

	public void setTextContent(String textContent) {
		this.textContent = textContent;
	}

	public String getFileContent() {
		return this.fileContent;
	}

	public void setFileContent(String fileContent) {
		this.fileContent = fileContent;
	}

	public MessageContentType getContentType() {
		return this.fileContentType;
	}

	public void setContentType(MessageContentType contentType) {
		this.fileContentType = contentType;
	}

	@Override
	public String toString() {
		return ("{" + " _id='" + get_id() + "'" + " senderId='" + getSenderId() + "'" + ", receiverId='"
				+ getReceiverId() + "'" + ", textContent='" + getTextContent() + "'" + ", fileContent='"
				+ getFileContent() + "'" + ", fileContentType='" + getContentType().toString() + "'" + "}");
	}
}
