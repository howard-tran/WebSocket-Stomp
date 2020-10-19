#!/bin/bash
scp -r data/chat root@larryjason.com:/app/chat-app/data/chat;
scp -r logs root@larryjason.com:/app/chat-app/backend/logs;
scp target/spring-1.0-SNAPSHOT.jar root@larryjason.com:/app/chat-app/backend;
scp DbEntry.sh root@larryjason.com:/app/chat-app;
scp docker-compose.yml root@larryjason.com:/app/chat-app;