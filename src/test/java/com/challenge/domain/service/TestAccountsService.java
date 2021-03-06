package com.challenge.domain.service;

import com.challenge.config.RepositoryModule;
import com.challenge.config.ServiceModule;
import com.challenge.domain.service.account.AccountDetail;
import com.challenge.domain.service.account.AccountRequest;
import com.challenge.domain.service.account.AccountService;
import com.challenge.domain.service.user.UserCreationRequest;
import com.challenge.domain.service.user.UserDetail;
import com.challenge.domain.service.user.UserService;
import com.google.inject.Guice;
import com.google.inject.Injector;
import java.time.LocalDate;
import javax.money.Monetary;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.JVM)
public class TestAccountsService {

  private AccountService accountService;
  private UserService userService;

  @Before
  public void setup() {
    Injector injector = Guice.createInjector(new RepositoryModule(), new ServiceModule());
    this.accountService = injector.getInstance(AccountService.class);
    this.userService = injector.getInstance(UserService.class);
  }

  private UserDetail createUser() {
    UserCreationRequest userCreationRequest = new UserCreationRequest();
    userCreationRequest.setFirstName("John");
    userCreationRequest.setLastName("Smith");
    userCreationRequest.setBirthDate(LocalDate.of(1970, 4, 1));
    return userService.create(userCreationRequest);
  }

  private AccountRequest prepareAccountEntity(UserDetail user) {
    AccountRequest request = new AccountRequest();
    request.setUserId(user.getId());
    request.setCurrency("EUR");
    return request;
  }

  @Test
  public void testCreationAndGetOfAccount() {
    //Arrange
    UserDetail user = createUser();
    AccountRequest requestObject = prepareAccountEntity(user);
    //Act
    AccountDetail accountDetail = accountService.create(requestObject);
    AccountDetail accountDetailFromGet = accountService.get(accountDetail.getIban());
    //Assert
    Assert.assertNotNull(accountDetail);
    Assert.assertNotNull(accountDetail.getBalance());
    Assert.assertEquals(accountDetail.getBalance().getCurrency(), Monetary.getCurrency("EUR"));
    Assert.assertNotNull(accountDetail.getIban());
    Assert.assertNotNull(accountDetail.getLastMovement());
    Assert.assertNotNull(accountDetail.getUser());
    Assert.assertNotNull(accountDetail.getId());
    Assert.assertEquals(accountDetailFromGet, accountDetail);
  }

}
