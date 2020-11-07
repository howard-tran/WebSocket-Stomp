#!/bin/bash
#chown root /var/log/mongodb
echo "init file"
#bash
mongod --bind_ip 0.0.0.0 &> /dev/null &
# echo "RunMongo done"
mongo bet_store mongo-init.js
fg
# --fork --logpath /var/log/mongodb/mongod.log
#mongo bet_store mongo-init.js
echo "rundone init js"