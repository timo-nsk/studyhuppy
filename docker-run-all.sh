#!/bin/bash

current_time=$(date +"%Y-%m-%d %H:%M:%S")

STACK_NAME="studyhuppy"

echo "[$current_time] Starte alle Studyhuppy-Services unter Stack \"$STACK_NAME\"..."

echo "Starte Mail-Service..."
docker compose -p "$STACK_NAME" --env-file backend/mail/.env -f backend/mail/docker-compose.yaml up -d --build

echo "Starte Kartei-Service..."
docker compose -p "$STACK_NAME" --env-file backend/kartei/.env -f backend/kartei/docker-compose.yaml up -d --build

echo "Starte Track-Service..."
docker compose -p "$STACK_NAME" --env-file backend/track/.env -f backend/track/docker-compose.yaml up -d --build

echo "Starte Mindmap-Service..."
docker compose -p "$STACK_NAME" --env-file backend/mindmap/.env -f backend/mindmap/docker-compose.yaml up -d --build

echo "Starte Authentication-Service..."
docker compose -p "$STACK_NAME" --env-file backend/authentication/.env -f backend/authentication/docker-compose.yaml up -d --build

echo "Starte Actuator-Service..."
docker compose -p "$STACK_NAME" --env-file backend/actuator/.env -f backend/actuator/docker-compose.yaml up -d --build

echo "Starte Frontend..."
docker compose -p "$STACK_NAME" -f frontend/docker-compose.yaml up -d --build

current_time=$(date +"%Y-%m-%d %H:%M:%S")
echo "[$current_time] Alle Studyhuppy-Services unter Stack \"$STACK_NAME\" gestartet."
