# Book Listing Project

## Used Technologies

- Java 11
- Spring Boot
- Spring Security
- Spring Data MongoDB
- Restful API
- OpenAPI documentation
- Lombok
- JUnit Mockito
- Maven

## Run & Build

*$PORT: 8080*

```ssh

git clone https://github.com/nejlasahin/book-listing-project.git

$ cd book-listing-project
$ docker-compose -f src/main/resources/docker-compose-mongodb.yaml up
$ mvn clean install
$ mvn spring-boot:run

```

## Swagger UI will be run on this url

`http://localhost:${PORT}/swagger-ui.html`

## API Endpoints

![endpoints](./docs/endpoint.png)


