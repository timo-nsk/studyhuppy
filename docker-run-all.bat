@echo off
setlocal enabledelayedexpansion

rem ==== Zeit erfassen ====
for /f "tokens=1-3 delims=:.," %%a in ("%time%") do (
    set "hh=%%a"
    set "mm=%%b"
    set "ss=%%c"
)

set STACK_NAME=studyhuppy

echo [%date% !hh!:!mm!:!ss!] Starte alle Studyhuppy-Services unter Stack "%STACK_NAME%"...

rem ==== Mail-Service starten ====
echo Starte Mail-Service...
docker compose -p %STACK_NAME% --env-file backend/mail/.env -f backend/mail/docker-compose.yaml up -d --build

rem ==== Kartei-Service starten ====
echo Starte Kartei-Service...
docker compose -p %STACK_NAME% --env-file backend/kartei/.env -f backend/kartei/docker-compose.yaml up -d --build

rem ==== Track-Service starten ====
echo Starte Track-Service...
docker compose -p %STACK_NAME% --env-file backend/track/.env -f backend/track/docker-compose.yaml up -d --build

rem ==== Mindmap-Service starten ====
echo Starte Mindmap-Service...
docker compose -p %STACK_NAME% --env-file backend/mindmap/.env -f backend/mindmap/docker-compose.yaml up -d --build

rem ==== Authentication-Service starten ====
echo Starte Authentication-Service...
docker compose -p %STACK_NAME% --env-file backend/authentication/.env -f backend/authentication/docker-compose.yaml up -d --build

rem ==== Actuator-Service starten ====
echo Starte Actuator-Service...
docker compose -p %STACK_NAME% --env-file backend/actuator/.env -f backend/actuator/docker-compose.yaml up -d --build

rem ==== Frontend starten ====
echo Starte Frontend...
docker compose -p %STACK_NAME% -f frontend/docker-compose.yaml up -d --build

echo [%date% !hh!:!mm!:!ss!] Alle Studyhuppy-Services unter Stack "%STACK_NAME%" gestartet.

endlocal
