package com.model;

import org.bson.types.ObjectId;

public class MongoIdModel {
  protected ObjectId _id;

  public ObjectId get_id() {
    return this._id;
  }

  public void set_id(ObjectId _id) {
    this._id = _id;
  }
}
