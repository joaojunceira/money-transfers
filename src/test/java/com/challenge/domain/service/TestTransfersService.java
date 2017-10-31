package com.challenge.domain.service;

import static org.hamcrest.CoreMatchers.equalTo;

import com.challenge.config.RepositoryModule;
import com.challenge.config.ServiceModule;
import com.challenge.domain.model.Account;
import com.challenge.domain.model.User;
import com.challenge.domain.repository.AccountRepository;
import com.challenge.domain.repository.UserRepository;
import com.challenge.domain.service.transfer.TransferRequest;
import com.challenge.domain.service.transfer.TransferResult;
import com.challenge.domain.service.transfer.TransferService;
import com.google.inject.Guice;
import com.google.inject.Injector;
import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import javax.inject.Inject;
import javax.money.Monetary;
import javax.money.MonetaryAmount;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.JVM)
public class TestTransfersService {

  private UserRepository userRepository;
  private AccountRepository accountsRepository;
  private TransferService transferService;

  @Before
  public void setup() {
    Injector injector = Guice.createInjector(new RepositoryModule(), new ServiceModule());
    this.userRepository = injector.getInstance(UserRepository.class);
    this.accountsRepository = injector.getInstance(AccountRepository.class);
    this.transferService = injector.getInstance(TransferService.class);
  }

  private void arrangeRepositories() {
    User user = createUser();
    MonetaryAmount amount = Monetary.getDefaultAmountFactory().
        setCurrency("USD").setNumber(100L).create();
    Account account = createAccountEntity(user, "LT601010012345678901", amount);
    accountsRepository.create(account);
    Account account2 = createAccountEntity(user, "LT601010012345678902", amount);
    accountsRepository.create(account2);
  }

  private Account createAccountEntity(User user, String iban, MonetaryAmount amount) {
    Account account = new Account();
    account.setBalance(amount);
    account.setIban(iban);
    account.setLastMovement(LocalDateTime.now(Clock.systemUTC()));
    account.setMovements(new LinkedList<>());
    account.setUser(user);
    return account;
  }

  private User createUser() {
    User user = new User();
    user.setFirstName("John");
    user.setLastName("Smith");
    user.setBirthDate(LocalDate.of(1970, 4, 1));
    Long id = userRepository.create(user);
    user.setId(id);
    return user;
  }

  @Test
  public void testSingleTransfer() {
    //Arrange
    arrangeRepositories();
    Account sourceBefore = accountsRepository.findByIban("LT601010012345678901").get();
    Account destinationBefore = accountsRepository.findByIban("LT601010012345678902").get();
    //Act
    MonetaryAmount amount = Monetary.getDefaultAmountFactory().
        setCurrency("USD").setNumber(50L).create();
    TransferRequest transferRequest = new TransferRequest();
    TransferResult transferResult = doTransfer(amount, transferRequest);
    //Assert
    assertResult(amount, transferRequest, transferResult);
    //Check if Balance was changed correctly
    assertBalance(sourceBefore, destinationBefore, amount, transferResult);
  }

  private void assertResult(MonetaryAmount amount, TransferRequest transferRequest,
      TransferResult transferResult) {
    Assert.assertNotNull(transferResult);
    Assert.assertThat("Source account is same", transferResult.getSource(),
        equalTo(transferRequest.getSource()));
    Assert.assertThat("Destination account is same", transferResult.getDestination(),
        equalTo(transferRequest.getDestination()));
    Assert.assertThat("Amount is same", transferResult.getAmount(), equalTo(amount));
  }

  private void assertBalance(Account sourceBefore, Account destinationBefore, MonetaryAmount amount,
      TransferResult transferResult) {
    Account sourceAfter = accountsRepository.findByIban(transferResult.getSource()).get();
    Account destinationAfter = accountsRepository.findByIban(transferResult.getDestination()).get();
    MonetaryAmount expectedSourceBalance = sourceBefore.getBalance();
    expectedSourceBalance.subtract(amount);
    Assert.assertThat("Source account was debited", sourceAfter.getBalance(),
        equalTo(expectedSourceBalance));
    MonetaryAmount expectedDestinationBalance = destinationBefore.getBalance();
    expectedDestinationBalance.add(amount);
    Assert.assertThat("Destination account was credited", destinationAfter.getBalance(),
        equalTo(expectedDestinationBalance));
  }

  private TransferResult doTransfer(MonetaryAmount amount, TransferRequest transferRequest) {
    transferRequest.setSource("LT601010012345678901");
    transferRequest.setDestination("LT601010012345678902");
    transferRequest.setAmount(amount.getNumber().numberValue(BigDecimal.class));
    transferRequest.setCurrency(amount.getCurrency().getCurrencyCode());
    return transferService.doTransfer(transferRequest);
  }

}
