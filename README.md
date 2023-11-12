# devsu-microservices

Generate the artifacts on the micros:
```sh
# /microservices/{micro}
./gradlew bootJar
```

Create containers
```sh
# ./
docker-compose up -d
```

Bind client topic to movement queue

1. go to RabbitMQ management:  http://localhost:15672/
2. Login (Credenciales in .env)
3. Select the client topic and bind it to the movement queue.