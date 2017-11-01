package com.challenge.rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;

import com.challenge.App;
import com.challenge.rest.model.account.CreateAccountModel;
import com.challenge.rest.model.shared.MonetaryAmountModel;
import com.challenge.rest.model.transfer.RequestTransferModel;
import com.challenge.rest.model.user.CreateUserInput;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import javax.money.Monetary;
import javax.money.MonetaryAmount;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.JVM)
public class TransferIntegrationTest {

  private HttpServer server;
  private ObjectMapper objectMapper;
  private String baseUserUri;
  private String baseAccountsUri;
  private String baseTransfersUri;


  @Before
  public void setup() throws IOException {
    objectMapper = new ObjectMapper().findAndRegisterModules();
    App app = new App();
    server = app.startServer();
    baseUserUri = App.BASE_URI.concat("/user");
    baseAccountsUri = App.BASE_URI.concat("/account");
    baseTransfersUri = App.BASE_URI.concat("/transfer");
  }

  private void arrangeTestData()
      throws JsonProcessingException {
    Integer user = createUser();
    createAccount(user);
    createAccount(user);
  }

  private Integer createUser() throws JsonProcessingException {
    CreateUserInput input = new CreateUserInput();
    input.setFirstName("John");
    input.setLastName("Smith");
    input.setBirthDate(LocalDate.of(1974, 4, 25));
    String requestBody = objectMapper.writeValueAsString(input);
    String location = given().contentType(ContentType.JSON).body(requestBody).post(baseUserUri)
        .then().
            extract().header("Location");
    return given().contentType(ContentType.JSON).get(location).then().extract().path("id");
  }

  private void createAccount(Integer userId) throws JsonProcessingException {
    CreateAccountModel model = new CreateAccountModel();
    model.setUserId(new Long(userId));
    model.setCurrency("USD");
    String body = objectMapper.writeValueAsString(model);
    given().contentType(ContentType.JSON).body(body).post(baseAccountsUri).then().statusCode(201);
  }

  @Test
  public void testTransfer() throws JsonProcessingException {
    //Arrange
    final String ibanSource = "LT6000000000000000000001";
    final String ibanDestination = "LT6000000000000000000002";
    arrangeTestData();
    final String body = prepareRequestBody(ibanSource, ibanDestination, 0L);

    //Act and Assert
    given().contentType(ContentType.JSON).body(body).post(baseTransfersUri).then().
        assertThat().statusCode(200)
        .body("id", is(greaterThanOrEqualTo(1)))
        .body("source", is(equalTo(ibanSource)))
        .body("destination", is(equalTo(ibanDestination)))
        .body("amount.amount", is(equalTo(0)))
        .body("amount.currency", is(equalTo("USD")));
  }

  @Test
  public void testTransferWrongIban() throws JsonProcessingException {
    //Arrange
    final String ibanSource = "LT6000000000000000000001";
    final String ibanFake = "LT6000000000000000000009";
    final String body = prepareRequestBody(ibanSource, ibanFake, 0L);
    arrangeTestData();

    //Act and Assert
    given().contentType(ContentType.JSON).body(body).post(baseTransfersUri).then().
        assertThat().statusCode(400);
  }

  @Test
  public void testTransferNotEnoughFunds() throws JsonProcessingException {
    //Arrange
    final String ibanSource = "LT6000000000000000000001";
    final String ibanDestination = "LT6000000000000000000002";
    final String body = prepareRequestBody(ibanSource, ibanDestination, 50L);
    arrangeTestData();

    //Act and Assert
    given().contentType(ContentType.JSON).body(body).post(baseTransfersUri).then().
        assertThat().statusCode(403);
  }

  private String prepareRequestBody(String ibanSource, String ibanDestination,
      Long amountMoney)
      throws JsonProcessingException {
    RequestTransferModel requestTransferModel = new RequestTransferModel();
    MonetaryAmount amount = Monetary.getDefaultAmountFactory().
        setCurrency("USD").setNumber(amountMoney).create();
    requestTransferModel.setSource(ibanSource);
    requestTransferModel.setDestination(ibanDestination);
    requestTransferModel.setAmount(new MonetaryAmountModel(amount.getNumber().numberValue(
        BigDecimal.class), amount.getCurrency().getCurrencyCode()));
    return objectMapper.writeValueAsString(requestTransferModel);
  }

  @After
  public void tearDown() throws Exception {
    server.shutdownNow();
  }
}
