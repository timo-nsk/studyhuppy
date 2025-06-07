@echo off

setlocal enabledelayedexpansion

for /f "tokens=1-3 delims=:.," %%a in ("%time%") do (
    set "hh=%%a"
    set "mm=%%b"
    set "ss=%%c"
)

echo [%date% !hh!:!mm!:!ss!] Starte Studyhub-Services...
echo [%date% !hh!:!mm!:!ss!] Starte Mail-Service...
docker compose --env-file mail/.env -f mail/docker-compose.yaml up -d --build
echo [%date% !hh!:!mm!:!ss!] Mail-Service gestartet.

echo [%date% !hh!:!mm!:!ss!] Starte Kartei-Service...
docker compose --env-file kartei/.env -f kartei/docker-compose.yaml up -d --build
echo [%date% !hh!:!mm!:!ss!] Kartei-Service gestartet.

echo [%date% !hh!:!mm!:!ss!] Starte Track-Service...
docker compose --env-file track/.env -f track/docker-compose.yaml up -d --build
echo [%date% !hh!:!mm!:!ss!] Track-Service gestartet.

echo [%date% !hh!:!mm!:!ss!] Starte Mindmap-Service...
docker compose --env-file mindmap/.env -f mindmap/docker-compose.yaml up -d --build
echo [%date% !hh!:!mm!:!ss!] Mindmap-Service gestartet.

echo [%date% !hh!:!mm!:!ss!] Starte Authentication-Service...
docker compose --env-file authentication/.env -f authentication/docker-compose.yaml up -d --build
echo [%date% !hh!:!mm!:!ss!] Authentication-Service gestartet.

echo [%date% !hh!:!mm!:!ss!] Starte Actuator-Service...
docker compose --env-file actuator/.env -f actuator/docker-compose.yaml up -d --build
echo [%date% !hh!:!mm!:!ss!] Actuator-Service gestartet.

echo [%date% !hh!:!mm!:!ss!] Alle Studyhub-Services sind gestartet.