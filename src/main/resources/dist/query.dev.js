"use strict";

DBQuery.shellBatchSize = 100;
db.user.find();
db.user.deleteOne({
  id: "8da225ed-1f14-42cc-bb40-f0abb7d0e348",
});
db.user.deleteOne({
  id: "5e48320f-549d-4fa6-abca-e9bff0885a33",
});
db.user.deleteOne({
  id: "6f3aec8e-587b-4ce1-9faa-a2238eb57010",
});
db.user.deleteOne({
  id: "add57961-fa0d-4d26-b9e3-e69df91a911d",
});
db.user.deleteOne({
  id: "80a449eb-5ee0-41b5-be88-bc2a33aa8102",
});
db.user.find({
  UserName: {
    $regex: /^m/,
    $options: "i",
  },
});
db.user.update(
  {},
  {
    $rename: {
      UserName: "userName",
    },
  },
  false,
  true
);
db.user.update(
  {},
  {
    $rename: {
      PassWord: "passWord",
    },
  },
  false,
  true
);
db.createCollection("test");
db.test.insertOne({
  name: "abcd",
  time: "123",
});

for (var i = 0; i < 10000000; i++) {
  var randomNum = Math.random() * 1000;
  db.test.insertOne({
    name: "abcd",
    time: Math.round(randomNum),
  });
}

db.test.deleteMany({});
db.test.find({}).sort({
  time: -1,
});
db.messageCounter.insertOne({
  seq: 0,
});
db.messageCounter.find(); // aggregate = query and return a new collection

db.messageCounter.aggregate([
  {
    $project: {
      item: 1,
      seq: {
        $add: ["$seq", 1],
      },
    },
  },
]);
db.messageCounter.updateOne(
  {},
  {
    $inc: {
      seq: -1,
    },
  }
);
