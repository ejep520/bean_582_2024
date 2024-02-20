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
start /B /WAIT docker compose up --no-start
set bean_mysql_conn=""
set bean_mysql_admin_name=""
set bean_mysql_admin_pass=""
echo All done.
exit /B 0
