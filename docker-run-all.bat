@echo off
setlocal enabledelayedexpansion

for /f "tokens=1-3 delims=:.," %%a in ("%time%") do (
    set "hh=%%a"
    set "mm=%%b"
    set "ss=%%c"
)

set STACK_NAME=studyhuppy

echo [%date% !hh!:!mm!:!ss!] Starte alle Studyhuppy-Services unter Stack "%STACK_NAME%"...

docker compose -p %STACK_NAME% ^
  --env-file mail/.env -f mail/docker-compose.yaml ^
  --env-file kartei/.env -f kartei/docker-compose.yaml ^
  --env-file track/.env -f track/docker-compose.yaml ^
  --env-file mindmap/.env -f mindmap/docker-compose.yaml ^
  --env-file authentication/.env -f authentication/docker-compose.yaml ^
  --env-file actuator/.env -f actuator/docker-compose.yaml ^
  up -d --build

echo [%date% !hh!:!mm!:!ss!] Alle Studyhuppy-Services unter Stack "%STACK_NAME%" gestartet.
