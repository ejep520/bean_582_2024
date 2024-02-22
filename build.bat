@echo off
echo Checking for a database password.
if "%~1" equ "" (
echo This batch file requires a password be passed in for the database creation.
echo Example:
echo buildme.bat ThisIsALousyPassword!
echo ""
exit /B 1
)
echo Setting up...
set bean_mysql_admin_name=root
set bean_mysql_admin_pass=%~1
set bean_mysql_conn=jdbc:mysql://mysql:3306/aptFinder
echo Building the jar...
call gradlew clean vaadinClean bootJar
echo Copying the jar...
copy .\build\libs\ApartmentFinder-0.0.1-SNAPSHOT.jar .\
echo Building the docker image
start /B /WAIT docker build . 
echo Composing the Docker container...
start /B /WAIT docker compose up --no-start
call gradlew clean vaadinClean
set bean_mysql_conn=""
set bean_mysql_admin_name=""
set bean_mysql_admin_pass=""
del ApartmentFinder-0.0.1-SNAPSHOT.jar
echo All done.
exit /B 0
