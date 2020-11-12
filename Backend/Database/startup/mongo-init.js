print("###################### Start Create MongoDB ######################");

db = db.getSiblingDB("bet_store");
db.KeyCodeTel.createIndex({ time: 1 }, { expireAfterSeconds: 300 });
db.Account.createIndex({ username: 1 }, { unique: true });

print("###################### End Create MongoDB ######################");
