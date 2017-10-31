package com.challenge.rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.Is.is;

import com.challenge.App;
import com.challenge.config.RepositoryModule;
import com.challenge.config.ServiceModule;
import com.challenge.rest.model.account.CreateAccountModel;
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
import org.hamcrest.text.MatchesPattern;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AccountIntegrationTest {

  private HttpServer server;
  private ObjectMapper objectMapper;
  private String baseAccountsUri;
  private String baseUsersUri;

  @Before
  public void setup() throws IOException {
    Injector injector = Guice.createInjector(new RepositoryModule(), new ServiceModule());
    objectMapper = new ObjectMapper().findAndRegisterModules()
        .configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, false);
    App app = injector.getInstance(App.class);
    server = app.startServer();
    baseAccountsUri = App.BASE_URI.concat("/account");
    baseUsersUri = App.BASE_URI.concat("/user");
  }

  @Test
  public void testCreateAccount() throws JsonProcessingException {
    //Arrange
    Integer userId = createUser();
    String requestBody = prepareRequestBody(new Long(userId));
    //Act and Assert
    String location = given().contentType(ContentType.JSON).body(requestBody).post(baseAccountsUri)
        .then().
            assertThat().statusCode(is(201)).extract().header("Location");
    given().contentType(ContentType.JSON).get(location).then().
        statusCode(is(200)).
        body("iban", MatchesPattern
            .matchesPattern("[a-zA-Z]{2}[0-9]{2}[a-zA-Z0-9]{4}[0-9]{7}([a-zA-Z0-9]?){0,16}")).
        body("balance.currency", is(equalTo("USD"))).
        body("userId", is(equalTo(userId)));
  }

  private String prepareRequestBody(Long userId) throws JsonProcessingException {
    CreateAccountModel model = new CreateAccountModel();
    model.setUserId(userId);
    model.setCurrency("USD");
    return objectMapper.writeValueAsString(model);
  }

  private Integer createUser() throws JsonProcessingException {
    CreateUserInput input = new CreateUserInput();
    input.setFirstName("John");
    input.setLastName("Smith");
    input.setBirthDate(LocalDate.of(1974, 4, 25));
    String requestBody = objectMapper.writeValueAsString(input);
    String location = given().contentType(ContentType.JSON).body(requestBody).post(baseUsersUri)
        .then().
            extract().header("Location");
    Integer id = given().contentType(ContentType.JSON).get(location).then().extract().path("id");
    return id;
  }

  @After
  public void tearDown() throws Exception {
    server.shutdownNow();
  }
}
