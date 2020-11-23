package com.controller;

public class ResponseHandler {
  public static Response<Object> ok(Object data) {
    return new Response<Object>(200, "ok", data);
  }

  public static Response<Object> ok() {
    return new Response<Object>(200, "ok", null);
  }

  public static Response<Object> error() {
    return new Response<Object>(500, "internal-server-error", null);
  }

  public static Response<Object> error(Object data) {
    return new Response<Object>(500, "internal-server-error", data);
  }

  public static Response<Object> duplicateConversation() {
    return new Response<Object>(1001, "duplicate-conversation", null);
  }
}
