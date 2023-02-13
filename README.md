# andela-test

> Tech Stack Used

    - Java 11.
    - Spring Boot, Spring Data JPA.
    - Swagger2
    - PostgreSQL.
    - Docker and DockerCompose.

> Please execute the below steps from root of the project to run application

  - Step 1 : mvn clean package
  - Step 2 : docker-compose up (This will bring the PostgreSQL database up and running).
  - Step 3 : Connect to the postgres instance with tool of choice(pgAdmin4) and create a db named 'xml-catalog'. 
  - Step 4 : java -jar target/xml-parser-1.0.0.jar
  - Step 5 : Now you can access the api running on port 8080.
  
> Note: Please note step 3 can be replace by building the dockerfile as well.
 - docker build -t xml-parser:latest
 - docker run xml-parser:latest
 
> Note: Postman collection as well provided with in the workspace for easier testing purpous.

> TODO: We can improve the docker-compose file to be a multi stage one, where build of the spring boot app, building of the image and running of image will be done at once. 
