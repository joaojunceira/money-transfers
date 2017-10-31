package com.challenge.rest;


import static io.restassured.RestAssured.given;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.number.OrderingComparison.greaterThanOrEqualTo;

import com.challenge.App;
import com.challenge.config.RepositoryModule;
import com.challenge.config.ServiceModule;
import com.challenge.rest.model.user.CreateUserInput;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import io.restassured.http.ContentType;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UserIntegrationTest {

  private HttpServer server;
  private ObjectMapper objectMapper;
  private String baseUri;

  @Before
  public void setup() throws IOException {
    Injector injector = Guice.createInjector(new RepositoryModule(), new ServiceModule());
    objectMapper = new ObjectMapper();
    App app = injector.getInstance(App.class);
    server = app.startServer();
    baseUri = App.BASE_URI.concat("/user");
  }

  @Test
  public void testCreateUser() throws JsonProcessingException {
    //Arrange
    CreateUserInput input = new CreateUserInput();
    String requestBody = prepareRequestBody(input);
    //Act and Assert
    //Assert creation (POST)
    String location = given().contentType(ContentType.JSON).body(requestBody).post(baseUri).then().
        assertThat().statusCode(is(201)).extract().header("Location");
    System.out.println(location);
    //Assert GET
    given().contentType(ContentType.JSON).get(location).then().
        assertThat().statusCode(is(200)).
        body("firstName", is(input.getFirstName())).
        body("lastName", is(input.getLastName())).
        body("birthDate", is(input.getBirthDate())).
        body("id", is(greaterThanOrEqualTo(1)));
  }

  private String prepareRequestBody(CreateUserInput input) throws JsonProcessingException {
    input.setFirstName("John");
    input.setLastName("Smith");
    input.setBirthDate(LocalDate.of(1974, 4, 25));
    return objectMapper.writeValueAsString(input);
  }

  @After
  public void tearDown() throws Exception {
    server.shutdownNow();
  }

}
