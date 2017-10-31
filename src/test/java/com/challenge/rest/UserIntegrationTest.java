package com.challenge.rest;


import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.number.OrderingComparison.greaterThanOrEqualTo;

import com.challenge.App;
import com.challenge.config.RepositoryModule;
import com.challenge.config.ServiceModule;
import com.challenge.rest.model.user.CreateUserInput;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Guice;
import com.google.inject.Injector;
import io.restassured.http.ContentType;
import java.io.IOException;
import java.time.LocalDate;
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
    objectMapper = new ObjectMapper().findAndRegisterModules()
        .configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, false);
    App app = injector.getInstance(App.class);
    server = app.startServer();
    baseUri = App.BASE_URI.concat("/user");
  }

  @Test
  public void testCreateUser() throws JsonProcessingException {
    //Arrange
    CreateUserInput input = new CreateUserInput();
    String requestBody = prepareRequestBody(input);
    LocalDate birthDate = input.getBirthDate();

    //Act and Assert
    //Assert creation (POST)
    String location = given().contentType(ContentType.JSON).body(requestBody).post(baseUri).then().
        assertThat().statusCode(is(201)).extract().header("Location");
    //Assert GET
    given().contentType(ContentType.JSON).get(location).then().
        assertThat().statusCode(is(200)).
        body("firstName", is(input.getFirstName())).
        body("lastName", is(input.getLastName())).
        body("birthDate", contains(
            new Integer[]{birthDate.getYear(), birthDate.getMonthValue(),
                birthDate.getDayOfMonth()})).
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
