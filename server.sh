scp -r data/chat root@larryjason.com:/app/chat-app/data;
scp -r logs root@larryjason.com:/app/chat-app/backend;
scp target/spring-1.0-SNAPSHOT.jar root@larryjason.com:/app/chat-app/backend;
scp docker-compose.yml root@larryjason.com:/app/chat-app;