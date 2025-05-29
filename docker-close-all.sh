#!/bin/bash

echo "[$(date '+%d.%m.%Y %H:%M:%S')] Schließe Studyhub-Services..."
echo "[$(date '+%d.%m.%Y %H:%M:%S')] Schließe Mail-Service..."
docker compose -f mail/docker-compose.yaml down
echo "[$(date '+%d.%m.%Y %H:%M:%S')] Mail-Service geschlossen."

echo "[$(date '+%d.%m.%Y %H:%M:%S')] Schließe Kartei-Service..."
docker compose -f kartei/docker-compose.yaml down
echo "[$(date '+%d.%m.%Y %H:%M:%S')] Kartei-Service geschlossen."

echo "[$(date '+%d.%m.%Y %H:%M:%S')] Schließe Track-Service..."
docker compose -f track/docker-compose.yaml down
echo "[$(date '+%d.%m.%Y %H:%M:%S')] Track-Service geschlossen."

echo "[$(date '+%d.%m.%Y %H:%M:%S')] Schließe Authentication-Service..."
docker compose -f authentication/docker-compose.yaml down
echo "[$(date '+%d.%m.%Y %H:%M:%S')] Authentication-Service geschlossen."

echo "[$(date '+%d.%m.%Y %H:%M:%S')] Schließe Actuator-Service..."
docker compose -f actuator/docker-compose.yaml down
echo "[$(date '+%d.%m.%Y %H:%M:%S')] Actuator-Service geschlossen."

echo "[$(date '+%d.%m.%Y %H:%M:%S')] Alle Studyhub-Services wurden heruntergefahren."