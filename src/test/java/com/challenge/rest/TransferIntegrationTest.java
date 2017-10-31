package com.challenge.rest;

import static io.restassured.RestAssured.given;

import com.challenge.App;
import com.challenge.config.RepositoryModule;
import com.challenge.config.ServiceModule;
import com.challenge.domain.model.Account;
import com.challenge.domain.model.User;
import com.challenge.domain.repository.AccountRepository;
import com.challenge.domain.repository.UserRepository;
import com.challenge.rest.model.shared.MonetaryAmountModel;
import com.challenge.rest.model.transfer.RequestTransferModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Guice;
import com.google.inject.Injector;
import io.restassured.http.ContentType;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import javax.inject.Inject;
import javax.money.Monetary;
import javax.money.MonetaryAmount;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TransferIntegrationTest {

  @Inject
  private UserRepository userRepository;
  @Inject
  private AccountRepository accountsRepository;
  private HttpServer server;
  private ObjectMapper objectMapper;


  @Before
  public void setup() throws IOException {

    Injector injector = Guice.createInjector(new RepositoryModule(), new ServiceModule());
    this.userRepository = injector.getInstance(UserRepository.class);
    this.accountsRepository = injector.getInstance(AccountRepository.class);
    objectMapper = new ObjectMapper();//.registerModule(new MoneyModule());
    App app = injector.getInstance(App.class);
    server = app.startServer();
    User user = new User();
    user.setFirstName("John");
    user.setLastName("Smith");
    user.setBirthDate(LocalDate.of(1970, 4, 1));
    Long id = userRepository.create(user);
    user.setId(id);
    Account account = new Account();
    MonetaryAmount amount = Monetary.getDefaultAmountFactory().
        setCurrency("USD").setNumber(100L).create();
    account.setBalance(amount);
    account.setIban("LT601010012345678901");
    account.setLastMovement(LocalDateTime.now(Clock.systemUTC()));
    account.setMovements(new LinkedList<>());
    account.setUser(user);
    accountsRepository.create(account);
    Account account2 = new Account();
    account2.setBalance(amount);
    account2.setLastMovement(LocalDateTime.now(Clock.systemUTC()));
    account2.setMovements(new LinkedList<>());
    account2.setUser(user);
    account2.setIban("LT601010012345678902");
    accountsRepository.create(account2);
  }

  @Test
  public void testTransfer() throws JsonProcessingException {
    MonetaryAmount amount = Monetary.getDefaultAmountFactory().
        setCurrency("USD").setNumber(50L).create();
    RequestTransferModel requestTransferModel = new RequestTransferModel();
    requestTransferModel.setSource("LT601010012345678901");
    requestTransferModel.setDestination("LT601010012345678902");
    requestTransferModel.setAmount(new MonetaryAmountModel(amount.getNumber().numberValue(
        BigDecimal.class), amount.getCurrency().getCurrencyCode()));
    String body = objectMapper.writeValueAsString(requestTransferModel);

    given().contentType(ContentType.JSON).body(body).post("http://localhost:8080/transfer").then()
        .statusCode(200);
  }

  @After
  public void tearDown() throws Exception {
    server.shutdownNow();
  }
}
