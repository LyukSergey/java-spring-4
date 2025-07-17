@echo off
REM Очищення Maven кешу і перезавантаження проекту
echo Cleaning Maven cache...
call mvn clean
call mvn -U clean install -DskipTests

echo Done. Please refresh your IDE project.
