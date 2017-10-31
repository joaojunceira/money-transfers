package com.challenge.infrastructure.persistence;

import com.challenge.domain.model.Transfer;
import com.challenge.domain.repository.TransferRepository;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;


public final class TransferRepositoryInMemory extends TransferRepository {

  private final ConcurrentHashMap<Long, Transfer> dataStore = new ConcurrentHashMap<>();
  private final AtomicLong idCounter = new AtomicLong();

  @Override
  public Long create(Transfer entity) {
    Long id = idCounter.incrementAndGet();
    entity.setId(id);
    dataStore.putIfAbsent(id, entity);
    return id;
  }

  @Override
  public Optional<Transfer> findById(Long key) {
    return Optional.ofNullable(dataStore.get(key));
  }

  @Override
  public Optional<Iterable<Transfer>> findAll() {
    return Optional.ofNullable(dataStore.values());
  }

  @Override
  public Integer delete(Long key) {
    return dataStore.remove(key) != null ? 1 : 0;
  }

  @Override
  public Integer update(Transfer entity) {
    return dataStore.replace(entity.getId(), entity) != null ? 1 : 0;
  }
}
