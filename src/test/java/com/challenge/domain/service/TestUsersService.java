package com.challenge.domain.service;

import static org.hamcrest.number.OrderingComparison.greaterThanOrEqualTo;

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

  private UserCreationRequest prepareUserRequestObject() {
    UserCreationRequest request = new UserCreationRequest();
    request.setLastName("John");
    request.setFirstName("Watson");
    request.setBirthDate(LocalDate.of(1974, 4, 25));
    return request;
  }

  @Test
  public void testUserCreation() {
    //Arrange
    UserCreationRequest request = prepareUserRequestObject();
    //Act
    UserDetail response = userService.create(request);
    //Assert
    Assert.assertNotNull(response);
    Assert.assertThat("Is Id generated", response.getId(), greaterThanOrEqualTo(response.getId()));
    Assert.assertEquals(response.getFirstName(), request.getFirstName());
    Assert.assertEquals(response.getLastName(), request.getLastName());
    Assert.assertEquals(response.getBirthDate(), request.getBirthDate());
  }

  @Test
  public void testGettingUser() {
    //Arrange
    UserCreationRequest request = prepareUserRequestObject();
    UserDetail response = userService.create(request);
    //Act
    UserDetail detailGetted = userService.get(response.getId());
    //Assert
    Assert.assertEquals(response, detailGetted);
  }
}
