<h1 style="text-align:center;">
	Washington State University<br />
	CPT_S 582 Software Testing<br />
	Spring 2024
</h1>

<table align=center>
<tr><td>Team Name</td><td colspan=4>Bean_582_2024_aptFinder</td></tr>
<tr><td>Repository</td><td colspan=4><a href="https://github.com/ejep520/bean_582_2024" target=_blank>https://github.com/ejep520/bean_582_2024</a></td></tr>
<tr><td>Project topic</td><td colspan=4> A java spring web application to enable users to locate an apartment available for rent.</td></tr>
<tr><td>Team members</td><td>Name</td><td>EMail</td><td>WSU ID</td><td>Contact</td></tr>
<tr><td></td><td>Bryan Lipscy</td><td><a href="mailto:bryan.lipscy@wsu.edu">bryan.lipscy@wsu.edu</a></td><td>011860966</td><td>Yes</td></tr>
<tr><td></td><td>Erik Jepsen</td><td><a href="mailto:erik.jepsen@wsu.edu">erik.jepsen@wsu.edu</a></td><td>011868711</td><td></td></tr>
<tr><td></td><td>Antonio Barber</td><td><a href="mailto:antonio.barber@wsu.edu">antonio.barber@wsu.edu</a></td><td>011551036</td><td></td></tr>
<tr><td></td><td>Nico Williams</td><td><a href="mailto:nico.williams@wsu.edu">nico.williams@wsu.edu</a></td><td>011878369</td><td></td></tr>
</table>

## Pre-requisites
* An IDE - [Eclipse](https://eclipseide.org/), [Visual Studio Code](https://code.visualstudio.com/), [IntelliJ IDEA](https://www.jetbrains.com/idea/)
* [Gradle](https://gradle.org/install/)
* [BellSoft Liberica JDK version 17](https://bell-sw.com/pages/downloads/#jdk-17-lts)
* [Docker](https://www.docker.com)
* [JUnit 5](https://junit.org/junit5/)

## Running this project
* Install docker desktop
* Start Docker Desktop
* Install [Java 17 liberica](https://bell-sw.com/pages/downloads/#jdk-17-lts)
* Set environment variables (Windows)
  * Name: `bean_mysql_admin_name` Value: `root`
  * Name: `bean_mysql_admin_pass` Value: `MyPassword`
  * Name: `bean_mysql_conn` Value: `jdbc:mysql://mysql:3306/aptfinder`

### Approach #1
* Get the code
  * Clone this repository: `https://github.com/ejep520/bean_582_2024.git`
  * Or unzip the project
* Open a command line prompt or terminal window
* Change directory to `ApartmentFinder`
* Run: `gradlew bootjar`
* Copy jar file from `$\bean_582_2024\ApartmentFinder\build\libs\ApartmentFinder-0.0.1-SNAPSHOT.jar` to `$\bean_582_2024\ApartmentFinder`
* Run `docker compose up`

### Approach #2
* Run: `docker pull <need the image name>`
  
## Introduction
The purpose of this project is to provide a functional application to author tests against. 

## Requirements
### Security
* User authentication will be used for apartment managers and apartment hunters

## Test
[Junit 5](https://junit.org/junit5/) will be used for automated test cases

Approach to testing. WECT, SECT, partioning etc.

### ~Additional links~
* [Official Gradle documentation](https://docs.gradle.org)
* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)
* [Spring](https://spring.io/)
* [Spring Initializer](https://start.spring.io/)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.2.1/gradle-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.2.1/gradle-plugin/reference/html/#build-image)
