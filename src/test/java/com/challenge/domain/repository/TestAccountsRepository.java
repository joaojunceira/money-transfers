package com.challenge.domain.repository;


import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.number.OrderingComparison.greaterThanOrEqualTo;

import com.challenge.config.RepositoryModule;
import com.challenge.domain.model.Account;
import com.challenge.domain.model.User;
import com.google.inject.Guice;
import com.google.inject.Injector;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.inject.Inject;
import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.MonetaryAmount;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.JVM)
public class TestAccountsRepository {

  @Inject
  private AccountRepository repository;

  @Before
  public void setup() {
    Injector injector = Guice.createInjector(new RepositoryModule());
    this.repository = injector.getInstance(AccountRepository.class);
  }

  private Long createAccount() {
    Account account = new Account();
    CurrencyUnit usd = Monetary.getCurrency("USD");
    MonetaryAmount amount = Monetary.getDefaultAmountFactory().
        setCurrency(usd).setNumber(100L).create();
    account.setBalance(amount);
    account.setIban("LT601010012345678901");
    User user = new User();
    user.setId(999L);
    user.setFirstName("John");
    user.setLastName("Smith");
    user.setBirthDate(LocalDate.of(1970, 4, 1));
    account.setUser(user);
    account.setLastMovement(LocalDateTime.now(Clock.systemUTC()));
    Long id = repository.create(account);
    return id;
  }

  @Test
  public void testAccountCreation() {
    Long id = createAccount();
    Assert.assertThat("Id is generated correctly", id, greaterThanOrEqualTo(1L));
  }

  @Test
  public void testAccountBalanceChange() {
    Long id = createAccount();
    Account account = repository.findById(id);
    CurrencyUnit usd = Monetary.getCurrency("USD");
    MonetaryAmount amount = Monetary.getDefaultAmountFactory().
        setCurrency(usd).setNumber(100L).create();
    account.setBalance(account.getBalance().add(amount));
    Assert.assertThat("is updated", repository.update(account), is(equalTo(1)));
    account = repository.findById(id);
    MonetaryAmount expectedAmount = Monetary.getDefaultAmountFactory().
        setCurrency(usd).setNumber(200L).create();
    Assert.assertEquals("amount balance expected", account.getBalance(), expectedAmount);
  }


}
