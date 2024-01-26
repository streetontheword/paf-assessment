FROM maven:3-eclipse-temurin-21


WORKDIR /app

COPY mvnw .
COPY mvnw.cmd .
COPY pom.xml .
COPY .mvn .mvn 
COPY src src 

RUN mvn package -Dmaven.test.skip=true

ENV PORT=8080
ENV SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/bedandbreakfast
ENV SPRING_DATA_MONGODB_URI=mongodb://localhost:27017/bedandbreakfast


EXPOSE ${PORT}

ENTRYPOINT SERVER_PORT=${PORT} java -jar target/assessment-0.0.1-SNAPSHOT.jar



