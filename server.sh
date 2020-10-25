#!/bin/bash
# command to run before turn on server
ssh -t root@larryjason.com rm -rf /app/chat-app/data/chat/*; 
ssh -t root@larryjason.com rm -rf /app/chat-app/backend/logs/*; 

scp -r data/chat/* root@larryjason.com:/app/chat-app/data/chat;
scp -r logs/* root@larryjason.com:/app/chat-app/backend/logs;
scp target/spring-1.0-SNAPSHOT.jar root@larryjason.com:/app/chat-app/backend;
scp docker-compose.yml root@larryjason.com:/app/chat-app;

# sync client
ssh -t root@larryjason.com rm -rf /var/www/public_html/chat-app/*; 
scp -r client/chat/build root@larryjason.com:/var/www/public_html/chat-app; 
ssh -t root@larryjason.com mv /var/www/public_html/chat-app/build /var/www/public_html/chat-app/chat; 
scp -r client/login root@larryjason.com:/var/www/public_html/chat-app;

# turn off server 
ssh -t root@larryjason.com docker-compose -f /app/chat-app/docker-compose.yml down

# turn on server 
ssh -t root@larryjason.com "docker-compose -f /app/chat-app/docker-compose.yml up -d && 
  docker exec chat-app_chat-dtb_1 mongorestore /data/db/backup"