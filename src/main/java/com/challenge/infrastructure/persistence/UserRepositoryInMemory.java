package com.challenge.infrastructure.persistence;

import com.challenge.domain.model.User;
import com.challenge.domain.repository.UserRepository;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public final class UserRepositoryInMemory extends UserRepository {

  private final ConcurrentHashMap<Long, User> dataStore = new ConcurrentHashMap<>();
  private final AtomicLong idCounter = new AtomicLong();

  @Override
  public Long create(User entity) {
    Long id = idCounter.incrementAndGet();
    entity.setId(id);
    dataStore.putIfAbsent(id, entity);
    return id;
  }

  @Override
  public Optional<User> findById(Long key) {
    return Optional.ofNullable(dataStore.get(key));
  }

  @Override
  public Optional<Iterable<User>> findAll() {
    return Optional.ofNullable(dataStore.values());
  }

  @Override
  public Integer delete(Long key) {
    return dataStore.remove(key) != null ? 1 : 0;
  }

  @Override
  public Integer update(User entity) {
    return dataStore.replace(entity.getId(), entity) != null ? 1 : 0;
  }
}
