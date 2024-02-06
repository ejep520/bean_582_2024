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
* [JUnit 5](https://junit.org/junit5/)

## Running this project
* Step by step instructions for running this project starting at git clone
* 
## Introduction
This is a demostration project of an apartment finder web portal.

## Requirements
### Narrative
This project is a web portal for finding availiable apartments for rent.

### Functional
* As an owner I want to be able to have my listings "featured" before others
  * Featured meaning the featured apartments are shown first on the main landing page and on search pages.
  *  These could be done in a priority (who pays the most) or character set order (alphabetical).
* As a property owner I want to update and maintain my building's apartment unit data
* As a property owner I want to list my property
* As a shopper I want to search for an apartment with given search criteria 
  * For example: postal code, number of bedrooms, size, bathrooms
* As a shopper I want to view an apartment floorplan
* As a shopper I want to see interior views of an apartment
* As a shopper I want to send an apartment complex an inquiry message
* As an authenticated shopper I want to receive property notifications
  * When a favorited unit availiability state changes
  * When a message is read or received
* As an authenticated shopper I want to preserve my favorites between sessions
* As an authenticated shopper 
* As an unauthenticated shopper I want to maintain a list of favorited units. (Cookie)
* Stretch: Either link to Google Maps or embed Google Maps into the UI

* As a developer/tester I want a testable code base
  * Key UI components should have a way for test to locate the UI element programmatically

## Test
[Junit 5](https://junit.org/junit5/) and [Selenium](https://selenium.dev) will be used for automated test cases.
* Unit testing. Individual units of testable code must have a corresponding unit test. Mocking may be used appropriate
* Functional tests
  * API Testing
    * APIs may be needed to facilitate testing. These must be such that authorization is required with a fixed token
  * Integration Testing
  * UI Integration Testing
    * UI Testing will be accomplished using [Selenium](https://selenium.dev) to provide programmatic control of the UI to simulate the end user's behaviors.
    * UI Test scripts should not be one monolithic script. Each script should accomplish one use case
* Tests
  * Are "paid to promote" prioritization listed correctly?
  * Do searches return the proper properties?
    * I want all apartments with 3+ bedrooms. Do all the appropriate apartments get returned
  * The correct floorplan image is returned. This may just be a URL that can be made to the src attribute of an <image> element
  * Are the correct interior images returned?
  * Are favorited properties maintained?

// TODO:

* Show graphs, tables, and UML


### ~Additional links~
* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.2.1/gradle-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.2.1/gradle-plugin/reference/html/#build-image)
* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)
* [Spring](https://spring.io/)
* [Spring Initializer](https://start.spring.io/)
