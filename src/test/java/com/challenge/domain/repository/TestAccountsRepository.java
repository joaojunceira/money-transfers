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
import java.util.Optional;
import javax.inject.Inject;
import javax.money.Monetary;
import javax.money.MonetaryAmount;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.JVM)
public class TestAccountsRepository {

  private AccountRepository repository;

  @Before
  public void setup() {
    Injector injector = Guice.createInjector(new RepositoryModule());
    this.repository = injector.getInstance(AccountRepository.class);
  }

  private Account createAccountEntity() {
    Account account = new Account();
    MonetaryAmount amount = Monetary.getDefaultAmountFactory().
        setCurrency("USD").setNumber(100L).create();
    account.setBalance(amount);
    account.setIban("LT601010012345678901");
    User user = new User();
    user.setId(999L);
    user.setFirstName("John");
    user.setLastName("Smith");
    user.setBirthDate(LocalDate.of(1970, 4, 1));
    account.setUser(user);
    account.setLastMovement(LocalDateTime.now(Clock.systemUTC()));
    return account;
  }

  @Test
  public void testAccountCreation() {
    //Arrange
    Account account = createAccountEntity();
    //Act
    Long id = repository.create(account);
    //Assert
    Assert.assertThat("Id is generated correctly", id, greaterThanOrEqualTo(1L));
  }

  private Account prepareAccountRepository() {
    Account accountEntity = createAccountEntity();
    Long id = repository.create(accountEntity);
    Optional<Account> account = repository.findById(id);
    return account.get();
  }

  private Integer updateAccount(Account account) {
    MonetaryAmount amount = Monetary.getDefaultAmountFactory().
        setCurrency("USD").setNumber(100L).create();
    account.setBalance(account.getBalance().add(amount));
    return repository.update(account);
  }

  @Test
  public void testAccountBalanceChange() {
    //Arrange
    Account accountPrep = prepareAccountRepository();
    MonetaryAmount expectedAmount = Monetary.getDefaultAmountFactory().
        setCurrency("USD").setNumber(200L).create();
    //Act
    Integer updateResult = updateAccount(accountPrep);
    Optional<Account> account = repository.findById(accountPrep.getId());
    //Assert
    Assert.assertThat("is updated", updateResult, is(equalTo(1)));
    Assert.assertTrue(account.isPresent());
    Assert.assertEquals("amount balance expected", account.get().getBalance(), expectedAmount);
  }


}
