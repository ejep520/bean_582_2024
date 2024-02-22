echo 'Checking for a database password.'
if ($args.count -lt 1) {
    echo 'This script requires a password be passed in for the database creation.'
    echo ''
    echo 'Example:'
    echo '.\buildme.ps1 ThisIsALousyPassword!'
    echo ''
    exit
}
echo 'Setting up...'
$Env:bean_mysql_admin_pass='${args[0]}'
$Env:bean_mysql_admin_name='root'
$Env:bean_mysql_conn='jdbc:mysql://mysql:3306/aptFinder'
echo 'Building the jar...'
start-process -FilePath 'cmd' -ArgumentList @('/c', '.\gradlew.bat', 'clean', 'vaadinClean', 'bootJar') -Wait -ErrorAction Ignore
echo 'Copying the jar...'
copy-item build/libs/ApartmentFinder-0.0.1-SNAPSHOT.jar ./
echo 'Building the Docker container...'
Start-Process -FilePath docker -ArgumentList @('build', '.', '-t', 'apartment_finder')
echo 'Creating the Docker Compose stack...'
Start-Process -FilePath docker -ArgumentList @('compose', 'up') -Wait
echo 'Cleaning up...'
$Env:bean_mysql_admin_pass=''
$Env:bean_mysql_admin_name=''
$Env:bean_mysql_conn=''
Remove-Item ApartmentFinder-0.0.1-SNAPSHOT.jar
echo 'All done.'
