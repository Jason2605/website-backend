FROM openjdk:8-alpine

COPY ./ /usr/share/app
WORKDIR /usr/share/app

RUN apk add maven
RUN /bin/sh -c "./mvnw clean package"

CMD ["java", "-jar", "target/api-service-0.0.1-SNAPSHOT.jar"]