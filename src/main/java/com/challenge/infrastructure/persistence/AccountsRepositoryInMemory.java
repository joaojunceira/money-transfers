package com.challenge.infrastructure.persistence;

import com.challenge.domain.model.Account;
import com.challenge.domain.repository.AccountRepository;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public final class AccountsRepositoryInMemory extends AccountRepository {

  private final ConcurrentHashMap<Long, Account> dataStore = new ConcurrentHashMap<>();
  private final AtomicLong idCounter = new AtomicLong();

  @Override
  public Long create(Account entity) {
    Long id = idCounter.incrementAndGet();
    entity.setId(id);
    dataStore.putIfAbsent(id, entity);
    return id;
  }

  @Override
  public Optional<Account> findById(Long key) {
    return Optional.ofNullable(dataStore.get(key));
  }

  @Override
  public Optional<Iterable<Account>> findAll() {
    return Optional.ofNullable(dataStore.values());
  }

  @Override
  public Integer delete(Long key) {
    return dataStore.remove(key) != null ? 1 : 0;
  }


  @Override
  public Integer update(Account entity) {
    return dataStore.replace(entity.getId(), entity) != null ? 1 : 0;
  }

  @Override
  public Optional<Account> findByIban(final String iban) {
    return dataStore.values().stream().filter(a -> a.getIban().equalsIgnoreCase(iban)).
        findFirst();
  }
}
