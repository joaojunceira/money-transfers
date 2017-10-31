package com.challenge.domain.repository;


import static org.hamcrest.number.OrderingComparison.greaterThanOrEqualTo;

import com.challenge.config.RepositoryModule;
import com.challenge.domain.model.User;
import com.google.inject.Guice;
import com.google.inject.Injector;
import java.time.LocalDate;
import java.util.Optional;
import javax.inject.Inject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.JVM)
public class TestUserRepository {

  private UserRepository repository;

  @Before
  public void setup() {
    Injector injector = Guice.createInjector(new RepositoryModule());
    this.repository = injector.getInstance(UserRepository.class);
  }

  private User prepareUser() {
    User user = new User();
    user.setFirstName("John");
    user.setLastName("Smith");
    user.setBirthDate(LocalDate.of(1970, 4, 1));
    return user;
  }

  @Test
  public void testCreateUser() {
    //Arrange
    User user = prepareUser();
    //Act
    Long id = repository.create(user);
    //Assert
    Assert.assertThat("Id is generated correctly", id, greaterThanOrEqualTo(1L));
  }

  @Test
  public void testGetUser() {
    //Arrange
    User user = prepareUser();
    Long id = repository.create(user);
    user.setId(id);
    //Act
    Optional<User> userRead = repository.findById(id);
    //Assert
    Assert.assertTrue(userRead.isPresent());
    Assert.assertEquals(user, userRead.get());
  }

}
