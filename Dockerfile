FROM openjdk:8-jre-alpine
LABEL maintainer="bogdevich96@gmail.com"
ADD target/bulbs-0.0.1-SNAPSHOT.jar bulbs.jar
EXPOSE 9797
ENTRYPOINT ["java","-jar","bulbs.jar"]