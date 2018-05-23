FROM openjdk:8-jdk-stretch
ADD target/service.jar service.jar
EXPOSE 8080
CMD java -jar service.jar