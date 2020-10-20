#!/bin/bash
#command to run before turn on server
scp -r data/chat root@larryjason.com:/app/chat-app/data/chat;
scp -r logs root@larryjason.com:/app/chat-app/backend/logs;
scp target/spring-1.0-SNAPSHOT.jar root@larryjason.com:/app/chat-app/backend;
scp docker-compose.yml root@larryjason.com:/app/chat-app;

# turn off server 
ssh -t root@larryjason.com docker-compose -f /app/chat-app/docker-compose.yml down

# turn on server 
ssh -t root@larryjason.com "docker-compose -f /app/chat-app/docker-compose.yml up -d && 
  docker exec chat-app_chat-dtb_1 mongorestore /data/db/backup"