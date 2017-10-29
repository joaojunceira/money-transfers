package com.challenge.domain.service;

import com.challenge.config.RepositoryModule;
import com.challenge.config.ServiceModule;
import com.challenge.domain.service.user.UserCreationRequest;
import com.challenge.domain.service.user.UserDetail;
import com.challenge.domain.service.user.UserService;
import com.google.inject.Guice;
import com.google.inject.Injector;
import java.time.LocalDate;
import javax.inject.Inject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.JVM)
public class TestUsersService {

  @Inject
  private UserService userService;

  @Before
  public void setup() {
    Injector injector = Guice.createInjector(new RepositoryModule(), new ServiceModule());
    this.userService = injector.getInstance(UserService.class);
  }

  @Test
  public void testUserCreation() {
    UserDetail detail = createUser();
    Assert.assertNotNull(detail);
  }

  private UserDetail createUser() {
    UserCreationRequest request = new UserCreationRequest();
    request.setLastName("John");
    request.setFirstName("Watson");
    request.setBirthDate(LocalDate.of(1974, 4, 25));
    return userService.create(request);
  }

  @Test
  public void testGettingUser() {
    UserDetail createdUser = createUser();
    UserDetail detailGetted = userService.get(createdUser.getId());
    Assert.assertEquals(createdUser, detailGetted);
  }
}
