#Build image
FROM openjdk:11
WORKDIR /app
COPY ./target/xml-parser-1.0.0.jar /app/xml-parser-1.0.0.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","xml-parser-1.0.0.jar"]