package com.challenge.rest;

import com.challenge.config.RepositoryModule;
import com.challenge.config.ServiceModule;
import com.challenge.domain.model.Account;
import com.challenge.domain.model.User;
import com.challenge.domain.repository.AccountRepository;
import com.challenge.domain.repository.UserRepository;
import com.challenge.domain.service.transfer.TransferService;
import com.challenge.rest.controller.TransferController;
import com.google.inject.Guice;
import com.google.inject.Injector;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import javax.inject.Inject;
import javax.money.Monetary;
import javax.money.MonetaryAmount;
import org.junit.Before;

public class TransferIntegrationTest {
  @Inject
  private UserRepository userRepository;
  @Inject
  private AccountRepository accountRepository;


  @Before
  public void setup() {
    Injector injector = Guice.createInjector(new RepositoryModule(), new ServiceModule());
    this.userRepository = injector.getInstance(UserRepository.class);
    this.accountRepository = injector.getInstance(AccountRepository.class);
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
    accountRepository.create(account);
    Account account2 = new Account();
    account2.setBalance(amount);
    account2.setLastMovement(LocalDateTime.now(Clock.systemUTC()));
    account2.setMovements(new LinkedList<>());
    account2.setUser(user);
    account2.setIban("LT601010012345678902");
    accountRepository.create(account2);
  }


}
