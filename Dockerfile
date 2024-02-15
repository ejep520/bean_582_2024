FROM bellsoft/liberica-openjdk-alpine:17
LABEL "Author"="Erik Jepsen <erik.jepsen@wsu.edu>"
WORKDIR /app
ADD 'ApartmentFinder-0.0.1-SNAPSHOT.jar' /app/
CMD ["java", "-jar", "./ApartmentFinder-0.0.1-SNAPSHOT.jar"]
