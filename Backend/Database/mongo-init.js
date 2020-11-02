print('###################### Start Create MongoDB ######################');

db = db.getSiblingDB('bet_store');
// db.createUser(
//   {
//     user: 'root',
//     pwd: 'root',
//     roles: [{ role: 'readWrite', db: 'bet_store' }],
//   },
// );
//use('bet_store');
db.getCollection('Account').ensureIndex({username:1},{unique: true});
db.KeyCodeTel.createIndex({"time": 1},{expireAfterSeconds: 300});

print('###################### End Create MongoDB ######################');