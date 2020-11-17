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
db.Conversation.deleteMany({}); 
