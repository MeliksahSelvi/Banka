FROM postgres
ENV POSTGRES_PASSWORD 1234
ENV POSTGRES_DB bank

FROM openjdk:17
ARG JAR_FILE=target/banka-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} application.jar
EXPOSE 8080

ENTRYPOINT ["java","-jar","application.jar"]