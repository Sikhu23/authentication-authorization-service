FROM openjdk:17
ADD target/dockerAuthService.jar dockerAuthService.jar
EXPOSE 3000
ENTRYPOINT ["java","-jar","dockerAuthService.jar"]