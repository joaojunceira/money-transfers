package com.challenge.infrastructure.persistence;

import com.challenge.domain.model.User;
import com.challenge.domain.repository.UserRepository;
import java.util.List;

public class UserRepositoryInMemory extends UserRepository{

  @Override
  public Long create(User entity) {
    return null;
  }

  @Override
  public User findById(Long key) {
    return null;
  }

  @Override
  public List<User> findAll() {
    return null;
  }

  @Override
  public Integer delete(Long key) {
    return null;
  }

  @Override
  public Integer update(User entity) {
    return null;
  }
}
