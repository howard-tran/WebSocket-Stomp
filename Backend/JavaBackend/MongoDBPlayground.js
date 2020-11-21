// MongoDB playground

// create message collection

print("###################### Create Messagge Collection ######################");

db = db.getSiblingDB("bet_store");
db.Message.createIndex({ senderId: 1, receiverId: 1 });

print("###################### End Messagge Collection ######################");

// create conversation collection

print("###################### Create Conversation Collection ######################");

db = db.getSiblingDB("bet_store");
db.Conversation.createIndex({ senderId: 1 });

print("###################### End Conversation Collection ######################");

// test
db.Conversation.find();
db.Conversation.find({ _id: ObjectId("5fb8083ccda5c41790c0b067") });
db.Conversation.deleteMany({});
db.Conversation.deleteOne({ _id: ObjectId("5fb7ffdfed6e86392ceab1d6") });

db.Message.find({
  $or: [
    { $and: [{ senderId: "a" }, { receiverId: "b" }] },
    { $and: [{ senderId: "b" }, { receiverId: "a" }] },
  ],
}).sort({ _id: 1 });

db.Message.find({ senderId: "a" }).explain();
