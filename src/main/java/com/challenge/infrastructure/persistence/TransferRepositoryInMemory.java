package com.challenge.infrastructure.persistence;

import com.challenge.domain.model.Transfer;
import com.challenge.domain.repository.TransferRepository;
import java.util.List;

public class TransferRepositoryInMemory extends TransferRepository{

  @Override
  public Long create(Transfer entity) {
    return null;
  }

  @Override
  public Transfer findById(Long key) {
    return null;
  }

  @Override
  public List<Transfer> findAll() {
    return null;
  }

  @Override
  public Integer delete(Long key) {
    return null;
  }

  @Override
  public Integer update(Transfer entity) {
    return null;
  }
}
