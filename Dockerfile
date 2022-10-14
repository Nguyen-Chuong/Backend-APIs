FROM openjdk:15-jdk-alpine
EXPOSE 8080
VOLUME /tmp
ADD target/hbts-0.0.1-SNAPSHOT.jar hbts-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","hbts-0.0.1-SNAPSHOT.jar"]