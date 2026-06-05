@echo off

start "" cmd /c "java -jar clicker-game.jar"

timeout /t 5 /nobreak > nul

start http://localhost:8080