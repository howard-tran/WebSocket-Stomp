package com.model;

public enum MessageContentType {
  CONTENT_NONE("none"),
  CONTENT_PRODUCT_INFO("productInfo"),
  CONTENT_SOUND("sound"),
  CONTENT_IMAGE("image"),
  CONTENT_PLAINTEXT("txt"),
  CONTENT_ZIP("zip"),
  CONTENT_RAR("rar");

  private String contentType;

  MessageContentType(String value) {
    this.contentType = value;
  }

  public String getContentType() {
    return this.contentType;
  }
}
