#!/bin/bash

echo "[$(date '+%d.%m.%Y %H:%M:%S')] Starte Studyhub-Services..."
echo "[$(date '+%d.%m.%Y %H:%M:%S')] Starte Mail-Service..."
docker compose --env-file mail/.env -f mail/docker-compose.yaml up -d --build
echo "[$(date '+%d.%m.%Y %H:%M:%S')] Mail-Service gestartet."

echo "[$(date '+%d.%m.%Y %H:%M:%S')] Starte Kartei-Service..."
docker compose --env-file kartei/.env -f kartei/docker-compose.yaml up -d --build
echo "[$(date '+%d.%m.%Y %H:%M:%S')] Kartei-Service gestartet."

echo "[$(date '+%d.%m.%Y %H:%M:%S')] Starte Track-Service..."
docker compose --env-file track/.env -f track/docker-compose.yaml up -d --build
echo "[$(date '+%d.%m.%Y %H:%M:%S')] Track-Service gestartet."

echo "[$(date '+%d.%m.%Y %H:%M:%S')] Starte Mindmap-Service..."
docker compose --env-file mindmap/.env -f mindmap/docker-compose.yaml up -d --build
echo "[$(date '+%d.%m.%Y %H:%M:%S')] Mindmap-Service gestartet."

echo "[$(date '+%d.%m.%Y %H:%M:%S')] Starte Authentication-Service..."
docker compose --env-file authentication/.env -f authentication/docker-compose.yaml up -d --build
echo "[$(date '+%d.%m.%Y %H:%M:%S')] Authentication-Service gestartet."

echo "[$(date '+%d.%m.%Y %H:%M:%S')] Starte Actuator-Service..."
docker compose --env-file actuator/.env -f actuator/docker-compose.yaml up -d --build
echo "[$(date '+%d.%m.%Y %H:%M:%S')] Actuator-Service gestartet."

echo "[$(date '+%d.%m.%Y %H:%M:%S')] Alle Studyhub-Services sind gestartet."
