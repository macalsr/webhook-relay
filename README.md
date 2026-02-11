# Webhook Relay

Serviço para ingestão de webhooks com foco em idempotência, processamento assíncrono, retentativas e DLQ.

## Requisitos
- Java 21

## Rodar local
./mvnw spring-boot:run

## Health check
GET http://localhost:8080/actuator/health

## Ping
GET http://localhost:8080/ping