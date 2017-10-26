package com.challenge.infrastructure.persistence;

import com.challenge.domain.model.Account;
import com.challenge.domain.repository.AccountRepository;
import java.util.List;

public class AccountRepositoryInMemory extends AccountRepository{

  @Override
  public String create(Account entity) {
    return null;
  }

  @Override
  public Account findById(String key) {
    return null;
  }

  @Override
  public List<Account> findAll() {
    return null;
  }

  @Override
  public Integer delete(String key) {
    return null;
  }

  @Override
  public Integer update(Account entity) {
    return null;
  }
}
