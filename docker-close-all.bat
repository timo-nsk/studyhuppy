@echo off

setlocal enabledelayedexpansion

for /f "tokens=1-3 delims=:.," %%a in ("%time%") do (
    set "hh=%%a"
    set "mm=%%b"
    set "ss=%%c"
)

echo [%date% !hh!:!mm!:!ss!] Schließe Studyhub-Services...
echo [%date% !hh!:!mm!:!ss!] Schließe Mail-Service...
docker compose -f mail/docker-compose.yaml down
echo [%date% !hh!:!mm!:!ss!] Mail-Service geschlossen.

echo [%date% !hh!:!mm!:!ss!] Schließe Kartei-Service...
docker compose  -f kartei/docker-compose.yaml down
echo [%date% !hh!:!mm!:!ss!] Kartei-Service geschlossen.

echo [%date% !hh!:!mm!:!ss!] Schließe Track-Service...
docker compose  -f track/docker-compose.yaml down
echo [%date% !hh!:!mm!:!ss!] Track-Service geschlossen.

echo [%date% !hh!:!mm!:!ss!] Schließe Authentication-Service...
docker compose  -f authentication/docker-compose.yaml down
echo [%date% !hh!:!mm!:!ss!] Authentication-Service geschlossen.

echo [%date% !hh!:!mm!:!ss!] Schließe Actuator-Service...
docker compose -f actuator/docker-compose.yaml down
echo [%date% !hh!:!mm!:!ss!] Actuator-Service geschlossen.

echo [%date% !hh!:!mm!:!ss!]Alle Studyhub-Services runtergefahren.